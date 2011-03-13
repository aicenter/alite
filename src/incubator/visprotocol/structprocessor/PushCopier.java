package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, stores deep copy of the structure. When pull, returns stored struct.
 * 
 * Takes: any structure
 * 
 * Creates: stored deep copy of the structure structure
 * 
 * @author Ondrej Milenovsky
 * */
public class PushCopier implements StructProcessor {

    private Structure state;

    public PushCopier() {
    }

    @Override
    public Structure pull() {
        return state;
    }

    @Override
    public void push(Structure newPart) {
        state = newPart.deepCopy();
    }

}
