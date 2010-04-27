package cz.agents.alite.vis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import cz.agents.alite.vis.layer.VisLayer;


/**
 *
 * @author vokrinek
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

                    long sleepNanos = (long)(1.0/FPS_MAX*1000000000.0) - (endNanos  - startNanos);
                    sleepNanos = sleepNanos < 0? 0 : sleepNanos;
                    long sleepMillis = sleepNanos / 1000000;
                    sleepNanos -= sleepMillis*1000000;

                    try {
                        Thread.sleep(sleepMillis, (int) sleepNanos);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }).start();
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

    public static void saveToFile(String fileName) {
        try {
            ImageIO.write((RenderedImage) renderImage(), "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image renderImage() {
        Image image = Vis.getInstance().createImage(1000, 1000);
        Graphics2D graphics = (Graphics2D) image.getGraphics();

        for (VisLayer visLayer : layers) {
            try {
                visLayer.paint(graphics);
            } catch (Exception e) {
                Logger.getLogger(VisManager.class.getName()).log(Level.SEVERE, "Skipped layer drawing during file save.");
            }
        }
        return image;
    }

    private static void update() {
        for (VisLayer visLayer : layers) {
            try {
                visLayer.paint(Vis.getCanvas());
            } catch (ConcurrentModificationException e) {
                Logger.getLogger(VisManager.class.getName()).log(Level.FINEST, "Skipped layer drawing.");
            } catch (Exception e) {
                Logger.getLogger(VisManager.class.getName()).log(Level.FINEST, "Skipped layer drawing.");
            }
        }
        Vis.flip();
    }


}
