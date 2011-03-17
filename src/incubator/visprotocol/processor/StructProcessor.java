package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Anything that somehow processes the structure. Some processors do not use push or pull.
 * 
 * @author Ondrej Milenovsky
 * */
public interface StructProcessor {
    public Structure pull();
}
