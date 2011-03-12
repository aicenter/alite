package incubator.visprotocol.vis.output.painter.vis2d;

import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.util.HashMap;
import java.util.Map;

public abstract class BasicPainters {

    public static Map<String, Painter> getAllBasicPainters(Vis2DOutput vis2d) {
        Map<String, Painter> ret = new HashMap<String, Painter>();
        
        ret.put(PointKeys.TYPE, new PointPainter(vis2d));
        ret.put(FillColorKeys.TYPE, new FillColorPainter(vis2d));
        
        
        return ret;
    }
}
