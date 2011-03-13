package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, pushes to the first processor, then from first to second, from second to third....
 * When pull, pulles from the last processor.
 * 
 * @author Ondrej Milenovsky
 * */
public class PushForwarder extends MultipleProcessor {

    /** Pull from the last subprocessors */
    @Override
    public Structure pull() {
        return getProcessor(size() - 1).pull();
    }

    /** Push to all subprocessors */
    @Override
    public void push(Structure newPart) {
        Structure struct = newPart;
        for (int i = 0; i < size(); i++) {
            StructProcessor pr = getProcessor(i);
            pr.push(struct);
            if (i < size() - 1) {
                struct = pr.pull();
            }
        }
    }
}
