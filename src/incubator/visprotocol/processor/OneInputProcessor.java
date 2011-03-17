package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Implementation of StructProcessor with one input.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class OneInputProcessor implements StructProcessor {

    private final StructProcessor input;

    public OneInputProcessor(StructProcessor input) {
        this.input = input;
    }

    @Override
    public Structure pull() {
        return input.pull();
    }

}
