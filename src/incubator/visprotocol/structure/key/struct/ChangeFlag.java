package incubator.visprotocol.structure.key.struct;

public enum ChangeFlag {
    /** probably not usable, when update, element does not contain the parameter */
    UPDATE,
    /** element/folder will be created */
    CREATE,
    /** element/folder will be deleted */
    DELETE,
    /**
     * element/folder will not be deleted, even it is not sent from world; in folder means that
     * nothing in the folder will be deleted, not used for elements
     */
    NOT_DELETE
}
