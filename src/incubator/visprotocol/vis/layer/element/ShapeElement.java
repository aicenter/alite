package incubator.visprotocol.vis.layer.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.ShapeKeys;
import incubator.visprotocol.structure.key.struct.Shape;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.Color;

import javax.vecmath.Point3d;

/**
 * Structure for shape. One instance can be used many times with changed parameters
 * 
 * @author Ondrej Milenovsky
 * */
public class ShapeElement extends AbstractElement {
    public Shape shape;
    public Point3d center;
    public Color color;
    public double lineWidth;
    public double size;
    public double sizeX;
    public double sizeY;
    public boolean constantSize;
    public boolean constantLineWidth;

    public ShapeElement(Shape shape, Point3d center, Color color, double size, double lineWidth,
            boolean constatnSize, boolean constantLineWidth) {
        this.shape = shape;
        this.center = center;
        this.color = color;
        this.size = size;
        this.lineWidth = lineWidth;
        this.constantSize = constatnSize;
        this.constantLineWidth = constantLineWidth;
    }

    public ShapeElement(Shape shape, Point3d center, Color color, double sizeX, double sizeY,
            double lineWidth, boolean constatnSize, boolean constantLineWidth) {
        this.shape = shape;
        this.center = center;
        this.color = color;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.lineWidth = lineWidth;
        this.constantSize = constatnSize;
        this.constantLineWidth = constantLineWidth;
    }

    @Override
    public Element createElement(Element lastElement, String name, FilterStorage filter) {
        Element e = new Element(name, ShapeKeys.TYPE);
        setElementParameter(e, lastElement, ShapeKeys.SHAPE, shape, filter);
        setElementParameter(e, lastElement, ShapeKeys.CENTER, center, filter);
        setElementParameter(e, lastElement, ShapeKeys.COLOR, color, filter);
        setElementParameter(e, lastElement, ShapeKeys.LINE_WIDTH, lineWidth, filter);
        setElementParameter(e, lastElement, ShapeKeys.CONSTANT_SIZE, constantSize, filter);
        setElementParameter(e, lastElement, ShapeKeys.CONSTANT_LINE_WIDTH, constantLineWidth,
                filter);
        if (size > 0) {
            setElementParameter(e, lastElement, ShapeKeys.SIZE, size, filter);
        } else {
            setElementParameter(e, lastElement, ShapeKeys.SIZE_X, sizeX, filter);
            setElementParameter(e, lastElement, ShapeKeys.SIZE_Y, sizeY, filter);
        }
        return e;
    }

    @Override
    public String getType() {
        return ShapeKeys.TYPE;
    }
}
