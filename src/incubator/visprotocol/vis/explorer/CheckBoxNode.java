package incubator.visprotocol.vis.explorer;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * Tree node with checkbox
 * 
 * @author Ondrej Milenovsky
 * */
public class CheckBoxNode implements MutableTreeNode {

    private static final long serialVersionUID = 5758349537398498969L;

    private boolean selected;
    private String text;
    private boolean checkable;

    private MutableTreeNode parent;
    private LinkedHashMap<String, CheckBoxNode> children;

    /** same as DefaultMutableTreeNode */
    public CheckBoxNode(String name) {
        this(name, true);
        checkable = false;
    }

    public CheckBoxNode(String name, boolean selected) {
        checkable = true;
        this.text = name;
        this.selected = selected;
        children = new LinkedHashMap<String, CheckBoxNode>();
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

    public void add(CheckBoxNode node) {
        children.put(node.getText(), node);
        node.setParent(this);
    }

    public void remove(String id) {
        // TODO remove this and fix some bug
        if (!hasChild(id)) {
            return;
        }
        try {
            children.remove(id).setParent(null);
        } catch (NullPointerException e) {
            throw new NullPointerException("No node " + id + " in node " + getText());
        }
    }

    public CheckBoxNode getChild(String id) {
        return children.get(id);
    }

    public boolean hasChild(String id) {
        return children.containsKey(id);
    }

    private void notUsed() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        notUsed();
    }

    @Override
    public void remove(int index) {
        children.remove(getChildAt(index));
    }

    @Override
    public void remove(MutableTreeNode node) {
        if (node instanceof CheckBoxNode) {
            children.remove(((CheckBoxNode) node).getText());
        } else {
            notUsed();
        }
    }

    @Override
    public void removeFromParent() {
        parent.remove(this);
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        parent = newParent;
    }

    @Override
    public void setUserObject(Object object) {
        notUsed();
    }

    @Override
    public Enumeration<CheckBoxNode> children() {
        return Collections.enumeration(children.values());
    }

    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        int i = 0;
        for (CheckBoxNode node : children.values()) {
            if (i == childIndex) {
                return node;
            }
            i++;
        }
        return null;
    }

    @Override
    public int getChildCount() {
        return children.size();
    }

    @Override
    public int getIndex(TreeNode node) {
        int i = 0;
        for (CheckBoxNode n : children.values()) {
            if (n == node) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public TreeNode getParent() {
        return parent;
    }

    @Override
    public boolean isLeaf() {
        return children.isEmpty();
    }

}
