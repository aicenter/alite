package incubator.visprotocol.vis.structure;

public class Structure {
    private Folder folder;

    public Structure() {
    }

    public Structure(Folder folder) {
        this.folder = folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Folder getFolder() {
        return folder;
    }

    /** if folder does not exist, creates it */
    public Folder getFolder(String id) {
        if (folder == null) {
            folder = new Folder(id);
        }
        return folder;
    }

}
