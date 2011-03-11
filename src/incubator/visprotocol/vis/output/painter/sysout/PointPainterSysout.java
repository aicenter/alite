package incubator.visprotocol.vis.output.painter.sysout;

import incubator.visprotocol.vis.element.Points;
import incubator.visprotocol.vis.output.painter.Painter;
import incubator.visprotocol.vis.protocol.Protocol;
import cz.agents.alite.vis.element.Point;

public class PointPainterSysout implements Painter {

    @Override
    public void paint(Protocol protocol) {
        Points points = protocol.pull(Points.class);

        String output = "";
        for (Point point : points.points) {
            output = "POINT: " + point.getPosition();
        }
        System.out.println(output);
    }

}
