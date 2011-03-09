package incubator.visprotocol.vis.output;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

public class Vis2DOutput extends Canvas {

    private static final long serialVersionUID = -4597445627896905949L;

    private static final int MAGIC_NUMBER = 900;

    // TODO: refactor - create pluggable transformations and create scale as one
    // of the transformation pluggins
    private final double SCALE_X = 900 / 1500.0;
    private final double SCALE_Y = 900 / 1500.0;

    // TODO: refactor - create pluggable transformations and create zoom and pan
    // as one of the transformation pluggins
    private double zoomFactor;
    private final Point2d offset;
    private final Point2d lastOffset;
    private boolean panning = false;
    private double zoomFactorBack = 1.0;
    private final Point2d offsetBack = new Point2d(0, 0);

    private JFrame window;

    private boolean reinitializeBuffers = true;
    private BufferStrategy strategy;
    private Graphics2D graphics;

    public Vis2DOutput() {
	this(new Vis2DParams());
    }

    public Vis2DOutput(Vis2DParams params) {
	super();

	// canvas
	setBounds(0, 0, params.size.width, params.size.height);

	window = new JFrame(params.title);
	zoomFactor = params.zoomFactor;
	offset = new Point2d(params.offset);
	lastOffset = new Point2d(params.offset);

	final JPanel panel = (JPanel) window.getContentPane();
	panel.setBounds(0, 0, params.size.width, params.size.height);
	panel.add(this);

	window.addWindowListener(new WindowAdapter() {

	    public void windowClosing(WindowEvent evt) {
		System.exit(0);
	    }

	});
	window.addComponentListener(new ComponentListener() {

	    @Override
	    public void componentShown(ComponentEvent e) {
	    }

	    @Override
	    public void componentResized(ComponentEvent e) {
		reinitializeBuffers = true;
	    }

	    @Override
	    public void componentMoved(ComponentEvent e) {
	    }

	    @Override
	    public void componentHidden(ComponentEvent e) {
	    }

	});

	window.pack();

	// listeners
	addMouseWheelListener(new MouseWheelListener() {

	    @Override
	    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
		final double zoomStep = 1.1;

		int rotation = mouseWheelEvent.getWheelRotation()
			* mouseWheelEvent.getScrollAmount();
		if (rotation < 0) {
		    offset.x -= transInvX(mouseWheelEvent.getX()) * SCALE_X
			    * zoomFactor * (zoomStep - 1.0);
		    offset.y -= transInvY(mouseWheelEvent.getY()) * SCALE_Y
			    * zoomFactor * (zoomStep - 1.0);

		    zoomFactor *= zoomStep;
		} else {
		    zoomFactor /= zoomStep;

		    offset.x += transInvX(getWidth() / 2) * SCALE_X
			    * zoomFactor * (zoomStep - 1.0);
		    offset.y += transInvY(getHeight() / 2) * SCALE_Y
			    * zoomFactor * (zoomStep - 1.0);
		}

		limitTransformation();
	    }

	});
	addMouseListener(new MouseListener() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
		    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		    panning = false;
		}
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
		    setCursor(new Cursor(Cursor.HAND_CURSOR));
		    panning = true;
		}
	    }

	    @Override
	    public void mouseExited(MouseEvent e) {
	    }

	    @Override
	    public void mouseEntered(MouseEvent e) {
	    }

	    @Override
	    public void mouseClicked(MouseEvent e) {
	    }

	});
	addMouseMotionListener(new MouseMotionListener() {

	    @Override
	    public void mouseDragged(MouseEvent e) {
		if (panning) {
		    offset.x -= lastOffset.x - e.getX();
		    offset.y -= lastOffset.y - e.getY();

		    limitTransformation();
		}

		lastOffset.x = e.getX();
		lastOffset.y = e.getY();
	    }

	    @Override
	    public void mouseMoved(MouseEvent e) {
		lastOffset.x = e.getX();
		lastOffset.y = e.getY();
	    }

	});
	addKeyListener(new KeyListener() {

	    @Override
	    public void keyTyped(KeyEvent e) {
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	    }

	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_HOME) {
		    offset.x = 0;
		    offset.y = 0;
		    zoomFactor = 1;
		}
	    }
	});

	// buffers
	reinitializeBuffers();

	window.setVisible(true);
    }

    public Graphics2D getGraphics2D() {
	return graphics;
    }

    private boolean reinitializeBuffers() {
	if (reinitializeBuffers) {
	    reinitializeBuffers = false;

	    createBufferStrategy(2);
	    strategy = getBufferStrategy();

	    graphics = (Graphics2D) strategy.getDrawGraphics();
	    graphics.setColor(Color.WHITE);
	    graphics.setBackground(Color.BLACK);

	    return true;
	}

	return false;
    }

    public void flip() {
	strategy.show();

	if (reinitializeBuffers()) {
	    limitTransformation();
	}

	zoomFactorBack = zoomFactor;
	offsetBack.set(offset);
    }

    public int transX(double x) {
	return (int) (offsetBack.x + x * zoomFactorBack * SCALE_X);
    }

    public int transY(double y) {
	return (int) (offsetBack.y + y * zoomFactorBack * SCALE_Y);
    }

    public int transW(double w) {
	return (int) (w * zoomFactorBack * SCALE_X);
    }

    public int transH(double h) {
	return (int) (h * zoomFactorBack * SCALE_Y);
    }

    public double transInvX(int x) {
	return (x - offsetBack.x) / zoomFactorBack / SCALE_X;
    }

    public double transInvY(int y) {
	return (y - offsetBack.y) / zoomFactorBack / SCALE_Y;
    }

    public double transInvW(int w) {
	return w / zoomFactorBack / SCALE_X;
    }

    public double transInvH(int h) {
	return h / zoomFactorBack / SCALE_Y;
    }

    public int getWorldDimX() {
	return (int) (getWidth() / SCALE_X);
    }

    public int getWorldDimY() {
	return (int) (getHeight() / SCALE_Y);
    }

    public double getZoomFactor() {
	return zoomFactorBack;
    }

    public Point2d getOffset() {
	return offsetBack;
    }

    public Point2d getCursorPosition() {
	return lastOffset;
    }

    public Dimension getDrawingDimension() {
	return window.getContentPane().getSize();
    }

    public void setWindowBounds(Rectangle rect) {
	window.setBounds(rect);
    }

    public void setWindowTitle(String title) {
	window.setTitle(title);
    }

    private double transInvXCurrent(int x) {
	return (x - offset.x) / zoomFactor;
    }

    private double transInvYCurrent(int y) {
	return (y - offset.y) / zoomFactor;
    }

    private int transSCurrent(int s) {
	return (int) (s * zoomFactor);
    }

    private void limitTransformation() {
	int windowWidth = getWidth();
	int windowHeight = getHeight();

	if (windowWidth > windowHeight) {
	    if (zoomFactor < (double) windowWidth / MAGIC_NUMBER) {
		zoomFactor = (double) windowWidth / MAGIC_NUMBER;
	    }
	} else {
	    if (zoomFactor < (double) windowHeight / MAGIC_NUMBER) {
		zoomFactor = (double) windowHeight / MAGIC_NUMBER;
	    }
	}

	if (offset.x > 0) {
	    offset.x = 0;
	}
	if (offset.y > 0) {
	    offset.y = 0;
	}
	if (transInvXCurrent(windowWidth) > MAGIC_NUMBER) {
	    offset.x = -transSCurrent(MAGIC_NUMBER) + windowWidth;
	}
	if (transInvYCurrent(windowHeight) > MAGIC_NUMBER) {
	    offset.y = -transSCurrent(MAGIC_NUMBER) + windowHeight;
	}
    }

    public int getWidth() {
	return window.getContentPane().getWidth();
    }

    public int getHeight() {
	return window.getContentPane().getHeight();
    }

}
