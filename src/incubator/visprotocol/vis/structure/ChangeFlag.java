package incubator.visprotocol.vis.structure;

/**
 * type of change in element
 * 
 * @author Ondrej Milenovsky
 * */
public enum ChangeFlag {
    /** not defined (no change or update) */
    NONE,
    /** not changed */
    NO_CHANGE,
    /** updated some parameters */
    UPDATE,
    /** element created */
    CREATE,
    /** element deleted */
    DELETE
}
