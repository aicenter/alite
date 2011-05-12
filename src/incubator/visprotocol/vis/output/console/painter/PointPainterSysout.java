package incubator.visprotocol.vis.output.console.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.output.painter.Painter;

import org.apache.commons.math.geometry.Vector3D;

public class PointPainterSysout implements Painter {

    public PointPainterSysout() {
    }

    @Override
    public void paint(Element e) {
        Vector3D pos = e.getParameter(PointKeys.CENTER);
        System.out.println(e.getId() + " " + pos);
    }

}
