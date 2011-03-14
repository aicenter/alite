package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.utils.StructUtils;

/**
 * Holds current state, accepts parts of world. When push, returns current world state. Default
 * setting is not to delete folders.
 * 
 * Push: differences between two world states
 * 
 * Pull: last world state, clears current state
 * 
 * @author Ondrej Milenovsky
 * */
public class MergeUpdater implements StructProcessor {

    private Structure state;
    private boolean deleteFolders;
    private boolean deepCopy;

    public MergeUpdater() {
        this(new Structure(0L));
        clearState();
    }

    public MergeUpdater(Structure struct) {
        deleteFolders = false;
        deepCopy = false;
        state = struct;
    }

    public void setDeleteFolders(boolean deleteFolders) {
        this.deleteFolders = deleteFolders;
    }

    public boolean isDeleteFolders() {
        return deleteFolders;
    }

    /** returns current state and clears it */
    @Override
    public Structure pull() {
        Structure ret = state;
        clearState();
        return ret;
    }

    /** merge current state with new part */
    @Override
    public void push(Structure newPart) {
        if (newPart.getTimeStamp() == null) {
            //System.out.println("Warning: new part has no timestamp");
        } else if ((state.getTimeStamp() != null)
                && (state.getTimeStamp() >= newPart.getTimeStamp())) {
            System.out.println("Warning: Current time: " + state.getTimeStamp()
                    + " >= update time " + newPart.getTimeStamp());
        }
        state.setTimeStamp(newPart);
        if (newPart.isEmpty()) {
            return;
        }
        if (state.isEmpty()) {
            state.setRoot(newPart.getRoot());
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
        for (Folder f : newF.getFolders()) {
            if (deepCopy || currF.containsElement(f)) {
                update(f, currF.getFolder(f));
            } else {
                currF.addFolder(f);
            }
        }
        for (Element e : newF.getElements()) {
            if (deepCopy || currF.containsElement(e)) {
                currF.getElement(e).updateParams(e);
            } else {
                currF.addElement(e);
            }
        }
    }

    /** prepare for next updating */
    private void clearState() {
        if (deleteFolders) {
            state = new Structure(state.getTimeStamp());
        } else {
            state = StructUtils.copyFolders(state);
        }
    }

}
