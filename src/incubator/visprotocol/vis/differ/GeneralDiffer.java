package incubator.visprotocol.vis.differ;

import incubator.visprotocol.vis.structure.Element;
import incubator.visprotocol.vis.structure.Folder;
import incubator.visprotocol.vis.structure.Structure;

import java.util.ArrayList;
import java.util.List;

// TODO test regular use and all exception cases
// TODO deleting
/**
 * Stores last state and structure to send. When pushed new part, updates last state and differences
 * are added to the structure to send. When pulled, structure to send is returned and cleared.
 * 
 * @author Ondrej Milenovsky
 */
public class GeneralDiffer implements Differ {

    private Structure state;
    private Structure newState;

    public GeneralDiffer() {
        state = new Structure();
        newState = new Structure();
    }

    /** the newPart may be inserted into the differ, do not use it any more ! */
    @Override
    public void push(Structure newPart) {
        if (newPart.isEmpty()) {
            return;
        }
        if (state.isEmpty()) {
            state.update(newPart);
            newState.update(newPart);
        } else {
            update(newPart.getRoot(), state.getRoot(newPart.getRoot()), new ArrayList<String>());
        }
    }

    /**
     * Update deeper from current folder. New and current folders must be on same path, path is
     * stored in the list excluding current folder id
     */
    private void update(Folder newF, Folder current, List<String> path) {
        if (newF.equals(current)) {
            throw new RuntimeException("New folder " + newF.getId() + " is not same id as folder "
                    + current.getId());
        }
        path.add(newF.getId());
        Folder updateFolder = null;

        for (Folder f : newF.getFolders()) {
            // whole subfolder missing, add it
            if (!current.containsFolder(f.getId())) {
                current.addFolder(f);
                if (updateFolder == null) {
                    updateFolder = getFolderFromUpdate(path);
                }
                updateFolder.addFolder(f);
            } else {
                update(f, current.getFolder(f), path);
            }
        }

        for (Element e : newF.getElements()) {
            // whole element missing, add it
            if (!current.containsElement(e)) {
                current.addElement(e);
                if (updateFolder == null) {
                    updateFolder = getFolderFromUpdate(path);
                }
                updateFolder.addElement(e);
            } else {
                update(e, current.getElement(e), path);
            }
        }
        path.remove(path.size() - 1);
    }

    /** update element */
    private void update(Element newE, Element current, List<String> path) {
        if (newE.equals(current)) {
            throw new RuntimeException("New element " + newE.getId()
                    + " is not same type and id as element " + current.getId());
        }
        Element updateElement = null;
        for (String id : newE.getParamIds()) {
            Object value = newE.getParameter(id);
            Object currVal = current.getParameter(id);
            if (((value == null) && (currVal != null))
                    || ((value != currVal) && !value.equals(currVal))) {
                current.setParameter(id, value);
                if (updateElement == null) {
                    updateElement = getFolderFromUpdate(path).getElement(newE);
                }
                updateElement.setParameter(id, value);
            }
        }
        current.update(newE);
    }

    /**
     * returns folder from update struct on specified path (folders created if not exist)
     */
    private Folder getFolderFromUpdate(List<String> path) {
        Folder f = newState.getRoot(path.get(0));
        boolean first = true;
        for (String id : path) {
            if (first) {
                first = false;
            } else {
                f = f.getFolder(id);
            }
        }
        return f;
    }

    @Override
    public Structure pull() {
        Structure ret = newState;
        newState = new Structure();
        return ret;
    }

}
