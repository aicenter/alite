package cz.agents.alite.vis;

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

/**
 * Vis is a singleton holding the visualization window and the drawing canvas.
 * 
 * Additionally, it also provides panning and zooming functionality for all visual elements drawn
 * using the transformation methods (transX(), transY(), transW(), transH()).
 * 
 * The Vis singleton do not need to be explicitly initialized. The static calls do the
 * initialization automatically. But also, it can be explicitly initialized with specified window
 * bounds (position and size) by the initWithBounds method.
 * 
 * 
 * @author Antonin Komneda
 */
public class Vis extends Canvas {

    private static final long serialVersionUID = 1093434407555503398L;

    // TODO: refactor - conf
    public static final int DIM_X = 900;
    public static final int DIM_Y = 900;

    private static String initTitle = "ALite Operator";
    private static int initDimX = DIM_X;
    private static int initDimY = DIM_Y;

    // TODO: refactor - aggr
    private static final double SCALE_X = DIM_X / 1500.0;
    private static final double SCALE_Y = DIM_Y / 1500.0;

    private static Vis instance = null;

    // TODO: refactor - aggr
    private static double zoomFactor = 1.0;
    private static final Point2d offset = new Point2d(0, 0);
    private static final Point2d lastOffset = new Point2d(0, 0);
    private static boolean panning = false;
    private static double zoomFactorBack = 1.0;
    private static final Point2d offsetBack = new Point2d(0, 0);

    private JFrame window;

    private boolean reinitializeBuffers = true;
    private BufferStrategy strategy;
    private Graphics2D graphics;

    private Vis() {
        super();

        // canvas
        setBounds(0, 0, initDimX, initDimY);

        window = new JFrame(initTitle);

        final JPanel panel = (JPanel) window.getContentPane();
        panel.setBounds(0, 0, initDimX, initDimY);
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
                    offset.x -= transInvX(mouseWheelEvent.getX()) * SCALE_X * zoomFactor
                            * (zoomStep - 1.0);
                    offset.y -= transInvY(mouseWheelEvent.getY()) * SCALE_Y * zoomFactor
                            * (zoomStep - 1.0);

                    zoomFactor *= zoomStep;
                } else {
                    zoomFactor /= zoomStep;

                    offset.x += transInvX(getWidth() / 2) * SCALE_X * zoomFactor * (zoomStep - 1.0);
                    offset.y += transInvY(getHeight() / 2) * SCALE_Y * zoomFactor
                            * (zoomStep - 1.0);
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
    }

    /**
     * sets initial parameters of the window, call this before creating the window
     */
    public static void setInitParam(String title, int dimX, int dimY) {
        initDimX = dimX;
        initDimY = dimY;
        initTitle = title;
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

    public static synchronized Vis getInstance() {
        if (instance == null) {
            instance = new Vis();

            // show window
            instance.window.setVisible(true);
            instance.window.requestFocus();
            instance.requestFocus();
        }

        return instance;
    }

    public static Graphics2D getCanvas() {
        return getInstance().graphics;
    }

    public static void flip() {
        getInstance().strategy.show();

        if (getInstance().reinitializeBuffers()) {
            limitTransformation();
        }

        zoomFactorBack = zoomFactor;
        offsetBack.set(offset);
    }

    public static int transX(double x) {
        return (int) (offsetBack.x + x * zoomFactorBack * SCALE_X);
    }

    public static int transY(double y) {
        return (int) (offsetBack.y + y * zoomFactorBack * SCALE_Y);
    }

    public static int transW(double w) {
        return (int) (w * zoomFactorBack * SCALE_X);
    }

    public static int transH(double h) {
        return (int) (h * zoomFactorBack * SCALE_Y);
    }

    public static double transInvX(int x) {
        return (x - offsetBack.x) / zoomFactorBack / SCALE_X;
    }

    public static double transInvY(int y) {
        return (y - offsetBack.y) / zoomFactorBack / SCALE_Y;
    }

    public static double transInvW(int w) {
        return w / zoomFactorBack / SCALE_X;
    }

    public static double transInvH(int h) {
        return h / zoomFactorBack / SCALE_Y;
    }

    public static int getWorldDimX() {
        return (int) (getInstance().getWidth() / SCALE_X);
    }

    public static int getWorldDimY() {
        return (int) (getInstance().getHeight() / SCALE_Y);
    }

    public static double getZoomFactor() {
        return zoomFactorBack;
    }

    public static Point2d getOffset() {
        return offsetBack;
    }

    public static Point2d getCursorPosition() {
        return lastOffset;
    }

    public static Dimension getDrawingDimension() {
        return getInstance().window.getContentPane().getSize();
    }

    public static void setWindowBounds(Rectangle rect) {
        getInstance().window.setBounds(rect);
    }

    public static void setWindowTitle(String title) {
        getInstance().window.setTitle(title);
    }

    public static void initWithBounds(Rectangle rect) {
        if (instance == null) {
            instance = new Vis();

            // show window
            getInstance().window.setBounds(rect);
            instance.window.setVisible(true);
            instance.requestFocus();
        }
    }

    private static double transInvXCurrent(int x) {
        return (x - offset.x) / zoomFactor;
    }

    private static double transInvYCurrent(int y) {
        return (y - offset.y) / zoomFactor;
    }

    private static int transSCurrent(int s) {
        return (int) (s * zoomFactor);
    }

    private static void limitTransformation() {
        int windowWidth = getInstance().window.getContentPane().getWidth();
        int windowHeight = getInstance().window.getContentPane().getHeight();

        if (windowWidth > windowHeight) {
            if (zoomFactor < (double) windowWidth / DIM_X) {
                zoomFactor = (double) windowWidth / DIM_X;
            }
        } else {
            if (zoomFactor < (double) windowHeight / DIM_Y) {
                zoomFactor = (double) windowHeight / DIM_Y;
            }
        }

        if (offset.x > 0) {
            offset.x = 0;
        }
        if (offset.y > 0) {
            offset.y = 0;
        }
        if (transInvXCurrent(windowWidth) > DIM_X) {
            offset.x = -transSCurrent(DIM_X) + windowWidth;
        }
        if (transInvYCurrent(windowHeight) > DIM_Y) {
            offset.y = -transSCurrent(DIM_Y) + windowHeight;
        }
    }
    
    public static void setPosition(double offsetX, double offsetY, double zoom) {
        offset.set(offsetX * zoom, offsetY * zoom);
        zoomFactor = zoom;
    }

}
