package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.utils.ProcessorUtils;

import java.util.List;

/**
 * Pulls all inputs, returns null.
 * 
 * @author Ondrej Milenovsky
 * */
public class MultiplePuller extends MultipleInputProcessor {

    public MultiplePuller(StructProcessor... inputs) {
        this(ProcessorUtils.asList(inputs));
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
