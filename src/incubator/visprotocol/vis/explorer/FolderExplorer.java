package incubator.visprotocol.vis.explorer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

/**
 * Folder explorer to filter layers
 * 
 * @author Ondrej Milenovsky
 * */
public class FolderExplorer extends JPanel {
    
    private static final long serialVersionUID = -3988610515367861313L;

    private JTree tree;
    
    private final StructProcessor input;
    
    public FolderExplorer(StructProcessor input) {
        this.input = input;
        initComponens();
    }

    
    private void initComponens() {
        tree = new JTree();
    }
    
    @SuppressWarnings("unused")
    public void refresh() {
        Structure struct = input.pull();
        TreeModel model = tree.getModel();
        // TODO
    }
    
}
