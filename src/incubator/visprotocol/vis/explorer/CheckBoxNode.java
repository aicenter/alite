package incubator.visprotocol.vis.explorer;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Tree node with checkbox
 * 
 * @author Ondrej Milenovsky
 * */
public class CheckBoxNode extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 5758349537398498969L;

    private boolean selected;
    private String text;

    public CheckBoxNode(String name) {
        this(name, true);
    }

    public CheckBoxNode(String name, boolean selected) {
        super(name);
        this.text = name;
        this.selected = selected;
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    public String getText() {
        return text;
    }
    
}
