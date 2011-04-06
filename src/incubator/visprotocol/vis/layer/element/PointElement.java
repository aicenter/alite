package incubator.visprotocol.vis.layer.element;

import java.awt.Color;

import javax.vecmath.Point3d;

/**
 * Structure for point
 * 
 * @author Ondrej Milenovsky
 * */
public class PointElement {
    public Point3d pos;
    public Color color;
    public double width;
    
    public PointElement(Point3d pos, Color color, double width) {
        this.pos = pos;
        this.color = color;
        this.width = width;
    }
    
}
