package incubator.visprotocol.vis.output.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;

import java.util.HashMap;
import java.util.Map;

/**
 * This painter ignores params of folders, first paints sub folders, then elements of folder.
 * 
 * @author Ondrej Milenovsky
 * */
public class GroupPainter implements ThePainter {

    private final Map<String, Painter> painters;

    public GroupPainter() {
        painters = new HashMap<String, Painter>();
    }

    public void addPainter(String elementType, Painter painter) {
        painters.put(elementType, painter);
    }

    public void addPainters(Map<String, Painter> map) {
        painters.putAll(map);
    }

    @Override
    public void paint(Structure struct) {
        if (!struct.isEmpty()) {
            paint(struct.getRoot());
        }
    }

    private void paint(Folder f) {
        if ((f.getElements().size() > 0) && (f.getFolders().size() > 0)) {
            System.err.println("Warning: " + f.getId()
                    + " has folders and elements, first are drawn elements, then folders");
        }
        for (Element e : f.getElements()) {
            if (painters.containsKey(e.getType())) {
                painters.get(e.getType()).paint(e);
            }
        }
        for (Folder f2 : f.getFolders()) {
            paint(f2);
        }
    }

}
