package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Stores the structure, so it can be pulled many times.
 * 
 * Push: any structure
 * 
 * Pull: the same instance of structure, does not change current state
 * 
 * @author Ondrej Milenovsky
 * */
public class LightStorage implements StructProcessor {

    private Structure state;

    public LightStorage() {
    }

    public LightStorage(Structure state) {
        this.state = state;
    }

    /** return current state, does not change it */
    @Override
    public Structure pull() {
        return state;
    }

    /** store the structure */
    @Override
    public void push(Structure newPart) {
        state = newPart;
    }

}
