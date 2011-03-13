package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, pushes to the first processor. When pull, pulles from first to second, from second to
 * third... and returns pull from last.
 * 
 * Takes: any structure
 * 
 * Creates: product of last processor composed from the pipe
 * 
 * Forward: first -> second -> third -> ... -> last
 * 
 * @author Ondrej Milenovsky
 * */
public class PullForwarder extends PushForwarder {

    public PullForwarder(StructProcessor... processors) {
        super(processors);
    }

    /** pull from whole chain */
    @Override
    public Structure pull() {
        forward();
        return getProcessor(size() - 1).pull();
    }

    /** push to the first */
    @Override
    public void push(Structure newPart) {
        getProcessor(0).push(newPart);
    }

}
