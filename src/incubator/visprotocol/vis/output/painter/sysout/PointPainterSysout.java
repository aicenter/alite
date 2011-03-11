package incubator.visprotocol.vis.output.painter.sysout;

import incubator.visprotocol.vis.element.Points;
import incubator.visprotocol.vis.output.painter.Painter;
import incubator.visprotocol.vis.protocol.Protocol;
import cz.agents.alite.vis.element.Point;

public class PointPainterSysout implements Painter {

    private final String id;

    public PointPainterSysout(String id) {
        this.id = id;
    }

    @Override
    public void paint(Protocol protocol) {
        Points points = (Points) protocol.pull(id);

        String output = "";
        for (Point point : points.points) {
            output = "POINT: " + point.getPosition();
        }
        System.out.println(output);
    }

}
