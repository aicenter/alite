package incubator.visprotocol.processor.updater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StateHolder;
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
// TODO bug: first comes empty struct, then .folder.folder which is not_change, will be ignored
public class DiffUpdater extends MultipleInputProcessor implements StateHolder {

    // properties
    private boolean deleteFolders = false;
    private boolean acceptPast = false;

    // state
    private Structure state = new Structure();
    private boolean firstRun = true;

    public DiffUpdater(Structure state) {
        super(new ArrayList<StructProcessor>(0));
        this.state = state;
    }

    public DiffUpdater(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public DiffUpdater(List<StructProcessor> inputs) {
        super(inputs);
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
        for (StructProcessor pr : getInputs()) {
            push(pr.pull());
        }

        firstRun = false;
        state.setType(CommonKeys.STRUCT_STATE);
        return state;
    }

    public void push(Structure newPart) {
        if (!newPart.isType(CommonKeys.STRUCT_DIFF)) {
            System.err.println("DiffUpdater should accept only diffs, not " + newPart.getType());
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
        if (!Differ.changableElement(currF) && !firstRun) {
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
                if (deleteElement(e)
                        && (!currF.containsElement(e) || currF.getElement(e).parameterEqual(
                                CommonKeys.NOT_CHANGE, true))) {
                    currF.removeElement(e);
                } else if (Differ.changableElement(currF.getElement(e)) || firstRun) {
                    currF.getElement(e).updateParams(e);
                }
            }
        }
    }

    @Override
    public Structure getState() {
        return state;
    }
    
    /** returns true only if folder.change == delete */
    public static boolean deleteElement(Element e) {
        return e.parameterEqual(CommonKeys.CHANGE, ChangeFlag.DELETE);
    }

}
