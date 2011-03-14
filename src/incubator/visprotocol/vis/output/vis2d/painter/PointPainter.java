package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2d;

/**
 * Painter to paint single point from element
 * 
 * @author Ondrej Milenovsky
 * */
public class PointPainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(PointKeys.COLOR
            .toString(), PointKeys.SIZE.toString(), PointKeys.CENTER.toString(),
            PointKeys.CONSTANT_SIZE.toString()));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;
    private double width = 1;
    private Point2d pos = new Point2d();
    private boolean constantSize = false;

    public PointPainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();

        color = StructUtils.updateValue(e, PointKeys.COLOR, color);
        width = StructUtils.updateValue(e, PointKeys.SIZE, width);
        pos = StructUtils.updateValue(e, PointKeys.CENTER, pos);
        constantSize = StructUtils.updateValue(e, PointKeys.CONSTANT_SIZE, constantSize);

        double drawWidth = width;
        if (!constantSize) {
            drawWidth = vis2dOutput.transW(width);
        }
        int radius = (int) (drawWidth / 2.0);

        graphics2d.setColor(color);

        int x1 = vis2dOutput.transX(pos.x) - radius;
        int y1 = vis2dOutput.transY(pos.y) - radius;
        int x2 = x1 + 2 * radius;
        int y2 = y1 + 2 * radius;
        if (vis2dOutput.containsRect(x1, y1, x2, y2)) {
            graphics2d.fillOval(x1, y1, (int) drawWidth, (int) drawWidth);
        }
    }

}
