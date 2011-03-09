package incubator.visprotocol.vis.output.painter.vis2d;

import incubator.visprotocol.vis.element.Points;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;
import incubator.visprotocol.vis.protocol.Protocol;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.element.Point;

public class PointPainterVis2D implements Painter {

    private Vis2DOutput vis2dOutput;

    public PointPainterVis2D(Vis2DOutput vis2dOutput) {
	this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Protocol protocol) {
	Points points = protocol.pull(Points.class);

	Graphics2D graphics2d = vis2dOutput.getGraphics2D();

	int radius = (int) (points.getStrokeWidth() / 2.0);
	graphics2d.setColor(points.getColor());
	graphics2d.setStroke(new BasicStroke(1));

	for (Point point : points.getPoints()) {
	    int x1 = vis2dOutput.transX(point.getPosition().x) - radius;
	    int y1 = vis2dOutput.transY(point.getPosition().y) - radius;
	    int x2 = vis2dOutput.transX(point.getPosition().x) + radius;
	    int y2 = vis2dOutput.transY(point.getPosition().y) + radius;
	    if (x2 > 0 && x1 < vis2dOutput.getDrawingDimension().width
		    && y2 > 0 && y1 < vis2dOutput.getDrawingDimension().height) {
		graphics2d.fillOval(x1, y1, radius * 2, radius * 2);
	    }
	}
    }

}
