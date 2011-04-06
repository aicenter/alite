package incubator.visprotocol.structure.key.struct;

/**
 * What will be done with an element/folder when updating.
 * 
 * @author Ondrej Milenovsky
 * */
public enum ChangeFlag {
    /** (not used) probably not usable, when update, element does not contain the parameter change */
    UPDATE,
    /** element/folder will be created */
    CREATE,
    /** element/folder will be deleted */
    DELETE,
    /**
     * Element/folder will not be deleted, even it is not sent from world. In folder means that
     * nothing in the folder will be deleted.
     */
    NOT_DELETE,

}
