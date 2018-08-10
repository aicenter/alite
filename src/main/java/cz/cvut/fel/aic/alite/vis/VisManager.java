/*
 * Copyright (C) 2017 Czech Technical University in Prague.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.alite.vis;

import java.awt.*;
import java.awt.image.BufferedImage;
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
import javax.vecmath.Point2d;

import cz.cvut.fel.aic.alite.common.event.EventProcessor;
import cz.cvut.fel.aic.alite.common.event.typed.ScreenRecordingEventHandler;
import io.humble.video.*;
import io.humble.video.awt.MediaPictureConverter;
import io.humble.video.awt.MediaPictureConverterFactory;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import cz.cvut.fel.aic.alite.vis.layer.VisLayer;

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

    private static long frame_no = 0;
    private static Muxer muxer;
    private static Encoder encoder;
    private static Rational framerate;
    private static MediaPacket packet;
    private static MediaPicture picture;
    private static MediaPictureConverter converter;
    private static EventProcessor processor;
    private static ScreenRecordingEventHandler handler;

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
        }, "Alite VisManager Thread").start();
    }

    /**
     * sets initial parameters of the window, call this before creating the
     * window
     *
     */
    public static void setInitParam(final String title, final int canvasWidth, final int canvasHeight) {
        Vis.setInitParam(title, canvasWidth, canvasHeight);
    }

    /**
     * sets initial parameters of the window, call this before creating the
     * window
     */
    public static void setInitParam(final String title, final int canvasWidth, final int canvasHeight, final int worldSizeX, final int worldSizeY) {
        setInitParam(title, canvasWidth, canvasHeight);
        setSceneParam(new SceneParams(){

            @Override
            public Rectangle getWorldBounds() {
                return new Rectangle(0, 0, worldSizeX, worldSizeY);
            }
        });
    }

    /**
     * @deprecated use setSceneParams instead
     */
    public static void setPanningBounds(Rectangle bounds) {
        Vis.setPanningBounds(bounds);
    }

    public static void setInvertYAxis(boolean enabled) {
        Vis.setInvertYAxis(enabled);
    }

    public static void setSceneParam(SceneParams sceneParams) {
        Vis.setSceneParams(sceneParams);
    }

    public static synchronized void init(EventProcessor eventProcessor) {
        VisManager.processor = eventProcessor;
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
        if (instance != null) {
            layer.deinit(Vis.getInstance());
        }
        layers.remove(layer);
    }

    public static List<VisLayer> getLayers() {
        return Collections.unmodifiableList(layers);
    }

    public static void unregisterLayers() {
        for (VisLayer layer : layers) {
            if (instance != null) {
                layer.deinit(Vis.getInstance());
            }
            layers.remove(layer);
        }
    }

    public static void swapLayers(VisLayer x, VisLayer y) {
        int xpos = layers.indexOf(x);
        int ypos = layers.indexOf(y);
        Collections.swap(layers, xpos, ypos);
    }

    public static void saveToFile(String fileName, int width, int height) {
        try {
            ImageIO.write((RenderedImage) renderImage(width, height), "png", new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startVideoRecording(String fileName, int width, int height){
        framerate = Rational.make(1, 25);

        /** First we create a muxer using the passed in filename and formatname if given. */
        muxer = Muxer.make(fileName, null, null);

        /** Now, we need to decide what type of codec to use to encode video. Muxers
         * have limited sets of codecs they can use. We're going to pick the first one that
         * works, or if the user supplied a codec name, we're going to force-fit that
         * in instead.
         */

        final Codec codec = Codec.findEncodingCodec(Codec.ID.CODEC_ID_H264);

        /**
         * Now that we know what codec, we need to create an encoder
         */
        encoder = Encoder.make(codec);

        /**
         * Video encoders need to know at a minimum:
         *   width
         *   height
         *   pixel format
         * Some also need to know frame-rate (older codecs that had a fixed rate at which video files could
         * be written needed this). There are many other options you can set on an encoder, but we're
         * going to keep it simpler here.
         */
        encoder.setWidth(width);
        encoder.setHeight(height);
        // We are going to use 420P as the format because that's what most video formats these days use
        final PixelFormat.Type pixelformat = PixelFormat.Type.PIX_FMT_YUV420P;
        encoder.setPixelFormat(pixelformat);
        encoder.setTimeBase(framerate);

        /** An annoynace of some formats is that they need global (rather than per-stream) headers,
         * and in that case you have to tell the encoder. And since Encoders are decoupled from
         * Muxers, there is no easy way to know this beyond
         */
        if (muxer.getFormat().getFlag(MuxerFormat.Flag.GLOBAL_HEADER))
            encoder.setFlag(Encoder.Flag.FLAG_GLOBAL_HEADER, true);

        /** Open the encoder. */
        encoder.open(null, null);


        /** Add this stream to the muxer. */
        muxer.addNewStream(encoder);

        /** And open the muxer for business. */
        try {
            muxer.open(null, null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /** Next, we need to make sure we have the right MediaPicture format objects
         * to encode data with. Java (and most on-screen graphics programs) use some
         * variant of Red-Green-Blue image encoding (a.k.a. RGB or BGR). Most video
         * codecs use some variant of YCrCb formatting. So we're going to have to
         * convert. To do that, we'll introduce a MediaPictureConverter object later. object.
         */
        picture = MediaPicture.make(
                encoder.getWidth(),
                encoder.getHeight(),
                pixelformat);
        picture.setTimeBase(framerate);

        handler = new ScreenRecordingEventHandler(processor, width, height);
        handler.recordingStarted();
        processor.addEvent(handler, 40);
    }

    public static void encodeFrame(int width, int height) {

        packet = MediaPacket.make();
        final BufferedImage screen = convertToType(renderImage(width, height), width, height, BufferedImage.TYPE_3BYTE_BGR);

        /** This is LIKELY not in YUV420P format, so we're going to convert it using some handy utilities. */
        if (converter == null)
            converter = MediaPictureConverterFactory.createConverter(screen, picture);
        converter.toPicture(picture, screen, frame_no);
        frame_no++;

        do {
            encoder.encode(packet, picture);
            if (packet.isComplete())
                muxer.write(packet, false);
        } while (packet.isComplete());

    }

    public static void stopVideoRecording(){
        handler.recordingStopped();
    }

    public static void finishVideoRecording(){
        /** Encoders, like decoders, sometimes cache pictures so it can do the right key-frame optimizations.
         * So, they need to be flushed as well. As with the decoders, the convention is to pass in a null
         * input until the output is not complete.
         */
        packet = MediaPacket.make();
        do {
            encoder.encode(packet, null);
            if (packet.isComplete())
                muxer.write(packet,  false);
        } while (packet.isComplete());

        /** Finally, let's clean up after ourselves. */
        muxer.close();
        frame_no = 0;
    }

    public static Image renderImage(int width, int height) {
        Image image = Vis.getInstance().createImage(width, height);
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

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

    private static BufferedImage convertToType(Image sourceImage, int width, int height, int targetType)
    {
        BufferedImage image = new BufferedImage(width, height, targetType);
        image.getGraphics().drawImage(sourceImage, 0, 0, null);
        return image;
    }

    /**
     * Extend this class to set custom scene parameters using the {@link VisManager#setSceneParam(SceneParams) method.}
     */
    public static class SceneParams {

        public Rectangle getWorldBounds() {
            return new Rectangle(-Integer.MAX_VALUE/2, -Integer.MAX_VALUE/2, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }

        public Point2d getDefaultLookAt() {
            Rectangle world = getWorldBounds();
            return new Point2d(world.x + world.width/2, world.y + world.height/2);
        }

        public double getDefaultZoomFactor() {
            return 1.0;
        }

    }

}