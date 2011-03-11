package incubator.visprotocol.vis.structure;

public class Structure {
    private Folder root;

    public Structure() {
    }

    public Structure(Folder folder) {
        this.root = folder;
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

    public void update(Structure s) {
        if (!equals(s)) {
            throw new RuntimeException("Updating different structure");
        }
        root = s.getRoot();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
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
}
