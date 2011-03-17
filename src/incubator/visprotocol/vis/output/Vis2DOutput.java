package incubator.visprotocol.vis.output;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.output.vis2d.Transformator;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Point2d;

/**
 * Canvas with window to draw 2D elements. Should be created with modified Vis2DParams.
 * 
 * @author Ondrej Milenovsky
 * */
public class Vis2DOutput extends Canvas implements StructProcessor {

    private static final long serialVersionUID = -4597445627896905949L;

    private List<StructProcessor> inputs = new ArrayList<StructProcessor>();

    // state
    private double zoomFactor;
    private final Point2d offset;
    private double zoomFactorBack;
    private final Point2d offsetBack;

    private int widthBack;
    private int heightBack;

    private final Point cursorPosition = new Point();

    private boolean reinitializeBuffers = true;

    // properties
    private final Rectangle2D bounds;
    private final double maxZoom;

    // components
    private JFrame window;
    private BufferStrategy strategy;
    private Graphics2D graphics;

    public Vis2DOutput() {
        this(new Vis2DParams());
    }

    public Vis2DOutput(Vis2DParams params) {
        super();

        widthBack = params.windowSize.width;
        heightBack = params.windowSize.height;

        zoomFactor = params.viewZoom;
        zoomFactorBack = zoomFactor;
        offset = new Point2d(params.viewOffset);
        offsetBack = new Point2d(params.viewOffset);
        bounds = params.worldBounds;
        maxZoom = params.viewMaxZoom;

        initComponents();
        window.setTitle(params.windowTitle);
    }

    private void initComponents() {
        window = new JFrame();
        setBounds(0, 0, widthBack, heightBack);

        final JPanel panel = (JPanel) window.getContentPane();
        panel.setBounds(0, 0, widthBack, heightBack);
        panel.setLayout(new BorderLayout());
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
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                cursorPosition.setLocation(e.getLocationOnScreen());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                cursorPosition.setLocation(e.getLocationOnScreen());
            }
        });

        // buffers
        reinitializeBuffers();

        window.setVisible(true);
    }

    public void addInput(StructProcessor input) {
        this.inputs.add(input);
    }

    public void addInputs(Collection<StructProcessor> inputs) {
        this.inputs.addAll(inputs);
    }

    public void addInputs(StructProcessor... inputs) {
        this.inputs.addAll(Arrays.asList(inputs));
    }

    public void addPanel(Component panel, String borderAlign) {
        window.getContentPane().add(panel, borderAlign);
        // TODO better repaint
        Rectangle bounds = window.getBounds();
        window.setBounds(bounds.x, bounds.y, bounds.width + 1, bounds.height);
        window.setBounds(bounds);
    }

    public void addTransformator(Transformator t) {
        t.setToVis(this);
    }

    public void addTransformators(Collection<Transformator> ts) {
        for (Transformator t : ts) {
            addTransformator(t);
        }
    }

    public Component getComponent() {
        return this;
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

        if ((getWidth() != widthBack) || (getHeight() != heightBack)) {
            reinitializeBuffers = true;
        }

        if (reinitializeBuffers()) {
            limitTransformation();
        }

        zoomFactorBack = zoomFactor;
        offsetBack.set(offset);
        widthBack = super.getWidth();
        heightBack = super.getHeight();
    }

    public int getBackWidth() {
        return widthBack;
    }

    public int getBackHeight() {
        return heightBack;
    }

    public void setZoomFactor(double zoom) {
        zoomFactor = zoom;
        limitTransformation();
    }

    public int transX(double x) {
        return (int) (offsetBack.x + x * zoomFactorBack);
    }

    public int transY(double y) {
        return (int) (offsetBack.y + y * zoomFactorBack);
    }

    public int transXCurrent(double x) {
        return (int) (offset.x + x * zoomFactor);
    }

    public int transYCurrent(double y) {
        return (int) (offset.y + y * zoomFactor);
    }

    public int transW(double w) {
        return (int) (w * zoomFactorBack);
    }

    public int transH(double h) {
        return (int) (h * zoomFactorBack);
    }

    public double transInvX(int x) {
        return (x - offsetBack.x) / zoomFactorBack;
    }

    public double transInvY(int y) {
        return (y - offsetBack.y) / zoomFactorBack;
    }

    public double transInvW(int w) {
        return w / zoomFactorBack;
    }

    public double transInvH(int h) {
        return h / zoomFactorBack;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }

    public Point2d getOffset() {
        return new Point2d(offset);
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

    protected double transInvXCurrent(int x) {
        return (x - offset.x) / zoomFactor;
    }

    protected double transInvYCurrent(int y) {
        return (y - offset.y) / zoomFactor;
    }

    public Point2d getOffsetBack() {
        return new Point2d(offsetBack);
    }

    public double getZoomFactorBack() {
        return zoomFactorBack;
    }

    public void setOffset(Point2d offset) {
        this.offset.set(offset);
        limitTransformation();
    }

    public Point2d getCursorPosition() {
        return new Point2d(transInvX(cursorPosition.x), transInvY(cursorPosition.y));
    }

    public boolean containsRect(int x1, int y1, int x2, int y2) {
        if (x1 > x2) {
            int u = x1;
            x1 = x2;
            x2 = u;
        }
        if (y1 > y2) {
            int u = y1;
            y1 = y2;
            y2 = u;
        }
        return (x2 >= 0) && (x1 < getWidth()) && (y2 >= 0) && (y1 < getHeight());
    }

    private void limitTransformation() {
        zoomFactor = Math.min(zoomFactor, maxZoom);

        int bMinX = transXCurrent(bounds.getMinX());
        int bMinY = transYCurrent(bounds.getMinY());
        int bMaxX = transXCurrent(bounds.getMaxX());
        int bMaxY = transYCurrent(bounds.getMaxY());
        int bW = transXCurrent(bounds.getMaxX() - bounds.getMinX());
        int bH = transYCurrent(bounds.getMaxY() - bounds.getMinY());

        // TODO limiting zoom is not working well
        if (bMaxX - bMinX < getWidth()) {
            zoomFactor *= getWidth() / (double) (bMaxX - bMinX);
        }
        if (bMaxY - bMinY < getHeight()) {
            zoomFactor *= getHeight() / (double) (bMaxY - bMinY);
        }

        if (bMinX > 0) {
            offset.x -= bMinX;
        }
        if (bMinY > 0) {
            offset.y -= bMinY;
        }
        if (bW < getWidth()) {
            offset.x += -bW + getWidth();
        }
        if (bH < getHeight()) {
            offset.y += -bH + getHeight();
        }
    }

    /** paint and flip() */
    @Override
    public Structure pull() {
        for (StructProcessor pr : inputs) {
            pr.pull();
        }
        flip();
        return null;
    }

}
