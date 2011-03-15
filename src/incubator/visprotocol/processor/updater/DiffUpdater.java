package incubator.visprotocol.processor.updater;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.struct.ChangeFlag;

/**
 * Holds current state, accepts updates from differ and updates current state. Default setting is
 * not to delete folders. Makes deep copy of update part.
 * 
 * Push: whole world state (can be split to parts)
 * 
 * Pull: last world state, does not change current state
 * 
 * @author Ondrej Milenovsky
 * */
public class DiffUpdater implements StructProcessor {

    private Structure state;
    private boolean deleteFolders;
    private boolean acceptPast = false;

    public DiffUpdater() {
        this(new Structure());
    }

    public DiffUpdater(Structure struct) {
        deleteFolders = false;
        state = struct;
    }

    public void setDeleteFolders(boolean deleteFolders) {
        this.deleteFolders = deleteFolders;
    }

    public boolean isDeleteFolders() {
        return deleteFolders;
    }

    public void setAcceptPast(boolean acceptPast) {
        this.acceptPast = acceptPast;
    }

    public boolean isAcceptPast() {
        return acceptPast;
    }

    /** just returns current state, does not change it */
    @Override
    public Structure pull() {
        return state;
    }

    @Override
    public void push(Structure newPart) {
        if (!acceptPast) {
            if (newPart.getTimeStamp() == null) {
                System.out.println("Warning: new part has no timestamp");
            } else if ((state.getTimeStamp() != null)
                    && (state.getTimeStamp() >= newPart.getTimeStamp())) {
                return;
            }
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
        if (!Differ.changableElement(currF)) {
            return;
        }
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
                } else if (Differ.changableElement(currF.getElement(e))) {
                    currF.getElement(e).updateParams(e);
                }
            }
        }
    }

    /** returns true only if folder.change == delete */
    public static boolean deleteElement(Element e) {
        return e.parameterEqual(CommonKeys.CHANGE, ChangeFlag.DELETE);
    }

}
