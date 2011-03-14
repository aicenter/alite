package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2d;

/**
 * Painter to paint single line from element
 * 
 * @author Ondrej Milenovsky
 * */
public class LinePainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(LineKeys.COLOR
            .toString(), LineKeys.WIDTH.toString(), LineKeys.POINTS.toString(),
            LineKeys.CONSTANT_SIZE.toString()));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;
    private double width = 1;
    private Collection<Point2d> points = new ArrayList<Point2d>(0);
    private boolean constantSize = false;

    public LinePainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();

        if (e.containsParameter(LineKeys.COLOR)) {
            color = e.getParameter(LineKeys.COLOR);
        }
        if (e.containsParameter(LineKeys.WIDTH)) {
            width = e.getParameter(LineKeys.WIDTH);
        }
        if (e.containsParameter(LineKeys.POINTS)) {
            points = e.getParameter(LineKeys.POINTS);
        }
        if (e.containsParameter(LineKeys.CONSTANT_SIZE)) {
            constantSize = e.getParameter(LineKeys.CONSTANT_SIZE);
        }

        double drawWidth = width;
        if (!constantSize) {
            drawWidth = vis2dOutput.transW(width);
        }

        graphics2d.setColor(color);
        graphics2d.setStroke(new BasicStroke((int) drawWidth));

        Point2d last = null;
        for (Point2d p : points) {
            if (last != null) {
                drawLine(last, p);
            }
            last = p;
        }

    }

    private void drawLine(Point2d p1, Point2d p2) {
        int x1 = vis2dOutput.transX(p1.x);
        int y1 = vis2dOutput.transY(p1.y);
        int x2 = vis2dOutput.transX(p2.x);
        int y2 = vis2dOutput.transY(p2.y);

        if (vis2dOutput.containsRect(x1, y1, x2, y2)) {
            vis2dOutput.getGraphics2D().drawLine(x1, y1, x2, y2);
        }
    }

}
