package incubator.visprotocol.vis.output.painter;

import java.util.Map;

import incubator.visprotocol.structure.Structure;

/**
 * @author Ondrej Milenovsky
 * */
public interface GroupPainter {
    public void addPainter(String elementType, Painter painter);

    public void addPainters(Map<String, Painter> map);

    public void paint(Structure struct);
}
