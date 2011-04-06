package incubator.visprotocol.vis.layer.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.Color;

import javax.vecmath.Point3d;

/**
 * Structure for point. One instance can be used many times with changed parameters
 * 
 * @author Ondrej Milenovsky
 * */
public class PointElement extends AbstractElement {
    public Point3d pos;
    public Color color;
    public double width;
    public boolean constantSize;

    public PointElement(Point3d pos, Color color, double width, boolean constatnSize) {
        this.pos = pos;
        this.color = color;
        this.width = width;
        this.constantSize = constatnSize;
    }

    @Override
    public Element createElement(Element lastElement, String name, FilterStorage filter) {
        Element e = new Element(name, PointKeys.TYPE);
        setElementParameter(e, lastElement, PointKeys.CENTER, pos, filter);
        setElementParameter(e, lastElement, PointKeys.COLOR, color, filter);
        setElementParameter(e, lastElement, PointKeys.SIZE, width, filter);
        setElementParameter(e, lastElement, PointKeys.CONSTANT_SIZE, constantSize, filter);
        return e;
    }

    @Override
    public String getType() {
        return PointKeys.TYPE;
    }
}
