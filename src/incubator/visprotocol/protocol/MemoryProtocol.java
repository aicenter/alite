package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.OneInputProcessor;
import incubator.visprotocol.processor.StructProcessor;

/**
 * Storage for one structure, pull clears current state.
 * 
 * @author Ondrej Milenovsky
 * */
public class MemoryProtocol extends OneInputProcessor {
    
    public MemoryProtocol(StructProcessor input) {
        super(input);
    }
}
