package incubator.visprotocol.vis.output;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Point2d;

/**
 * Params to initialize Vis2DOutput, objects are copied.
 * 
 * @author Ondrej Milenovsky
 * */
public class Vis2DParams {
    public String windowTitle = "Vis2D output";
    public Dimension windowSize = new Dimension(600, 600);
    public Point2d viewOffset = new Point2d(0, 0);
    public double viewZoom = 1;
    public Rectangle2D worldBounds = new Rectangle2D.Double(0, 0, 1000, 1000);
    public double viewMaxZoom = 1;
}
