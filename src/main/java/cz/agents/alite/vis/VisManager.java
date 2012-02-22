package cz.agents.alite.vis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import cz.agents.alite.vis.layer.VisLayer;

/**
 * The VisManager is a singleton holding the visualization layers and providing
 * the drawing cycle of them.
 *
 * The VisManager has to be explicitly initialized by the init() method. Without
 * its calling, the registered layers will be held, but the Vis singleton will
 * not be used, so the visualization window will not be created and shown. This
 * feature enables to use a live code for registering of the layers, but with
 * conditioned turning on of the visualization window only by the
 * VisManager.init() call.
 *
 * If the manager is not initialized, the drawing cycle is not started, so the
 * layers do not request any data from the visualized elements.
 *
 * Besides the registration and un-registration of the layers, the manager also
 * provides saving of a visualization state into a image file (the saveToFile()
 * method).
 *
 *
 * @author Jiri Vokrinek
 * @author Antonin Komenda
 * @author Ondrej Milenovsky
 */
public class VisManager {

    private static final int FPS_MAX = 24;
    private static final int VIS_THREAD_PRIORITY = Thread.MIN_PRIORITY;

    private static final List<VisLayer> layers = new CopyOnWriteArrayList<VisLayer>();
    private static VisManager instance = null;

    private VisManager() {
        new Thread(new Runnable() {

            @Override
                                public void run() {
                Thread.currentThread().setPriority(VIS_THREAD_PRIORITY);
                while (true) {
                    long startNanos = System.nanoTime();
                                    update();
                    long endNanos = System.nanoTime();

                    long sleepNanos = (long) (1.0 / FPS_MAX * 1000000000.0)
                            - (endNanos - startNanos);
                    sleepNanos = sleepNanos < 0 ? 0 : sleepNanos;
                    long sleepMillis = sleepNanos / 1000000;
                    sleepNanos -= sleepMillis * 1000000;

                    try {
                        Thread.sleep(sleepMillis, (int) sleepNanos);
                    } catch (InterruptedException ex) {

                                }
    }
            }
        }).start();
    }

    /**
     * sets initial parameters of the window, call this before creating the
     * window
     */
    public static void setInitParam(String title, int dimX, int dimY) {
        Vis.setInitParam(title, dimX, dimY);
    }

    /**
     * sets initial parameters of the window, call this before creating the
     * window
     */
    public static void setInitParam(String title, int dimX, int dimY, int sizeX, int sizeY) {
        Vis.setInitParam(title, dimX, dimY, sizeX, sizeY);
    }

    public static void setPanningBounds(Rectangle bounds) {
        Vis.setPanningBounds(bounds);
    }

    public static synchronized void init() {
        if (instance == null) {
            instance = new VisManager();

            for (VisLayer visLayer : layers) {
                visLayer.init(Vis.getInstance());
            }
        }
    }

    public static void registerLayer(VisLayer layer) {
        if (layers.contains(layer)) {
            return;
        }
        layers.add(layer);
        if (instance != null) {
            layer.init(Vis.getInstance());
        }
    }

    public static void unregisterLayer(VisLayer layer) {
        layers.remove(layer);
    }

    public static List<VisLayer> getLayers() {
        return Collections.unmodifiableList(layers);
    }

    public static void unregisterLayers() {
        for (VisLayer layer : layers) {
            layers.remove(layer);
        }
    }

    public static void saveToFile(String fileName, int width, int height) {
        try {
            ImageIO.write((RenderedImage) renderImage(width, height), "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image renderImage(int width, int height) {
        Image image = Vis.getInstance().createImage(width, height);
        Graphics2D graphics = (Graphics2D) image.getGraphics();

        for (VisLayer visLayer : layers) {
            drawLayer(visLayer, graphics);
        }
        return image;
    }

    private static void update() {
        Graphics2D graphics = Vis.getCanvas();
        for (VisLayer visLayer : layers) {
            drawLayer(visLayer, graphics);
        }
        Vis.flip();
    }

    private static void drawLayer(VisLayer visLayer, Graphics2D graphics) {
        try {
            visLayer.paint(graphics);
        } catch (ConcurrentModificationException e) {
            Logger.getLogger(VisManager.class.getName()).log(Level.DEBUG, "Skipped layer drawing.");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stacktrace = sw.toString();
            Logger.getLogger(VisManager.class.getName()).log(
                    Level.WARN,
                    "Vis layer " + visLayer
                            + " has thrown the following exception:\n"
                            + stacktrace);
        }
    }

}
