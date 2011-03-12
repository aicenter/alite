package incubator.visprotocol.structprocessor;

import incubator.visprotocol.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;

/**
 * Holds current state, accepts updates from differ and updates current state. Default setting is
 * not to delete folders. Makes deep copy of update part.
 * 
 * @author Ondrej Milenovsky
 * */
public class Updater implements StructProcessor {

    private Structure state;
    private boolean deleteFolders;

    public Updater() {
        this(new Structure());
    }

    public Updater(Structure struct) {
        deleteFolders = false;
        state = struct;
    }

    public void setDeleteFolders(boolean deleteFolders) {
        this.deleteFolders = deleteFolders;
    }

    public boolean isDeleteFolders() {
        return deleteFolders;
    }

    @Override
    public Structure pull() {
        return state;
    }

    @Override
    public void push(Structure newPart) {
        if (newPart.getTimeStamp() == null) {
            System.out.println("Warning: new part has no timestamp");
        } else if ((state.getTimeStamp() != null)
                && (state.getTimeStamp() >= newPart.getTimeStamp())) {
            System.out.println("Warning: Current time: " + state.getTimeStamp()
                    + " >= update time " + newPart.getTimeStamp());
        }
        state.setTimeStamp(newPart.getTimeStamp());
        if (newPart.isEmpty()) {
            return;
        }
        if (state.isEmpty()) {
            state.setRoot(newPart.getRoot().deepCopy());
            return;
        }
        if (!newPart.getRoot().equals(state.getRoot())) {
            throw new RuntimeException("Current folder" + state.getRoot().getId()
                    + " != new folder " + newPart.getRoot().getId());
        }
        update(newPart.getRoot(), state.getRoot());
    }

    /** recursive updating */
    private void update(Folder newF, Folder currF) {
        currF.updateParams(newF);
        if (deleteElement(newF)) {
            currF.clearElements();
            if (deleteFolders) {
                currF.clearFolders();
            }
        } else {
            for (Folder f : newF.getFolders()) {
                update(f, currF.getFolder(f));
            }
            for (Element e : newF.getElements()) {
                if (deleteElement(e)) {
                    currF.removeElement(e);
                } else {
                    currF.getElement(e).updateParams(e);
                }
            }
        }
    }

    /** returns true only if folder.delete = true */
    public static boolean deleteElement(Element e) {
        return e.parameterEqual(CommonKeys.DELETE, true);
    }

}
