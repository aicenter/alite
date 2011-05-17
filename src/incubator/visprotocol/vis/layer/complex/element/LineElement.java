package incubator.visprotocol.vis.layer.complex.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.Color;
import java.util.List;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Structure for line. One instance can be used many times with changed parameters
 * 
 * @author Ondrej Milenovsky
 * */
public class LineElement extends AbstractElement {
    public final List<Vector3D> points;
    public final Color color;
    public final double lineWidth;
    public final boolean constantLineWidth;

    public LineElement(List<Vector3D> points, Color color, double lineWidth,
            boolean constatnLineWidth) {
        this.points = points;
        this.color = color;
        this.lineWidth = lineWidth;
        this.constantLineWidth = constatnLineWidth;
    }

    @Override
    public Element createElement(Element lastElement, String name, FilterStorage filter) {
        Element e = new Element(name, LineKeys.TYPE);
        setElementParameter(e, lastElement, LineKeys.POINTS, points, filter);
        setElementParameter(e, lastElement, LineKeys.COLOR, color, filter);
        setElementParameter(e, lastElement, LineKeys.LINE_WIDTH, lineWidth, filter);
        setElementParameter(e, lastElement, LineKeys.CONSTANT_LINE_WIDTH, constantLineWidth, filter);
        return e;
    }

    @Override
    public String getType() {
        return LineKeys.TYPE;
    }
}
