package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.utils.StructUtils;
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

import javax.vecmath.Point3d;

/**
 * Painter to paint single line from element, line can has more than two points.
 * 
 * @author Ondrej Milenovsky
 * */
public class LinePainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(LineKeys.COLOR.id,
            LineKeys.LINE_WIDTH.id, LineKeys.POINTS.id, LineKeys.CONSTANT_LINE_WIDTH.id));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;
    private double width = 1;
    private Collection<Point3d> points = new ArrayList<Point3d>(0);
    private boolean constantLinwWidth = false;

    public LinePainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();

        color = StructUtils.updateValue(e, LineKeys.COLOR, color);
        width = StructUtils.updateValue(e, LineKeys.LINE_WIDTH, width);
        points = StructUtils.updateValue(e, LineKeys.POINTS, points);
        constantLinwWidth = StructUtils.updateValue(e, LineKeys.CONSTANT_LINE_WIDTH,
                constantLinwWidth);

        double drawWidth = width;
        if (!constantLinwWidth) {
            drawWidth = vis2dOutput.transW(width);
        }

        graphics2d.setColor(color);
        graphics2d.setStroke(new BasicStroke((int) drawWidth));

        int lastX = 0;
        int lastY = 0;
        boolean draw = false;
        for (Point3d p : points) {
            int x = vis2dOutput.transX(p.x);
            int y = vis2dOutput.transY(p.y);
            if (draw) {
                drawLine(lastX, lastY, x, y);
            }
            draw = true;
            lastX = x;
            lastY = y;
        }

    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        if (vis2dOutput.containsRect(x1, y1, x2, y2)) {
            vis2dOutput.getGraphics2D().drawLine(x1, y1, x2, y2);
        }
    }

}
