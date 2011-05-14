package incubator.visprotocol.vis.explorer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.struct.ChangeFlag;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Folder explorer to filter layers
 * 
 * @author Ondrej Milenovsky
 * */
public class FolderExplorer extends JPanel implements MouseListener, StructProcessor, Runnable {

    private static final long serialVersionUID = -3988610515367861313L;

    private JTree tree;

    private final StructProcessor input;
    private FilterStorage filter;
    private int resizable = 0;

    private int refreshInterval = 1000;

    public FolderExplorer(StructProcessor input, FilterStorage filter) {
        this.input = input;
        this.filter = filter;
        init();
    }

    public FolderExplorer(StructProcessor input, FilterStorage filter, boolean leftSide) {
        this.input = input;
        this.filter = filter;
        if (leftSide) {
            resizable = 1;
        } else {
            resizable = 2;
        }
        init();
    }

    private void init() {
        initComponens();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initComponens() {
        setPreferredSize(new Dimension(200, 500));

        tree = new JTree();
        tree.setModel(null);
        tree.setCellRenderer(new CheckBoxNodeRenderer());
        tree.addMouseListener(this);
        // Component toAdd = new JScrollPane(tree);
        if (resizable == 0) {
            setLayout(new BorderLayout());
            add(new JScrollPane(tree), BorderLayout.CENTER);
        } else {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gbc.gridy = 0;
            gbc.gridwidth = gbc.gridheight = 1;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = gbc.weighty = 100;
            // add(toAdd, gbc);
        }
    }

    public void update() {
        Structure struct = input.pull();
        if (!struct.getType().equals(CommonKeys.STRUCT_DIFF)) {
            System.err.println("Explorer must accept diffs");
        }
        if (struct.isEmpty()) {
            return;
        }
        CheckBoxNode root;
        if (tree.getModel() == null) {
            root = new CheckBoxNode(struct.getRoot().getId());
            tree.setModel(new DefaultTreeModel(root));
        } else {
            root = (CheckBoxNode) tree.getModel().getRoot();
        }
        update(root, struct.getRoot());
    }

    private void update(CheckBoxNode node, Folder folder) {
        for (Folder f : folder.getFolders()) {
            ChangeFlag change = f.getParameter(CommonKeys.CHANGE);
            if (change == ChangeFlag.DELETE) {
                node.remove(f.getId());
            } else if ((change == ChangeFlag.CREATE) || (!node.hasChild(f.getId()))) {
                CheckBoxNode newNode = new CheckBoxNode(f.getId(), true);
                node.add(newNode);
                update(newNode, f);
            } else {
                if (!f.parameterEqual(CommonKeys.NOT_CHANGE, true)) {
                    update(node.getChild(f.getId()), f);
                }
            }
        }
        for (Element e : folder.getElements()) {
            ChangeFlag change = e.getParameter(CommonKeys.CHANGE);
            if (change == ChangeFlag.DELETE) {
                node.remove(e.getId());
            } else if ((change == ChangeFlag.CREATE) || (!node.hasChild(e.getId()))) {
                CheckBoxNode newNode = new CheckBoxNode(e.getId(), true);
                node.add(newNode);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        TreePath path = tree.getSelectionPath();
        Object node = path.getLastPathComponent();
        if (node instanceof CheckBoxNode) {
            CheckBoxNode cbNode = (CheckBoxNode) node;
            if (e.getButton() == MouseEvent.BUTTON1) {
                cbNode.setSelected(!cbNode.isSelected());
                // TODO update filter
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                // TODO popup menu
            }
        }
        tree.setSelectionInterval(0, 0);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public Structure pull() {
        update();
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(refreshInterval);
            } catch (InterruptedException e) {
            }
            invalidate();
        }
    }

}
