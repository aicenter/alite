package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, stores deep copy of the structure. When pull, returns stored struct.
 * 
 * Push: any structure
 * 
 * Pull: stored deep copy of the structure (copy is not created when pull), does not change current
 * state
 * 
 * @author Ondrej Milenovsky
 * */
public class PushCopyStorage extends LightStorage {

    /** store deep copy of the structure */
    @Override
    public void push(Structure newPart) {
        super.push(newPart.deepCopy());
    }
}
