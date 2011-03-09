package incubator.visprotocol.vis.element;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

import cz.agents.alite.vis.element.Point;
import cz.agents.alite.vis.element.aggregation.PointElements;

// TODO: implementing the old PointElements interface is not probably good idea, try to remove it
public class Points implements Serializable, PointElements {

    private static final long serialVersionUID = -1704273440826602143L;

    private final Color color;
    private final int strokeWidth;

    private final LinkedList<Point> points;

    public Points(Color color, int strokeWidth) {
	points = new LinkedList<Point>();
	this.color = color;
	this.strokeWidth = strokeWidth;
    }

    public Points(PointElements pointElements) {
	points = new LinkedList<Point>();
	for (Point point : pointElements.getPoints()) {
	    points.add(point);
	}
	color = pointElements.getColor();
	strokeWidth = pointElements.getStrokeWidth();
    }

    @Override
    public Color getColor() {
	return color;
    }

    @Override
    public int getStrokeWidth() {
	return strokeWidth;
    }

    @Override
    public Iterable<? extends Point> getPoints() {
	return points;
    }

}
