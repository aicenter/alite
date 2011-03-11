package incubator.visprotocol.vis.differ;

import incubator.visprotocol.vis.structure.Structure;

/**
 * Interface for differs. Differ takes a part of structure, then generates
 * differing structure and updates actual state. Differ can be on server side
 * and in realtime case on client side.
 * 
 * @author Ondrej Milenovsky
 * */
public interface Differ {
    /** accepts new data, generates updates and updates actual state */
    public void push(Structure newPart);

    /** returns and clears the update part */
    public Structure pull();
}
