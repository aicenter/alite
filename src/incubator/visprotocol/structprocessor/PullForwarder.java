package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

/**
 * When push, pushes to the first processor. When pull, pulles from first to second, from second to
 * third... and returns pull from last.
 * 
 * @author Ondrej Milenovsky
 * */
public class PullForwarder extends MultipleProcessor {

    @Override
    public Structure pull() {
        Structure struct = null;
        for (StructProcessor pr : getProcessors()) {
            if (struct != null) {
                pr.push(struct);
            }
            struct = pr.pull();
        }
        return struct;
    }

    @Override
    public void push(Structure newPart) {
        getProcessor(0).push(newPart);
    }

}
