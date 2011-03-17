package incubator.visprotocol.processor;

import java.util.Arrays;
import java.util.List;

import incubator.visprotocol.structure.Structure;

/**
 * Pulls all inputs, returns null.
 * 
 * @author Ondrej Milenovsky
 * */
public class MultiplePuller extends MultipleInputProcessor {

    public MultiplePuller(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public MultiplePuller(List<StructProcessor> inputs) {
        super(inputs);
    }

    /** pull every input, return null */
    @Override
    public Structure pull() {
        for (StructProcessor pr : getInputs()) {
            pr.pull();
        }
        return null;
    }

}
