package incubator.visprotocol.vis.output;

import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

import javax.vecmath.Point2d;

public class Vis2DParams {
    public String title = "Vis2D output";
    public Dimension size = new Dimension(600, 600);
    public Point2d offset = new Point2d(0, 0);
    public double zoomFactor = 1;
    public Rectangle2D bounds = new Rectangle2D.Double(0, 0, 10000, 1000);
}
