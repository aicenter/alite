package incubator.visprotocol.processor.updater;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.utils.StructUtils;

/**
 * Holds current state, accepts parts of world. When push, returns current world state.
 * 
 * Push: differences between two world states
 * 
 * Pull: last world state, clears current state
 * 
 * @author Ondrej Milenovsky
 * */
// TODO bug: not working not_change on elements
public class MergeUpdater implements StructProcessor {

    // properties
    private boolean deleteFolders = false;
    private boolean deepCopyUpdating = false;
    private boolean deepCopyClearing = false;
    private boolean acceptPast = true;
    private boolean containsNotDelete = false;
    private boolean clearOnFirstPush = true;

    // state
    private Structure state;
    private boolean pulled = false;
    private boolean firstRun = true;

    public MergeUpdater() {
        this(new Structure(0L));
    }

    public MergeUpdater(Structure struct) {
        state = struct;
    }

    /** pull does not clear the state, but first push does */
    public void setClearOnFirstPush(boolean clearOnFirstPush) {
        this.clearOnFirstPush = clearOnFirstPush;
    }

    public boolean isClearOnFirstPush() {
        return clearOnFirstPush;
    }

    /**
     * If make deep copy or change current state if contains not changable element and clearing
     * state. If false, sets delete folders to false and clearOnFirstPush to true
     */
    public void setDeepCopyClearing(boolean deepCopyClearing) {
        this.deepCopyClearing = deepCopyClearing;
        if (!deepCopyClearing) {
            deleteFolders = false;
            clearOnFirstPush = true;
        }
    }

    public boolean isDeepCopyClearing() {
        return deepCopyClearing;
    }

    /** if make deep copy of new parts */
    public void setDeepCopy(boolean deepCopy) {
        this.deepCopyUpdating = deepCopy;
    }

    public boolean isDeepCopy() {
        return deepCopyUpdating;
    }

    /** If ever delete folders. If true, deep copy clearing must be true or throws exception! */
    public void setDeleteFolders(boolean deleteFolders) {
        if (deleteFolders && !deepCopyClearing) {
            throw new RuntimeException("Delete folders and deep copy clearing can't be both true.");
        }
        this.deleteFolders = deleteFolders;
    }

    public boolean isDeleteFolders() {
        return deleteFolders;
    }

    /** if accept or ignore parts with time <= last time */
    public void setAcceptPast(boolean acceptPast) {
        this.acceptPast = acceptPast;
    }

    public boolean isAcceptPast() {
        return acceptPast;
    }

    /** returns current state and clears it */
    @Override
    public Structure pull() {
        Structure ret = state;
        if (!clearOnFirstPush) {
            clearState();
        }
        pulled = true;
        firstRun = false;
        return ret;
    }

    /** merge current state with new part */
    @Override
    public void push(Structure newPart) {
        if (pulled && clearOnFirstPush) {
            clearState();
            pulled = false;
        }
        if (!acceptPast) {
            if (newPart.getTimeStamp() == null) {
                System.out.println("Warning: new part has no timestamp");
            } else if ((state.getTimeStamp() != null)
                    && (state.getTimeStamp() >= newPart.getTimeStamp())) {
                return;
            }
        }
        if (newPart.getTimeStamp() != null) {
            state.setTimeStamp(newPart);
        }
        if (newPart.isEmpty()) {
            return;
        }
        if (state.isEmpty()) {
            if (deepCopyUpdating) {
                state = newPart.deepCopy();
            } else {
                state.setRoot(newPart.getRoot());
            }
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
        if (!Differ.changableElement(currF) && !firstRun) {
            return;
        }
        currF.updateParams(newF);
        for (Folder f : newF.getFolders()) {
            if (needChange(currF, f) || firstRun) {
                updateDeletableFlag(f);
                if (deepCopyUpdating || currF.containsElement(f)) {
                    update(f, currF.getFolder(f));
                } else {
                    currF.addFolder(f);
                }
            }
        }
        for (Element e : newF.getElements()) {
            if (needChange(currF, e) || firstRun) {
                updateDeletableFlag(e);
                if (deepCopyUpdating || currF.containsElement(e)) {
                    currF.getElement(e).updateParams(e);
                } else {
                    currF.addElement(e);
                }
            }
        }
    }

    /** if folder needs to be updated by the element/folder */
    public static boolean needChange(Folder currF, Element e) {
        return !currF.containsElement(e)
                || (Differ.changableElement(currF.getElement(e)) && Differ.changableElement(e));

    }

    private void updateDeletableFlag(Element e) {
        if (!containsNotDelete && StructUtils.notClearable(e)) {
            containsNotDelete = true;
        }
    }

    /** prepare for next updating */
    public void clearState() {
        if (containsNotDelete) {
            if (deepCopyClearing) {
                state = StructUtils.makeDeepNotDeletableCopy(state);
            } else {
                StructUtils.removeClearableElements(state);
            }
        } else {
            if (deleteFolders) {
                state = new Structure(state.getTimeStamp());
            } else {
                state = StructUtils.copyFolders(state);
            }
        }
    }

}
