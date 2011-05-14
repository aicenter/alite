package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.OneInputProcessor;
import incubator.visprotocol.processor.StateHolder;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;

/**
 * Storage for one structure, pull clears current state.
 * 
 * @author Ondrej Milenovsky
 * */
public class MemoryProtocol extends OneInputProcessor implements StateHolder {

    private Structure struct;

    public MemoryProtocol(StructProcessor input) {
        super(input);
    }

    @Override
    public Structure pull() {
        struct = super.pull();
        return struct;
    }

    @Override
    public Structure getState() {
        return struct;
    }
}
