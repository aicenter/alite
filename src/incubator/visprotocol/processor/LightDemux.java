package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Pushes one instance to all subprocessors, has no pull.
 * 
 * Push: any structure
 * 
 * Pull: N/A
 * 
 * @author Ondrej Milenovsky
 * */
public class LightDemux extends MultipleProcessor {

    public LightDemux() {
        super();
    }

    @Deprecated
    @Override
    public Structure pull() {
        throw new RuntimeException("No pull");
    }

    /**
     * Same instance is pushed to all subprocessors, so processors must not change or store the
     * structure! Allowed is only when all processors do not store or change even part of the struct
     * and the last processor can store it or change it.
     */
    @Override
    public void push(Structure newPart) {
        for (StructProcessor pr : getProcessors()) {
            pr.push(newPart);
        }
    }
    
}
