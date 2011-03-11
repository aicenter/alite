package incubator.visprotocol.vis.element;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

import cz.agents.alite.vis.element.Point;
import cz.agents.alite.vis.element.aggregation.PointElements;

public class Points implements Serializable {

    private static final long serialVersionUID = -1704273440826602143L;

    public final LinkedList<Point> points;
    public final Color color;
    public final int strokeWidth;

    public Points(PointElements pointElements) {
        points = new LinkedList<Point>();
        for (Point point : pointElements.getPoints()) {
            points.add(point);
        }
        color = pointElements.getColor();
        strokeWidth = pointElements.getStrokeWidth();
    }

}
