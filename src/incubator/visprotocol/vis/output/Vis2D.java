package incubator.visprotocol.vis.output;

import java.awt.Component;
import java.awt.Graphics2D;

import javax.vecmath.Point2d;

public interface Vis2D {
    public void setZoomFactor(double zoomFactor);

    public double getZoomFactor();

    public double getZoomFactorBack();

    public Point2d getOffset();

    public Point2d getOffsetBack();

    public void setOffset(Point2d offset);

    public Component getComponent();

    public int getWidth();

    public int getHeight();

    public Graphics2D getGraphics2D();

    public int transX(double x);

    public int transY(double y);

}
