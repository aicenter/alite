package incubator.visprotocol.structure;

import java.io.Serializable;

public class Structure implements Serializable {

    private static final long serialVersionUID = -5536165397290203610L;

    /** do not modify this instance !!! */
    public static final Structure EMPTY_INSTANCE = new Structure();

    /** you can modify this */
    public static Structure createEmptyInstance() {
        return new Structure();
    }

    private Folder root;
    private Long timeStamp = null;

    public Structure() {
    }

    /** copies only timestamp */
    public Structure(Structure s) {
        this(s.getTimeStamp());
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

    public void setTimeStamp(Structure s) {
        this.timeStamp = s.getTimeStamp();
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

    public void clear() {
        root = null;
    }

    /** Makes deep copy of the structure, not of element parameters! */
    public Structure deepCopy() {
        if (root == null) {
            return new Structure(timeStamp);
        }
        if(timeStamp == null) {
            return new Structure(root.deepCopy());
        }
        return new Structure(root.deepCopy(), timeStamp);
    }

    public boolean equalsDeep(Object obj) {
        if (!equals(obj)) {
            return false;
        }
        Structure s2 = (Structure) obj;
        if (root == null) {
            return s2.getRoot() == null;
        } else {
            return root.equalsDeep(s2.getRoot());
        }
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
        if (timeStamp == null) {
            return (s2.getTimeStamp() == null);
        } else {
            return timeStamp.equals(s2.getTimeStamp());
        }
    }

    public String print() {
        String ret = "";
        if (timeStamp != null) {
            ret += timeStamp + "\n";
        }
        if (root == null) {
            ret += "Empty\n";
        } else {
            ret += root.print();
        }
        return ret;
    }
}
