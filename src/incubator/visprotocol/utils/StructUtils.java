package incubator.visprotocol.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;

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

    /** returns first leaf of structure */
    public static Folder getLeaf(Structure s) {
        return getLeaf(s.getRoot());
    }

    /** returns first leaf of folder */
    public static Folder getLeaf(Folder f) {
        if ((f == null) || f.getFolders().isEmpty()) {
            return f;
        }
        for (Folder f2 : f.getFolders()) {
            return getLeaf(f2);
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

}
