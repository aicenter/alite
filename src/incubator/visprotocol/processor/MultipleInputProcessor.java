package incubator.visprotocol.processor;

import java.util.Collection;
import java.util.List;

/**
 * Implementation of StructProcessor with ArrayList of inputs.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class MultipleInputProcessor implements StructProcessor {

    private final List<StructProcessor> inputs;

    public MultipleInputProcessor(List<StructProcessor> inputs) {
        this.inputs = inputs;
    }
    
    public void addInput(StructProcessor input) {
        inputs.add(input);
    }

    protected boolean isEmpty() {
        return inputs.isEmpty();
    }

    protected Collection<StructProcessor> getInputs() {
        return inputs;
    }

}
