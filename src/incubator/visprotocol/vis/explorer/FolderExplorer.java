package incubator.visprotocol.vis.explorer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * Folder explorer to filter layers
 * 
 * @author Ondrej Milenovsky
 * */
public class FolderExplorer extends JPanel implements Runnable, MouseListener {

    private static final long serialVersionUID = -3988610515367861313L;

    private JTree tree;

    private int refreshInterval = 500;

    private final StructProcessor input;
    private FilterStorage filter;

    /** inputs must return whole structure, not diffs */
    public FolderExplorer(StructProcessor input, FilterStorage filter) {
        this.input = input;
        this.filter = filter;
        initComponens();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initComponens() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 500));
        tree = new JTree();
        tree.setModel(null);
        tree.setCellRenderer(new CheckBoxNodeRenderer());
        tree.addMouseListener(this);
        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    public void refresh() {
        Structure struct = input.pull();
        if (!struct.getType().equals(CommonKeys.STRUCT_COMPLETE)) {
            System.err.println("Explorer must accept whole part");
        }
        if (struct.isEmpty()) {
            return;
        }
        DefaultMutableTreeNode root;
        if (tree.getModel() == null) {
            root = new DefaultMutableTreeNode(struct.getRoot().getId());
            tree.setModel(new DefaultTreeModel(root));
        } else {
            root = (DefaultMutableTreeNode) tree.getModel().getRoot();
        }
        refresh(root, struct.getRoot());
    }

    private void refresh(DefaultMutableTreeNode node, Folder folder) {
        if (node.isLeaf()) {
            for (Folder f : folder.getFolders()) {
                DefaultMutableTreeNode newNode = new CheckBoxNode(f.getId());
                node.add(newNode);
                refresh(newNode, f);
            }
            for (Element e : folder.getElements()) {
                DefaultMutableTreeNode newNode = new CheckBoxNode(e.getId());
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
    public void run() {
        while (true) {
            try {
                Thread.sleep(refreshInterval);
            } catch (InterruptedException e) {
            }
            refresh();
        }
    }

}
