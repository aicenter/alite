package incubator.visprotocol.vis.output.painter;

import incubator.visprotocol.structprocessor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;

import java.util.HashMap;
import java.util.Map;

/**
 * This painter ignores params of folders, default setting: first paints elements, then subfolders
 * of folder.
 * 
 * Takes: whole structure to paint
 * 
 * Creates: N/A
 * 
 * @author Ondrej Milenovsky
 * */
public class RootPainter implements GroupPainter, StructProcessor {

    private final Map<String, Painter> painters;
    private boolean firstElements;

    public RootPainter() {
        painters = new HashMap<String, Painter>();
        firstElements = true;
    }

    public void setFirstElements(boolean firstElements) {
        this.firstElements = firstElements;
    }

    public boolean isFirstElements() {
        return firstElements;
    }

    @Override
    public void addPainter(String elementType, Painter painter) {
        painters.put(elementType, painter);
    }

    @Override
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
        if (firstElements) {
            paintElements(f);
            paintFolders(f);
        } else {
            paintFolders(f);
            paintElements(f);
        }
    }

    private void paintFolders(Folder f) {
        for (Folder f2 : f.getFolders()) {
            paint(f2);
        }
    }

    private void paintElements(Folder f) {
        for (Element e : f.getElements()) {
            if (painters.containsKey(e.getType())) {
                painters.get(e.getType()).paint(e);
            }
        }
    }

    @Override
    @Deprecated
    public Structure pull() {
        throw new RuntimeException("Not used");
    }

    @Override
    public void push(Structure newPart) {
        paint(newPart);
    }

}
