package incubator.visprotocol.structure;

import java.io.Serializable;

/**
 * Entry point to whole tree. Has timestamp, used for diffs, should be simulation time in millis
 * (offset doesn't metter). Then has type and pointer to root folder.
 * 
 * @author Ondrej Milenovsky
 * */
public class Structure implements Serializable, Comparable<Structure> {

    private static final long serialVersionUID = -5536165397290203610L;

    public static final String DEFAULT_TYPE = "";

    /** you can modify this */
    public static Structure createEmptyInstance() {
        return new Structure();
    }

    private Folder root;
    private Long timeStamp = null;
    private String type = DEFAULT_TYPE;

    public Structure() {
    }

    public Structure(String type) {
        this.type = type;
    }

    /** copies only timestamp */
    public Structure(Structure s) {
        this(s.getTimeStamp());
        type = s.getType();
    }

    public Structure(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Structure(String type, Long timeStamp) {
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public Structure(Folder folder) {
        this.root = folder;
    }

    public Structure(Folder folder, Long timeStamp) {
        this(folder, DEFAULT_TYPE, timeStamp);
    }

    public Structure(Folder folder, String type, Long timeStamp) {
        this.type = type;
        this.root = folder;
        this.timeStamp = timeStamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /** if type is one of specified or default */
    public boolean isType(String... types) {
        if (type.equals(DEFAULT_TYPE)) {
            return true;
        }
        for (String s : types) {
            if (type.equals(s)) {
                return true;
            }
        }
        return false;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
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
        return new Structure(root.deepCopy(), type, timeStamp);
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
        if (type == null) {
            if (s2.getType() != null) {
                return false;
            }
        } else {
            if (!type.equals(s2.getType())) {
                return false;
            }
        }
        if (timeStamp == null) {
            return (s2.getTimeStamp() == null);
        } else {
            return timeStamp.equals(s2.getTimeStamp());
        }
    }

    public String print() {
        String ret = "";
        if ((type != null) && !type.equals("")) {
            ret += type + "\n";
        }
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

    @Override
    public int compareTo(Structure o) {
        return (int) (timeStamp - o.getTimeStamp());
    }
}
