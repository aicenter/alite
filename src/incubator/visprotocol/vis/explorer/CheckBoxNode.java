package incubator.visprotocol.vis.explorer;

import java.util.HashMap;
import java.util.Map;

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
    private final Map<String, Integer> childIndex;
    private boolean checkable;

    /** same as DefaultMutableTreeNode */
    public CheckBoxNode(String name) {
        this(name, true);
        checkable = false;
    }

    public CheckBoxNode(String name, boolean selected) {
        super(name);
        checkable = true;
        this.text = name;
        this.selected = selected;
        childIndex = new HashMap<String, Integer>();
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

    public boolean isCheckable() {
        return checkable;
    }

    public void add(CheckBoxNode newChild) {
        childIndex.put(newChild.getText(), getChildCount());
        super.add(newChild);
    }

    public void remove(String id) {
        int index = childIndex.remove(id);
        remove(index);
    }

    public CheckBoxNode getChild(String id) {
        return (CheckBoxNode) getChildAt(childIndex.get(id));
    }

    public boolean hasChild(String id) {
        return childIndex.containsKey(id);
    }

}
