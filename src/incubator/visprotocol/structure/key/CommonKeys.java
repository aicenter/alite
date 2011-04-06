package incubator.visprotocol.structure.key;

import java.util.HashSet;
import java.util.Set;

import incubator.visprotocol.structure.key.struct.ChangeFlag;

/**
 * Params for all elements and folders.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class CommonKeys {

    public static final Set<String> COMMON_PARAMS = new HashSet<String>();

    /**
     * True is used in update structure (elements, maybe folders), means that the element will be
     * deleted in vis layer. False is used by proxy layers (only folders), means that nothing in the
     * folder will be deleted, even if the proxy doesn't send any element to the folder.
     */
    public static final Typer<ChangeFlag> CHANGE = new Typer<ChangeFlag>("change");

    /** for static elements/folders */
    public static final Typer<Boolean> NOT_CHANGE = new Typer<Boolean>("not_change");

    /** Part of whole world state */
    public static final String STRUCT_PART = "part";
    /** Diff from differ */
    public static final String STRUCT_DIFF = "diff";
    /** Whole world state */
    public static final String STRUCT_COMPLETE = "state";

    static {
        COMMON_PARAMS.add(CHANGE.id);
        COMMON_PARAMS.add(NOT_CHANGE.id);
    }

}
