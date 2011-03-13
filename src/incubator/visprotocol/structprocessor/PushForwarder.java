package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, pushes to the first processor, then from first to second, from second to third....
 * When pull, pulles from the last processor.
 * 
 * @author Ondrej Milenovsky
 * */
public class PushForwarder extends MultipleProcessor implements Forwarder {

    public PushForwarder(StructProcessor... processors) {
        super(processors);
    }

    /** Pull from the last subprocessor */
    @Override
    public Structure pull() {
        return getProcessor(size() - 1).pull();
    }

    /** Push to whole chain */
    @Override
    public void push(Structure newPart) {
        getProcessor(0).push(newPart);
        forward();
    }

    /** first -> second -> third -> ... -> last */
    @Override
    public void forward() {
        Structure struct = getProcessor(0).pull();
        for (int i = 1; i < size(); i++) {
            StructProcessor pr = getProcessor(i);
            pr.push(struct);
            if (i < size() - 1) {
                struct = pr.pull();
            }
        }
    }

}
