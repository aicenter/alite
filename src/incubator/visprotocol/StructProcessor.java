package incubator.visprotocol;

import incubator.visprotocol.structure.Structure;

/**
 * Anything that somehow processes the structure.
 * 
 * @author Ondrej Milenovsky
 * */
public interface StructProcessor {
    public void push(Structure newPart);

    public Structure pull();

}
