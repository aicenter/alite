package incubator.visprotocol.vis.output.vis2d;

import incubator.visprotocol.vis.output.Vis2DOutput;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.vecmath.Point2d;

/**
 * Moving camera by holding right mouse button. Just create with pointer to vis, no need to add it
 * somewhere, you can forget pointer to this instance.
 * 
 * @author Ondrej Milenovsky
 * */
public class MoveTransformator implements Transformator, MouseListener, MouseMotionListener {

    private Vis2DOutput vis2d;
    private Point2d lastOffset;

    private boolean moving = false;

    private double speed = 1;

    public MoveTransformator() {
    }

    @Override
    public void setToVis(Vis2DOutput vis2d) {
        this.vis2d = vis2d;
        lastOffset = vis2d.getOffset();
        vis2d.addMouseListener(this);
        vis2d.addMouseMotionListener(this);
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            vis2d.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            moving = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            vis2d.setCursor(new Cursor(Cursor.HAND_CURSOR));
            lastOffset.x = e.getX();
            lastOffset.y = e.getY();
            moving = true;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (moving) {
            Point2d offset = vis2d.getOffset();
            offset.x += speed * (e.getX() - lastOffset.x);
            offset.y += speed * (e.getY() - lastOffset.y);
            vis2d.setOffset(offset);
            lastOffset.x = e.getX();
            lastOffset.y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
