package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Vis2DBasicPainters {

    /** all element types with parameters that are used by 2D vis */
    public static final Map<String, Set<String>> ELEMENT_TYPES = new HashMap<String, Set<String>>();

    static {
        ELEMENT_TYPES.put(PointKeys.TYPE, PointPainter.TYPES);
        ELEMENT_TYPES.put(FillColorKeys.TYPE, FillColorPainter.TYPES);
    }

    /** returns all painter instances used by 2D vis */
    public static Map<String, Painter> createBasicPainters(Vis2DOutput vis2d) {
        Map<String, Painter> ret = new HashMap<String, Painter>();

        ret.put(PointKeys.TYPE, new PointPainter(vis2d));
        ret.put(FillColorKeys.TYPE, new FillColorPainter(vis2d));

        return ret;
    }
}
