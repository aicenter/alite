package incubator.visprotocol.structure.key;

import incubator.visprotocol.structure.key.struct.ChangeFlag;

/**
 * Params for all elements and folders.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class CommonKeys {

    /**
     * True is used in update structure (elements, maybe folders), means that the element will be
     * deleted in vis layer. False is used by proxy layers (only folders), means that nothing in the
     * folder will be deleted, even if the proxy doesn't send any element to the folder.
     */
    public static final Typer<ChangeFlag> CHANGE = new Typer<ChangeFlag>("change");

}
