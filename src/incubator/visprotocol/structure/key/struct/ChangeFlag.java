package incubator.visprotocol.structure.key.struct;

public enum ChangeFlag {
    /** (not used) probably not usable, when update, element does not contain the parameter change */
    UPDATE,
    /** element/folder will be created */
    CREATE,
    /** element/folder will be deleted */
    DELETE,
    /** (not used) Element/folder params won't be updated */
    NOT_UPDATE,
    /** (not used) */
    NOT_CREATE,
    /**
     * Element/folder will not be deleted, even it is not sent from world. In folder means that
     * nothing in the folder will be deleted.
     */
    NOT_DELETE,
    /**
     * Element/folder will be once created and then never updated or deleted, used for speed up. In
     * folder means that nothing in the folder will be changed.
     */
    NOT_CHANGE,

}
