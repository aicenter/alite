package incubator.visprotocol.vis.output.painter;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
public class TreePainter extends MultipleInputProcessor implements GroupPainter {

    private final Map<String, Painter> painters;
    private boolean firstElements;

    public TreePainter(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public TreePainter(List<StructProcessor> inputs) {
        super(inputs);
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

    /** paints and returns null */
    @Override
    public Structure pull() {
        for (StructProcessor pr : getInputs()) {
            push(pr.pull());
        }
        return null;
    }

    public void push(Structure newPart) {
        if (!newPart.isType(CommonKeys.STRUCT_PART, CommonKeys.STRUCT_COMPLETE)) {
            System.err.println("RootPainter should accept whole or a part of world, not "
                    + newPart.getType());
        }
        paint(newPart);
    }

}
