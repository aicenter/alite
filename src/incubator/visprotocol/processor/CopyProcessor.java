package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * When pull, returns deep copy of the structure.
 * 
 * @author Ondrej Milenovsky
 * */
public class CopyProcessor extends OneInputProcessor {

    public CopyProcessor(StructProcessor input) {
        super(input);
    }
    
    /** returns deep copy of current state, does not change it */
    @Override
    public Structure pull() {
        return super.pull().deepCopy();
    }

}
