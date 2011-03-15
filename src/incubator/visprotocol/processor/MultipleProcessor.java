package incubator.visprotocol.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Implementation of ProcessorHolder using ArrayList.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class MultipleProcessor implements ProcessorHolder {

    private final List<StructProcessor> processors;

    public MultipleProcessor() {
        processors = new ArrayList<StructProcessor>(2);
    }

    public MultipleProcessor(StructProcessor... processors) {
        this.processors = Arrays.asList(processors);
    }

    public MultipleProcessor(List<StructProcessor> processors) {
        this.processors = processors;
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

    public boolean isEmpty() {
        return processors.isEmpty();
    }

    public int size() {
        return processors.size();
    }
    
    public StructProcessor getProcessor(int i) {
        return processors.get(i);
    }

    public Collection<StructProcessor> getProcessors() {
        return processors;
    }

}
