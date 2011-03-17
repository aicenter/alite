package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Returns state from input, not pull, does not change the inputs inner state.
 * 
 * @author Ondrej Milenovsky
 * */
public class StateGetter implements StructProcessor {
    private final StateHolder input;

    public StateGetter(StateHolder stateHolder) {
        this.input = stateHolder;
    }

    @Override
    public Structure pull() {
        return input.getState();
    }
}
