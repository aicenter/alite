package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, stores the structure. When pull, returns deep copy of the structure.
 * 
 * Push: any structure
 * 
 * Pull: deep copy of actual structure, does not change current state
 * 
 * @author Ondrej Milenovsky
 * */
public class PullCopyStorage extends LightStorage {

    /** returns deep copy of current state, does not change it */
    @Override
    public Structure pull() {
        return super.pull().deepCopy();
    }

}
