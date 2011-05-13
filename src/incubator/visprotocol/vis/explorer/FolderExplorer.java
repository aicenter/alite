package incubator.visprotocol.vis.explorer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Folder explorer to filter layers
 * 
 * @author Ondrej Milenovsky
 * */
public class FolderExplorer extends JPanel implements Runnable {

    private static final long serialVersionUID = -3988610515367861313L;

    private JTree tree;

    private final StructProcessor input;

    private int refreshInterval = 500;

    /** inputs must return whole structure, not diffs */
    public FolderExplorer(StructProcessor input) {
        this.input = input;
        initComponens();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initComponens() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 500));
        tree = new JTree();
        tree.setModel(null);
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
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(f.getId());
                node.add(newNode);
                refresh(newNode, f);
            }
            for (Element e : folder.getElements()) {
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(e.getId());
                node.add(newNode);
            }
        }
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
