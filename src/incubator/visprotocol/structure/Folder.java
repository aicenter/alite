package incubator.visprotocol.structure;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Folder in the structure. Has one collection for elements and one for folders.
 * There are no methods such as addFolder, folder is created when asking for it.
 * Type is not necessary.
 * 
 * @author Ondrej Milenovsky
 * */
public class Folder extends Element {

    private static final long serialVersionUID = 6708406764600130786L;

    public static final String DEFAULT_TYPE = "Folder";

    private final LinkedHashMap<String, Element> elements;
    private final LinkedHashMap<String, Folder> folders;

    public Folder(String id) {
        this(id, DEFAULT_TYPE);
    }

    public Folder(String id, String type) {
        super(id, type);
        elements = new LinkedHashMap<String, Element>();
        folders = new LinkedHashMap<String, Folder>();
    }

    public boolean containsFolder(String id) {
        return folders.containsKey(id);
    }

    public boolean containsElement(String id) {
        return elements.containsKey(id);
    }

    /** returns folder, if not exists, creates it */
    public Folder getFolder(String id) {
        return getFolder(id, DEFAULT_TYPE);
    }

    /** returns folder, if not exists, creates it, type is used when creating */
    public Folder getFolder(String id, String type) {
        if (folders.containsKey(id)) {
            return folders.get(id);
        }
        updated();
        Folder f = new Folder(id);
        folders.put(id, f);
        return f;
    }

    /**
     * returns element, if not exists, creates it, type is used only when
     * creating new element
     */
    public Element getElement(String id, String type) {
        if (elements.containsKey(id)) {
            return elements.get(id);
        }
        updated();
        Element e = new Element(id, type);
        elements.put(id, e);
        return e;
    }

    public Set<String> getFolderIds() {
        return folders.keySet();
    }

    public Collection<Folder> getFolders() {
        return folders.values();
    }

    public Set<String> getElementIds() {
        return elements.keySet();
    }

    public Collection<Element> getElements() {
        return elements.values();
    }

}
