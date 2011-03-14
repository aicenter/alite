package incubator.visprotocol.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.Typer;

/**
 * Generating specified structures.
 * 
 * String path syntax: first char is separator, example: ".root.vehicles.cars" is same as
 * "-root-vehicles-cars" and it is even same as ".root....vehicles.cars......" but not same as
 * "root.vehicles.cars" (here the separator will be 'r')
 * 
 * @author Ondrej Milenovsky
 * */
public class StructUtils {

    /** if the element contains the parameter, update the value, else return old value */
    public static <C> C updateValue(Element e, Typer<C> typer, C value) {
        if (e.containsParameter(typer)) {
            value = e.getParameter(typer);
        }
        return value;
    }

    /** returns first leaf folder of structure */
    public static Folder getLeafFolder(Structure s) {
        return getLeafFolder(s.getRoot());
    }

    /** returns first leaf folder of folder */
    public static Folder getLeafFolder(Folder f) {
        if ((f == null) || f.getFolders().isEmpty()) {
            return f;
        }
        for (Folder f2 : f.getFolders()) {
            return getLeafFolder(f2);
        }
        return null;
    }

    /** returns first leaf element of structure */
    public static Element getLeafElement(Structure s) {
        return getLeafElement(getLeafFolder(s.getRoot()));
    }

    /** returns first leaf element of folder */
    public static Element getLeafElement(Folder f) {
        f = getLeafFolder(f);
        for (Element e : f.getElements()) {
            return e;
        }
        return null;
    }

    public static Structure createStructure(String[] path) {
        Structure ret = new Structure();
        ret.setRoot(createFolder(path));
        return ret;
    }

    public static Structure createStructure(String path) {
        Structure ret = new Structure();
        ret.setRoot(createFolder(path));
        return ret;
    }

    public static Folder createFolder(String[] path) {
        Folder ret = null;
        Folder last = null;
        for (String s : Arrays.asList(path)) {
            Folder f = new Folder(s);
            if (ret == null) {
                ret = f;
            }
            if (last != null) {
                last.addFolder(f);
            }
            last = f;
        }
        return ret;
    }

    public static Folder createFolder(String path) {
        return createFolder(getFolderIds(path).toArray(new String[0]));
    }

    public static List<String> getFolderIds(String path) {
        char sep = path.charAt(0);
        List<String> ret = new ArrayList<String>(3);
        String actual = "";
        for (int i = 1; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == sep) {
                if (!actual.equals("")) {
                    ret.add(actual);
                    actual = "";
                }
            } else {
                actual += c;
            }
        }
        if (!actual.equals("")) {
            ret.add(actual);
        }
        return ret;
    }

    /** creates deep copy of folders (without elements and params), timestamp is copied */
    public static Structure copyFolders(Structure s) {
        Structure ret = new Structure(s);
        if (s.isEmpty()) {
            return ret;
        }
        ret.setRoot(copyFolders(s.getRoot()));
        return ret;
    }

    /** creates deep copy of folders (without elements and params) */
    private static Folder copyFolders(Folder f) {
        Folder ret = new Folder(f);
        for (Folder f2 : f.getFolders()) {
            ret.addFolder(copyFolders(f2));
        }
        return ret;
    }

}
