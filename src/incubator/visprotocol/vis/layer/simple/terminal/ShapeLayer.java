package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.structure.key.ShapeKeys;
import incubator.visprotocol.structure.key.struct.Shape;
import incubator.visprotocol.vis.layer.simple.SimpleAbstractLayer;

import java.awt.Color;
import java.util.Iterator;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Layer to draw ovals and rectangles. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class ShapeLayer extends SimpleAbstractLayer {

    private static int layerCount = 0;

    public ShapeLayer(String name) {
        super(name);
    }

    public ShapeLayer() {
        this("Shapes " + ++layerCount);
    }

    @Override
    protected String getDefaultElementName() {
        return "Shape";
    }

    @Override
    protected final void generateFrame() {
        boolean constantSize = isConstantSize();
        boolean constantWidth = isConstantLineWidth();
        Iterator<Vector3D> itPoints = getCenters().iterator();
        Iterator<Color> itColors = getColors().iterator();
        Iterator<Size> itSizes = getSizes().iterator();
        Iterator<Shape> itShapes = getShapes().iterator();
        Iterator<Double> itWidths = getWidths().iterator();
        if (!itPoints.hasNext()) {
            return;
        }
        if (!itColors.hasNext()) {
            throw new RuntimeException("Colors are empty, must contain at least one item");
        }
        if (!itSizes.hasNext()) {
            throw new RuntimeException("Sizes are empty, must contain at least one item");
        }
        if (!itShapes.hasNext()) {
            throw new RuntimeException("Shapes are empty, must contain at least one item");
        }
        if (!itWidths.hasNext()) {
            throw new RuntimeException("Widths are empty, must contain at least one item");
        }

        Iterator<String> itNames = null;
        Iterable<String> names = getNames();
        if (names != null) {
            itNames = names.iterator();
        }

        Color lastColor = null;
        Size lastSize = null;
        Shape lastShape = null;
        double lastWidth = 0;
        while (itPoints.hasNext()) {
            String name = null;
            if ((itNames != null) && (itNames.hasNext())) {
                name = itNames.next();
            }
            if (itColors.hasNext()) {
                lastColor = itColors.next();
            }
            if (itSizes.hasNext()) {
                lastSize = itSizes.next();
            }
            if (itShapes.hasNext()) {
                lastShape = itShapes.next();
            }
            if (itWidths.hasNext()) {
                lastWidth = itWidths.next();
            }
            Param param = addElement(name, ShapeKeys.TYPE).with(ShapeKeys.CENTER, itPoints.next())
                    .with(ShapeKeys.CONSTANT_SIZE, constantSize).with(ShapeKeys.COLOR, lastColor)
                    .with(ShapeKeys.CONSTANT_LINE_WIDTH, constantWidth).with(ShapeKeys.SHAPE,
                            lastShape).with(ShapeKeys.LINE_WIDTH, lastWidth);
            if (lastSize.size > 0) {
                param.with(ShapeKeys.SIZE, lastSize.size).end();
            } else {
                param.with(ShapeKeys.SIZE_X, lastSize.sizeX).with(ShapeKeys.SIZE_Y, lastSize.sizeY)
                        .end();
            }
        }
    }

    /** create center positions for the shapes */
    protected abstract Iterable<Vector3D> getCenters();

    /** create colors for the shapes (can contain only one item) */
    protected abstract Iterable<Color> getColors();

    /** create sizes for the shapes (can contain only one item) */
    protected abstract Iterable<Size> getSizes();

    /** create shapes for the shapes (can contain only one item) */
    protected abstract Iterable<Shape> getShapes();

    /** create line widths for the shapes (can contain only one item) */
    protected abstract Iterable<Double> getWidths();

    /** constant size of line width, default is true */
    protected boolean isConstantLineWidth() {
        return true;
    }

    /** constant size of shape, default is false */
    @Override
    protected boolean isConstantSize() {
        return false;
    }

}
