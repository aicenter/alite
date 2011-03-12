package incubator.visprotocol.vis.differ;

import incubator.visprotocol.vis.structure.Structure;

public interface Updater {
    /**
     * Updates current state with new update part. Must make deep copy of the new part! (except
     * parameters)
     */
    public void update(Structure newPart);

    /** returns current state */
    public Structure getState();
}
