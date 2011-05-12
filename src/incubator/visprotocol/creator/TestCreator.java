package incubator.visprotocol.creator;

import incubator.visprotocol.factory.VisFactory;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.sampler.MaxFPSRealTimeSampler;
import incubator.visprotocol.vis.layer.common.FillColorLayer;
import incubator.visprotocol.vis.layer.common.TimeHolder;
import incubator.visprotocol.vis.layer.common.TimeLayer;
import incubator.visprotocol.vis.layer.common.Vis2DInfoLayer;
import incubator.visprotocol.vis.layer.example.DynamicPointsLayer;
import incubator.visprotocol.vis.layer.example.PentagramLayer;
import incubator.visprotocol.vis.layer.example.PersonLayer;
import incubator.visprotocol.vis.layer.example.ScreenTextLayer;
import incubator.visprotocol.vis.layer.example.StaticPoints;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.Vis2DParams;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.vecmath.Point3d;

import cz.agents.alite.creator.Creator;

/**
 * Example creator for vis protocol.
 * 
 * Protocol: Difference from last state is generated by layers. The difference is sent through
 * protocol, then last state on the other side is updated by the differences. Current state is
 * painted.
 * 
 * Realtime: State is merged from layers, the state is painted.
 * 
 * Direct: Layers draw directly to output, no state storing. (not implemented)
 * 
 * Save to file: Saves record to file, so it can by played later, also opens vis 2d as protocol mode
 * 
 * Player from file: Plays from the file.
 * 
 * @author Ondrej Milenovsky
 * */
public class TestCreator implements Creator {

    private static final int DELAY = 20;
    private ExampleEnvironment exampleEnvironment;
    private StructProcessor root;
    private StructProcessor stream;

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

        // V realtime modu je to tak 3x rychlejsi nez protocol. Direct este rychlejsi, ale nema
        // ulozenej aktualni stav, hodne trhane dokaze i 1M bodu. Kdyz je direct, tak se z proxy
        // musi generovat body pokazdy, u ostatnich staci jednou na zacatku.
        final Mode mode = Mode.PLAYER_FROM_FILE;
        // 100k se trochu trha, 200k se dost trha, 1M u protocolu dosla pamet
        int nDynamicPoints = 10;
        // staticky body, tech to zvladne hodne, tady je direct nejpomalejsi
        int nStaticPoints = 10;

        VisFactory factory = new VisFactory();

        // layers
        factory.addLayer(new TimeLayer(exampleEnvironment));
        factory.addLayer(new FillColorLayer(Color.BLACK));
        factory.addLayer(new PentagramLayer(exampleEnvironment));
        factory.addLayer(new StaticPoints(nStaticPoints, 10000));
        factory.addLayer(new DynamicPointsLayer(nDynamicPoints, 10000));
        factory.addLayer(new PersonLayer(exampleEnvironment));
        factory.addLayer(new ScreenTextLayer(exampleEnvironment));

        Vis2DOutput vis2d = null;

        if (mode == Mode.REALTIME) {
            factory.createRealtimeProtocol();
            vis2d = factory.createVis2DOutput(params);
            factory.addOutputLayer(new Vis2DInfoLayer(vis2d));
        } else if (mode == Mode.PROTOCOL) {
            factory.createMemoryProcotol();
            vis2d = factory.createVis2DOutput(params);
            factory.addOutputLayer(new Vis2DInfoLayer(vis2d));
        } else if (mode == Mode.SAVE_TO_FILE) {
            stream = factory.createFileWriterProtocol("record.rec");
            factory.createMemoryProcotol();
            vis2d = factory.createVis2DOutput(params);
            factory.addOutputLayer(new Vis2DInfoLayer(vis2d));
        } else if (mode == Mode.PLAYER_FROM_FILE) {
            factory.createFileReaderProtocol("record.rec");
            vis2d = factory.createVis2DPlayerOutput(params);
            factory.addOutputLayer(new Vis2DInfoLayer(vis2d));
        }
        root = vis2d;

        // TODO second sampler for file saver

        // TODO refactor player

        // sampler
        MaxFPSRealTimeSampler sampler = new MaxFPSRealTimeSampler() {
            @Override
            protected void sample() {
                root.pull();
                if (stream != null) {
                    stream.pull();
                }
            }
        };
        sampler.start();
    }

    private void createAndRunSimulation() {
        Random random = new Random();
        while (true) {
            exampleEnvironment.examplePosition = new Point3d(random.nextDouble() * 200.0 + 100.0,
                    random.nextDouble() * 200.0 + 100.0, random.nextDouble() * 20.0 + 100.0);
            exampleEnvironment.exampleInteger = random.nextInt(256);

            exampleEnvironment.exampleTime += DELAY;

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ExampleEnvironment implements PersonProvider, TimeHolder {

        private long exampleTime = 1;

        private String exampleString = "Crazy person";
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

        @Override
        public long getCurrentTimeMillis() {
            return exampleTime;
        }

    }

    private enum Mode {
        /** proxies -> differ -> protocol -> updater -> painter */
        PROTOCOL,
        /** proxies -> updater -> painter */
        REALTIME,
        /** proxies -> painter, but the current state is not stored */
        @Deprecated
        DIRECT,
        /** protocol + save to file */
        SAVE_TO_FILE,
        /** no environment, player from file */
        PLAYER_FROM_FILE,
    }

}
