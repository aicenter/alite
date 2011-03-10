package incubator.visprotocol.vis.output.vis2d;

import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.vecmath.Point2d;

import incubator.visprotocol.vis.output.Vis2D;

public class ZoomTransformator implements MouseWheelListener {

    private double zoomStep = 1.1;
    private boolean zoomInCenter = false;
    private boolean zoomOutCenter = true;

    private final Vis2D vis2d;

    public ZoomTransformator(Vis2D vis2d) {
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

    public boolean getZoomOutCenter() {
	return zoomOutCenter;
    }

    public void setZoomInCenter(boolean zoomInCenter) {
	this.zoomInCenter = zoomInCenter;
    }

    public boolean getZoomInCenter() {
	return zoomInCenter;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
	double zoomFactor = vis2d.getZoomFactor();
	Point2d offset = vis2d.getOffset();

	int rotation = mouseWheelEvent.getWheelRotation()
		* mouseWheelEvent.getScrollAmount();
	if (rotation < 0) {
	    if (zoomInCenter) {
		// TODO not working
		computeCenterMiddle(offset, zoomFactor);
	    } else {
		computeCenterMouse(offset, zoomFactor, mouseWheelEvent
			.getPoint());
	    }
	    zoomFactor *= zoomStep;
	} else {
	    zoomFactor /= zoomStep;
	    if (zoomOutCenter) {
		computeCenterMiddle(offset, zoomFactor);
	    } else {
		// TODO not working
		computeCenterMouse(offset, zoomFactor, mouseWheelEvent
			.getPoint());
	    }
	}
	vis2d.setOffset(offset);
	vis2d.setZoomFactor(zoomFactor);
    }

    private void computeCenterMouse(Point2d offset, double zoomFactor,
	    Point mousePoint) {
	offset.x -= (mousePoint.x - vis2d.getOffsetBack().x)
		/ vis2d.getZoomFactorBack() * zoomFactor * (zoomStep - 1.0);
	offset.y -= (mousePoint.y - vis2d.getOffsetBack().y)
		/ vis2d.getZoomFactorBack() * zoomFactor * (zoomStep - 1.0);
    }

    private void computeCenterMiddle(Point2d offset, double zoomFactor) {
	offset.x += (vis2d.getWidth() / 2 - vis2d.getOffsetBack().x)
		/ vis2d.getZoomFactorBack() * zoomFactor * (zoomStep - 1.0);
	offset.y += (vis2d.getHeight() / 2 - vis2d.getOffsetBack().y)
		/ vis2d.getZoomFactorBack() * zoomFactor * (zoomStep - 1.0);
    }

}
