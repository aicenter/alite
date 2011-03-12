package incubator.visprotocol.structprocessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import incubator.visprotocol.structure.Structure;

/**
 * Pushes one instance to all subprocessors, has no pull.
 * 
 * @author Ondrej Milenovsky
 * */
public class LightDemux implements StructProcessor {

    private final List<StructProcessor> processors;

    public LightDemux() {
        processors = new ArrayList<StructProcessor>(2);
    }

    public void addProcessor(StructProcessor pr) {
        processors.add(pr);
    }

    public void removeProcessor(StructProcessor pr) {
        int i = processors.indexOf(pr);
        if (i >= 0) {
            processors.remove(i);
        }
    }
    
    public Collection<StructProcessor> getProcessors() {
        return processors;
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
        for (StructProcessor pr : processors) {
            pr.push(newPart);
        }
    }

}
