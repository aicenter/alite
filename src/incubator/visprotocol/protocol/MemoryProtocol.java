package incubator.visprotocol.protocol;

import incubator.visprotocol.structure.Structure;

/**
 * Storage for one structure, pull clears state.
 * 
 * @author Ondrej Milenovsky
 * */
public class MemoryProtocol implements Protocol {

    private Structure struct;

    public MemoryProtocol() {
    }

    @Override
    public void push(Structure struct) {
        this.struct = struct;
    }

    @Override
    public Structure pull() {
        Structure ret = struct;
        struct = null;
        return ret;
    }

}
