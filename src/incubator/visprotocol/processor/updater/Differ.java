package incubator.visprotocol.processor.updater;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.struct.ChangeFlag;

/**
 * Stores last state and structure to send. When pushed new part, updates last state and differences
 * are added to the structure to send. When pulled, structure to send is returned and cleared. Makes
 * deep copy.
 * 
 * Push: whole world state (can be split to parts)
 * 
 * Pull: difference between last state (generated when push), changes current state
 * 
 * @author Ondrej Milenovsky
 */
public class Differ implements StructProcessor {

    private Structure state;
    private Structure updatePart;
    private boolean firstRun = true;
    
    public Differ() {
        state = new Structure();
        updatePart = new Structure();
    }

    public Structure getState() {
        return state;
    }

    /**
     * Push part of the world, deep copy.
     */
    @Override
    public void push(Structure newPart) {
        if (newPart.getTimeStamp() != null) {
            updatePart.setTimeStamp(newPart.getTimeStamp());
        }
        if (newPart.isEmpty()) {
            return;
        }
        if (state.isEmpty()) {
            diff(newPart.getRoot(), null, updatePart.getRoot(newPart.getRoot()));
            return;
        }
        diff(newPart.getRoot(), state.getRoot(newPart.getRoot()), updatePart.getRoot(newPart
                .getRoot()));
    }

    /**
     * Recursive create diff on folder/element, current element can be null, if state does not
     * conttain it.
     */
    private void diff(Element newE, Element currE, Element updateE) {
        if ((currE != null) && !newE.equals(currE)) {
            throw new RuntimeException("New folder/element " + newE.getId()
                    + " is not same id as folder/element " + currE.getId());
        }
        if((currE != null) && !changableElement(newE) && !firstRun) {
            return;
        }
        if (currE == null) {
            setCreate(updateE);
        } else {
            notDelete(updateE);
        }
        // diff params
        for (String p : newE.getParamIds()) {
            Object value = newE.getParameter(p);
            if ((currE == null) || !currE.parameterEqual(p, value)) {
                updateE.setParameter(p, value);
            }
        }
        // is folder, diff folders and elements
        if (newE instanceof Folder) {
            diffFolder((Folder) newE, (Folder) currE, (Folder) updateE);
        }
    }

    /** So far diffed as element/folder, now diff the folder addons. */
    private void diffFolder(Folder newF, Folder currF, Folder updateF) {
        // diff folders
        for (Folder f : newF.getFolders()) {
            if ((currF == null) || !currF.containsFolder(f)) {
                diff(f, null, updateF.getFolder(f));
            } else {
                diff(f, currF.getFolder(f), updateF.getFolder(f));
            }
        }
        // diff elements
        for (Element e : newF.getElements()) {
            if ((currF == null) || !currF.containsElement(e)) {
                diff(e, null, updateF.getElement(e));
            } else {
                diff(e, currF.getElement(e), updateF.getElement(e));
            }
            if (updateF.getElement(e).isEmpty()) {
                updateF.removeElement(e);
            }
        }
    }

    /** returns differences between two states, clears current state */
    @Override
    public Structure pull() {
        firstRun = false;
        Structure ret = updatePart;

        DiffUpdater updater = new DiffUpdater(state);
        updater.push(updatePart);
        state = updater.pull();

        clearUpdate();

        return ret;
    }

    /**
     * creates copy of current state, but with no parameters, the only parameter is delete
     * everywhere true
     */
    private void clearUpdate() {
        updatePart = new Structure();
        if (!state.isEmpty() && deletableElement(state.getRoot())
                && changableElement(state.getRoot())) {
            clearUpdate(updatePart.getRoot(state.getRoot()), state.getRoot());
        }
    }

    /** recursive clearing update */
    private void clearUpdate(Folder updF, Folder currF) {
        setDelete(updF);
        for (Folder f : currF.getFolders()) {
            if (deletableElement(f) && changableElement(f)) {
                clearUpdate(updF.getFolder(f), f);
            }
        }
        for (Element e : currF.getElements()) {
            if (deletableElement(e) && changableElement(e)) {
                setDelete(updF.getElement(e));
            }
        }
    }

    public static void notDelete(Element e) {
        e.removeParameter(CommonKeys.CHANGE);
    }

    public static void setCreate(Element e) {
        e.setParameter(CommonKeys.CHANGE, ChangeFlag.CREATE);
    }

    public static void setDelete(Element e) {
        e.setParameter(CommonKeys.CHANGE, ChangeFlag.DELETE);
    }

    /** returns false only if folder.change == not_delete */
    public static boolean deletableElement(Element e) {
        return !e.parameterEqual(CommonKeys.CHANGE, ChangeFlag.NOT_DELETE);
    }

    /** returns false only if folder.change == not_change */
    public static boolean changableElement(Element e) {
        return !e.parameterEqual(CommonKeys.NOT_CHANGE, true);
    }

}
