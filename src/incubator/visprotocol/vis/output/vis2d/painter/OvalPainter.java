package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.OvalKeys;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2d;

/**
 * Painter to paint single oval from element
 * 
 * @author Ondrej Milenovsky
 * */
public class OvalPainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(OvalKeys.COLOR
            .toString(), OvalKeys.WIDTH.toString(), OvalKeys.CONSTANT_SIZE.toString(),
            OvalKeys.DIAMETER.toString(), OvalKeys.POS.toString(), OvalKeys.SIZE_X.toString(),
            OvalKeys.SIZE_Y.toString()));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;
    private double width = 1;
    private Point2d pos = new Point2d();
    private boolean constantSize = false;
    private double sizeX = 10;
    private double sizeY = 10;

    public OvalPainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();

        if (e.containsParameter(OvalKeys.COLOR)) {
            color = e.getParameter(OvalKeys.COLOR);
        }
        if (e.containsParameter(OvalKeys.WIDTH)) {
            width = e.getParameter(OvalKeys.WIDTH);
        }
        if (e.containsParameter(OvalKeys.CONSTANT_SIZE)) {
            constantSize = e.getParameter(OvalKeys.CONSTANT_SIZE);
        }
        if (e.containsParameter(OvalKeys.POS)) {
            pos = e.getParameter(OvalKeys.POS);
            if (e.containsParameter(OvalKeys.DIAMETER)) {
                sizeX = e.getParameter(OvalKeys.DIAMETER);
                sizeY = sizeX;
            } else {
                if (e.containsParameter(OvalKeys.SIZE_X)) {
                    sizeX = e.getParameter(OvalKeys.SIZE_X);
                }
                if (e.containsParameter(OvalKeys.SIZE_Y)) {
                    sizeY = e.getParameter(OvalKeys.SIZE_Y);
                }
            }
        }

        double drawWidth = width;
        if (!constantSize) {
            drawWidth = vis2dOutput.transW(width);
        }

        graphics2d.setColor(color);
        graphics2d.setStroke(new BasicStroke((int) drawWidth));

        int sx = vis2dOutput.transW(sizeX);
        int sy = vis2dOutput.transW(sizeY);

        int x1 = vis2dOutput.transX(pos.x) - sx / 2;
        int y1 = vis2dOutput.transY(pos.y) - sy / 2;
        int x2 = vis2dOutput.transX(pos.x) + sx / 2;
        int y2 = vis2dOutput.transY(pos.y) + sy / 2;
        if (vis2dOutput.containsRect(x1, y1, x2, y2)) {
            graphics2d.drawOval(x1, y1, sx, sy);
        }
    }

}
