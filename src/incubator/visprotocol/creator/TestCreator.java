package incubator.visprotocol.creator;

import incubator.visprotocol.protocol.MemoryProtocol;
import incubator.visprotocol.protocol.Protocol;
import incubator.visprotocol.sampler.MaxFPSRealTimeSampler;
import incubator.visprotocol.structprocessor.Differ;
import incubator.visprotocol.structprocessor.LightMux;
import incubator.visprotocol.structprocessor.Updater;
import incubator.visprotocol.vis.layer.common.FillColorProxyLayer;
import incubator.visprotocol.vis.layer.example.BrainzProxyLayer;
import incubator.visprotocol.vis.layer.example.SimInfoProxyLayer;
import incubator.visprotocol.vis.layer.example.ZombieProxyLayer;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.Vis2DParams;
import incubator.visprotocol.vis.output.painter.RootPainter;
import incubator.visprotocol.vis.output.vis2d.MoveTransformator;
import incubator.visprotocol.vis.output.vis2d.ZoomTransformator;
import incubator.visprotocol.vis.output.vis2d.painter.Vis2DBasicPainters;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.vecmath.Point3d;

import cz.agents.alite.creator.Creator;

public class TestCreator implements Creator {

    private static final int DELAY = 100;
    private ExampleEnvironment exampleEnvironment;
    private Vis2DOutput vis2d;

    @Override
    public void init(String[] args) {
    }

    @Override
    public void create() {
        createEnvironment();
        createAndRunVis();
        createAndRunSimulation();
    }

    private void createEnvironment() {
        exampleEnvironment = new ExampleEnvironment();
    }

    private void createAndRunVis() {
        Vis2DParams params = new Vis2DParams();
        params.worldBounds = new Rectangle2D.Double(-400, -600, 11000, 11000);
        vis2d = new Vis2DOutput(params);
        vis2d.addTransformator(new ZoomTransformator());
        vis2d.addTransformator(new MoveTransformator());

        // joint between proxies and protocol
        final Differ differ = new Differ();

        // layers
        final LightMux rootProxyLayer = new LightMux(differ);
        rootProxyLayer.addProcessor(new SimInfoProxyLayer(Vis2DBasicPainters.ELEMENT_TYPES));
        rootProxyLayer.addProcessor(new FillColorProxyLayer(Color.WHITE, ".Undead land.Other",
                Vis2DBasicPainters.ELEMENT_TYPES));
        rootProxyLayer.addProcessor(new BrainzProxyLayer(1000, 10000,
                Vis2DBasicPainters.ELEMENT_TYPES));
        rootProxyLayer.addProcessor(new ZombieProxyLayer(exampleEnvironment,
                Vis2DBasicPainters.ELEMENT_TYPES));

        // protocol
        // use protocol storing the visual elements (Points) into memory
        final Protocol protocol = new MemoryProtocol();

        // joint between protocol and painters
        final Updater updater = new Updater();

        // outputs
        final RootPainter painter = new RootPainter();
        painter.addPainters(Vis2DBasicPainters.createBasicPainters(vis2d));

        // sampler
        MaxFPSRealTimeSampler sampler = new MaxFPSRealTimeSampler() {
            @Override
            protected void sample() {
                // generate update struct and fill the used protocol by new struct
                protocol.push(rootProxyLayer.pull());
                // update current state
                updater.push(protocol.pull());
                // draw the elements using the painters, they will get what they want from the
                // current state in updater
                painter.paint(updater.pull());

                // TODO: should be done probably by painter ...but I don't think so
                vis2d.flip();
            }
        };
        sampler.start();
    }

    private void createAndRunSimulation() {
        Random random = new Random();
        while (true) {
            exampleEnvironment.exampleString = "string" + Long.toString(random.nextLong());
            exampleEnvironment.examplePosition = new Point3d(random.nextDouble() * 20.0 + 100.0,
                    random.nextDouble() * 20.0 + 100.0, random.nextDouble() * 20.0 + 100.0);
            exampleEnvironment.exampleInteger = random.nextInt(256);

            exampleEnvironment.exampleTime++;

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ExampleEnvironment implements ZombieProvider {

        private long exampleTime = 1;

        private String exampleString = "string";
        private Point3d examplePosition = new Point3d();
        private int exampleInteger = 100;

        @Override
        public String getPersonName() {
            return exampleString;
        }

        @Override
        public Point3d getPersonPosition() {
            return examplePosition;
        }

        @Override
        public int getPersonHealth() {
            return exampleInteger;
        }

    }

}
