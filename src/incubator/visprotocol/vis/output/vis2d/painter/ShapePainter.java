package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.ShapeKeys;
import incubator.visprotocol.structure.key.struct.Shape;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Painter to paint single shape from element. Could be oval or rectangle.
 * 
 * @author Ondrej Milenovsky
 * */
public class ShapePainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(ShapeKeys.COLOR.id,
            ShapeKeys.LINE_WIDTH.id, ShapeKeys.CONSTANT_LINE_WIDTH.id, ShapeKeys.SIZE.id,
            ShapeKeys.CENTER.id, ShapeKeys.SIZE_X.id, ShapeKeys.SIZE_Y.id,
            ShapeKeys.CONSTANT_SIZE.id, ShapeKeys.SHAPE.id));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;
    private double width = 1;
    private Vector3D pos = new Vector3D(0, 0, 0);
    private boolean constantLineWidth = false;
    private boolean constantSize = false;
    private double sizeX = 10;
    private double sizeY = 10;
    private Shape shape = Shape.RECT;

    public ShapePainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();

        color = StructUtils.updateValue(e, ShapeKeys.COLOR, color);
        width = StructUtils.updateValue(e, ShapeKeys.LINE_WIDTH, width);
        constantLineWidth = StructUtils.updateValue(e, ShapeKeys.CONSTANT_LINE_WIDTH,
                constantLineWidth);
        constantSize = StructUtils.updateValue(e, ShapeKeys.CONSTANT_SIZE, constantSize);
        pos = StructUtils.updateValue(e, ShapeKeys.CENTER, pos);
        shape = StructUtils.updateValue(e, ShapeKeys.SHAPE, shape);
        if (e.containsParameter(ShapeKeys.SIZE)) {
            sizeX = e.getParameter(ShapeKeys.SIZE);
            sizeY = sizeX;
        } else {
            sizeX = StructUtils.updateValue(e, ShapeKeys.SIZE_X, sizeX);
            sizeY = StructUtils.updateValue(e, ShapeKeys.SIZE_Y, sizeY);
        }

        double drawWidth = width;
        if (!constantLineWidth && (width > 0)) {
            drawWidth = vis2dOutput.transW(width);
        }

        graphics2d.setColor(color);
        if (width > 0) {
            graphics2d.setStroke(new BasicStroke((int) drawWidth));
        }

        int sx;
        int sy;
        if (constantSize) {
            sx = (int) sizeX;
            sy = (int) sizeY;
        } else {
            sx = vis2dOutput.transW(sizeX);
            sy = vis2dOutput.transW(sizeY);
        }

        int x1 = vis2dOutput.transX(pos.getX()) - sx / 2;
        int y1 = vis2dOutput.transY(pos.getY()) - sy / 2;
        int x2 = x1 + sx;
        int y2 = y1 + sy;
        if (vis2dOutput.containsRect(x1, y1, x2, y2)) {
            if (shape == Shape.OVAL) {
                if (width < 0) {
                    graphics2d.fillOval(x1, y1, sx, sy);
                } else {
                    graphics2d.drawOval(x1, y1, sx, sy);
                }
            } else if (shape == Shape.RECT) {
                if (width < 0) {
                    graphics2d.fillRect(x1, y1, sx, sy);
                } else {
                    graphics2d.drawRect(x1, y1, sx, sy);
                }
            }
        }
    }
}
