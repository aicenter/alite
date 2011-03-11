package incubator.visprotocol.vis.differ;

import incubator.visprotocol.vis.structure.Structure;

public interface Differ {
    /** accepts new data, generates updates and updates actual state */
    public void push(Structure newPart);

    /** returns and clears the update part */
    public Structure pull();
}
