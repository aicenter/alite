package incubator.visprotocol.vis.output.vis2d;

import incubator.visprotocol.vis.output.Vis2DOutput;

import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.vecmath.Point2d;

/**
 * Zooming by mouse wheel. Just create with pointer to vis, no need to add it somewhere, you can
 * forget pointer to this instance.
 * 
 * @author Ondrej Milenovsky
 * */
public class ZoomTransformator implements Transformator, MouseWheelListener {

    private double zoomStep = 1.1;
    private boolean zoomInCenter = false;
    private boolean zoomOutCenter = true;

    private Vis2DOutput vis2d;

    public ZoomTransformator() {
    }

    @Override
    public void setToVis(Vis2DOutput vis2d) {
        this.vis2d = vis2d;
        vis2d.getComponent().addMouseWheelListener(this);
    }

    public void setZoomStep(double zoomStep) {
        this.zoomStep = zoomStep;
    }

    public double getZoomStep() {
        return zoomStep;
    }

    public void setZoomOutCenter(boolean zoomOutCenter) {
        this.zoomOutCenter = zoomOutCenter;
    }

    public boolean isZoomOutCenter() {
        return zoomOutCenter;
    }

    public void setZoomInCenter(boolean zoomInCenter) {
        this.zoomInCenter = zoomInCenter;
    }

    public boolean isZoomInCenter() {
        return zoomInCenter;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        double zoomFactor = vis2d.getZoomFactor();
        Point2d offset = vis2d.getOffset();

        int rotation = mouseWheelEvent.getWheelRotation() * mouseWheelEvent.getScrollAmount();
        // TODO bugged
        // TODO what if step < 1
        if (rotation < 0) {
            // zoom in
            if (zoomInCenter) {
                // TODO not working
                computeCenterMiddle(offset, zoomFactor);
            } else {
                computeCenterMouse(offset, zoomFactor, mouseWheelEvent.getPoint());
            }
            zoomFactor *= zoomStep;
        } else {
            // zoom out
            zoomFactor /= zoomStep;
            if (zoomOutCenter) {
                computeCenterMiddle(offset, zoomFactor);
            } else {
                // TODO not working
                computeCenterMouse(offset, zoomFactor, mouseWheelEvent.getPoint());
            }
        }
        vis2d.setZoomFactor(zoomFactor);
        vis2d.setOffset(offset);
    }

    private void computeCenterMouse(Point2d offset, double zoomFactor, Point mousePoint) {
        offset.x -= (mousePoint.x - vis2d.getOffsetBack().x) / vis2d.getZoomFactorBack()
                * zoomFactor * (zoomStep - 1.0);
        offset.y -= (mousePoint.y - vis2d.getOffsetBack().y) / vis2d.getZoomFactorBack()
                * zoomFactor * (zoomStep - 1.0);
    }

    private void computeCenterMiddle(Point2d offset, double zoomFactor) {
        offset.x += (vis2d.getPaintWidth() / 2 - vis2d.getOffsetBack().x) / vis2d.getZoomFactorBack()
                * zoomFactor * (zoomStep - 1.0);
        offset.y += (vis2d.getPaintHeight() / 2 - vis2d.getOffsetBack().y) / vis2d.getZoomFactorBack()
                * zoomFactor * (zoomStep - 1.0);
    }

}
