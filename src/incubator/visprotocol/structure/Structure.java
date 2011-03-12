package incubator.visprotocol.structure;

import java.io.Serializable;

public class Structure implements Serializable {

    private static final long serialVersionUID = -5536165397290203610L;

    private Folder root;
    private Long timeStamp = null;

    public Structure() {
    }

    public Structure(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Structure(Folder folder) {
        this.root = folder;
    }

    public Structure(Folder folder, long timeStamp) {
        this.root = folder;
        this.timeStamp = timeStamp;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setRoot(Folder folder) {
        this.root = folder;
    }

    public Folder getRoot() {
        return root;
    }

    /** if folder does not exist, creates it */
    public Folder getRoot(String id) {
        if (root == null) {
            root = new Folder(id);
        }
        return root;
    }

    /** if folder does not exist, creates it */
    public Folder getRoot(Folder f) {
        return getRoot(f.getId());
    }

    public boolean isEmpty() {
        return root == null;
    }

    @Deprecated
    public void update(Structure s) {
        if (!equals(s)) {
            throw new RuntimeException("Updating different structure");
        }
        root = s.getRoot();
    }

    /** Makes deep copy of the structure, not of element parameters! */
    public Structure deepCopy() {
        if (root == null) {
            return new Structure(timeStamp);
        }
        return new Structure(getRoot().deepCopy(), timeStamp);
    }

    // TODO
    public boolean equalsDeep(Object obj) {
        return equals(obj);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Structure)) {
            return false;
        }
        Structure s2 = (Structure) obj;
        if (s2.getRoot() == root) {
            return true;
        }
        if ((root == null) || (s2.root == null)) {
            return false;
        }
        return root.equals(s2.getRoot());
    }

    public String print() {
        if (root == null) {
            return "Empty\n";
        }
        return root.print();
    }
}
