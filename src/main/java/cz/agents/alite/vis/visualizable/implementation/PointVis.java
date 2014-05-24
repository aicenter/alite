package cz.agents.alite.vis.visualizable.implementation;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.visualizable.Point2dAdapter;
import cz.agents.alite.vis.visualizable.Visualizable;

import javax.vecmath.Point2d;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public class PointVis<T> implements Visualizable {

    private final T centerPosition;
    private final int radiusInPx;
    private final Point2dAdapter<T> adapter;
    private final Color color;

    public PointVis(T centerPosition, int radiusInPx, Color color, Point2dAdapter<T> adapter) {
        this.centerPosition = centerPosition;
        this.radiusInPx = radiusInPx;
        this.adapter = adapter;
        this.color = color;
    }

    @Override
    public void paint(Graphics2D canvas, Rectangle2D drawingRectangle) {
        Point2d center = adapter.convert(centerPosition);
        int x = Vis.transX(center.x);
        int y = Vis.transY(center.y);
        canvas.setColor(color);

        Ellipse2D point = new Ellipse2D.Double(x - radiusInPx, y - radiusInPx,
                2 * radiusInPx + 1, 2 * radiusInPx + 1);

        if (drawingRectangle.intersects(point.getBounds2D())) {
            canvas.fill(point);
        }
    }
}
