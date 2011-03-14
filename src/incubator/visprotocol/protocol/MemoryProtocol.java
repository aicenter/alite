package incubator.visprotocol.protocol;

import incubator.visprotocol.structprocessor.LightStorage;
import incubator.visprotocol.structure.Structure;

/**
 * Storage for one structure, pull clears current state.
 * 
 * @author Ondrej Milenovsky
 * */
public class MemoryProtocol extends LightStorage implements Protocol {

    public MemoryProtocol() {
    }

    @Override
    public Structure pull() {
        Structure ret = super.pull();
        push(null);
        return ret;
    }

}
