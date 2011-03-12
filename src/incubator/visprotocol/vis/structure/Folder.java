package incubator.visprotocol.vis.structure;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Folder in the structure. Has one collection for elements and one for folders. Type is not
 * necessary.
 * 
 * @author Ondrej Milenovsky
 * */
public class Folder extends Element {

    public static final String TYPE = "Folder";

    private static final long serialVersionUID = 6708406764600130786L;

    private final LinkedHashMap<String, Element> elements;
    private final LinkedHashMap<String, Folder> folders;

    public Folder(String id) {
        super(id, null);
        elements = new LinkedHashMap<String, Element>(2);
        folders = new LinkedHashMap<String, Folder>(2);
    }

    public boolean containsFolder(String id) {
        return folders.containsKey(id);
    }

    public boolean containsFolder(Folder f) {
        return folders.containsKey(f.getId());
    }

    public boolean containsElement(String id) {
        return elements.containsKey(id);
    }

    public boolean containsElement(Element e) {
        return elements.containsKey(e.getId());
    }

    /** returns folder, if not exists, creates it */
    public Folder getFolder(String id) {
        if (folders.containsKey(id)) {
            return folders.get(id);
        }
        Folder f = new Folder(id);
        folders.put(id, f);
        return f;
    }

    /** returns folder, if not exists, creates it */
    public Folder getFolder(Folder f) {
        return getFolder(f.getId());
    }

    /** if the element does not exists, throw exception */
    public Element getElement(String id) {
        if (!elements.containsKey(id)) {
            throw new RuntimeException("Folder " + getId() + " does not contain element " + id);
        }
        return elements.get(id);
    }

    /**
     * returns element, if not exists, creates it, type is used only when creating new element
     */
    public Element getElement(String id, String type) {
        if (elements.containsKey(id)) {
            return elements.get(id);
        }
        Element e = new Element(id, type);
        elements.put(id, e);
        return e;
    }

    /**
     * returns element, if not exists, creates it, type is will be same as requested element
     */
    public Element getElement(Element e) {
        return getElement(e.getId(), e.getType());
    }

    public Element removeElement(String id) {
        return elements.remove(id);
    }

    public Element removeElement(Element e) {
        return elements.remove(e.getId());
    }

    public Folder removeFolder(String id) {
        return folders.remove(id);
    }

    public Folder removeFolder(Folder f) {
        return folders.remove(f.getId());
    }

    public Collection<String> getFolderIds() {
        return folders.keySet();
    }

    public Collection<Folder> getFolders() {
        return folders.values();
    }

    public Collection<String> getElementIds() {
        return elements.keySet();
    }

    public Collection<Element> getElements() {
        return elements.values();
    }

    public void addFolder(Folder f) {
        folders.put(f.getId(), f);
    }

    public void addElement(Element e) {
        elements.put(e.getId(), e);
    }

    public boolean isEmpty() {
        return elements.isEmpty() && folders.isEmpty() && super.isEmpty();
    }

    /** Clears parameters, folders and elements */
    @Override
    public void clear() {
        super.clear();
        elements.clear();
        folders.clear();
    }

    public void clearFolders() {
        folders.clear();
    }

    public void clearElements() {
        elements.clear();
    }

    /** Makes deep copy of the folder, not of element parameters! */
    public Folder deepCopy() {
        Folder ret = (Folder) super.deepCopy();
        for (Folder f : folders.values()) {
            ret.addFolder(f.deepCopy());
        }
        for (Element e : elements.values()) {
            ret.addElement(e.deepCopy());
        }
        return ret;
    }

    /** Copied also all elements and folders, shallow copy! */
    @Override
    @Deprecated
    public void update(Element e) {
        super.update(e);
        if (e instanceof Folder) {
            Folder f = (Folder) e;
            folders.putAll(f.folders);
            elements.putAll(f.elements);
        }
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean equalsDeep(Object obj) {
        if (!super.equalsDeep(obj)) {
            return false;
        }
        Folder f2 = (Folder) obj;
        if ((elements.size() != f2.elements.size()) || (folders.size() != f2.folders.size())) {
            return false;
        }
        for (Element e : elements.values()) {
            if (!f2.containsElement(e)) {
                return false;
            }
            if (!e.equalsDeep(f2.getElement(e))) {
                return false;
            }
        }
        for (Folder f : folders.values()) {
            if (!f2.containsFolder(f)) {
                return false;
            }
            if (!f.equalsDeep(f2.getFolder(f))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Folder)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        return getId().equals(((Folder) obj).getId());
    }

    public String print() {
        return print(0);
    }

    public String print(int spaces) {
        String ret = super.print(spaces);
        String s = getSpaces(spaces);
        if (!getElements().isEmpty()) {
            ret += s + "---elements---\n";
            for (String ed : getElementIds()) {
                Element e = getElement(ed);
                ret += e.print(spaces + 2);
            }
        }
        if (!getFolders().isEmpty()) {
            ret += s + "---folders---\n";
            for (String fd : getFolderIds()) {
                Folder f = getFolder(fd);
                ret += f.print(spaces + 2);
            }
        }
        return ret;
    }

}
