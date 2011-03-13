package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, stores the structure. When pull, returns deep copy of the structure.
 * 
 * Takes: any structure
 * 
 * Creates: deep copy of actual structure
 * 
 * @author Ondrej Milenovsky
 * */
public class PullCopier implements StructProcessor {

    private Structure state;

    public PullCopier() {
    }

    @Override
    public Structure pull() {
        return state.deepCopy();
    }

    @Override
    public void push(Structure newPart) {
        state = newPart;
    }

}
