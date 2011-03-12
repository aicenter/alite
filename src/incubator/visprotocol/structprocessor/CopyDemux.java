package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * Pushes deep copy of the instance to all subprocessors, has no pull.
 * 
 * @author Ondrej Milenovsky
 * */
public class CopyDemux extends LightDemux {

    public CopyDemux() {
        super();
    }

    /** Deep copy is pushed to every subprocessor, so they can store or change it */
    @Override
    public void push(Structure newPart) {
        for (StructProcessor pr : getProcessors()) {
            pr.push(newPart.deepCopy());
        }
    }

}
