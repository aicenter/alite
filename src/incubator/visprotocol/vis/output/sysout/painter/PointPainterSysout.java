package incubator.visprotocol.vis.output.sysout.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.output.painter.Painter;

import javax.vecmath.Point2d;

public class PointPainterSysout implements Painter {

    public PointPainterSysout() {
    }

    @Override
    public void paint(Element e) {
        Point2d pos = e.getParameter(PointKeys.POS);
        System.out.println(e.getId() + " " + pos);
    }

}
