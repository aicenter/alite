package incubator.visprotocol.creator;

import incubator.visprotocol.processor.Forwarder;
import incubator.visprotocol.processor.LightPullMux;
import incubator.visprotocol.processor.PullForwarder;
import incubator.visprotocol.processor.updater.DiffUpdater;
import incubator.visprotocol.processor.updater.Differ;
import incubator.visprotocol.processor.updater.MergeUpdater;
import incubator.visprotocol.protocol.MemoryProtocol;
import incubator.visprotocol.sampler.MaxFPSRealTimeSampler;
import incubator.visprotocol.structure.key.Vis2DCommonKeys;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.common.FillColorProxyLayer;
import incubator.visprotocol.vis.layer.example.BrainzProxyLayer;
import incubator.visprotocol.vis.layer.example.LightsProxyLayer;
import incubator.visprotocol.vis.layer.example.PentagramLayer;
import incubator.visprotocol.vis.layer.example.ScreenTextLayer;
import incubator.visprotocol.vis.layer.example.SimInfoProxyLayer;
import incubator.visprotocol.vis.layer.example.ZombieProxyLayer;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.Vis2DParams;
import incubator.visprotocol.vis.output.painter.RootPainter;
import incubator.visprotocol.vis.output.vis2d.Vis2DBasicTransformators;
import incubator.visprotocol.vis.output.vis2d.painter.Vis2DBasicPainters;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.vecmath.Point3d;

import cz.agents.alite.creator.Creator;

public class TestCreator implements Creator {

    private static final int DELAY = 20;
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
        vis2d.addTransformators(Vis2DBasicTransformators.createBasicTransformators());

        // TODO bug kdyz je protocol, u dynamickych bodu se obcas neprepisou parametry

        // V realtime modu je to tak 3x rychlejsi nez protocol. Direct este rychlejsi, ale nema
        // ulozenej aktualni stav, hodne trhane dokaze i 1M bodu. Kdyz je direct, tak se z proxy
        // musi generovat body pokazdy, u ostatnich staci jednou na zacatku (posledni parametr u
        // BrainzLayer).
        final Mode mode = Mode.PROTOCOL;
        // 10k bodu este v pohode, 100k se trochu trha, 200k se dost trha, 1M jsem se nedockal
        int nDynamicPoints = 1000;
        // staticky body, tech to zvladne hodne, tady je direct nejpomalejsi (nevim proc)
        int nStaticPoints = 10000;

        // layers mux
        LightPullMux collector = new LightPullMux();
        // filter
        FilterStorage filter = new FilterStorage(Vis2DBasicPainters.ELEMENT_TYPES,
                Vis2DCommonKeys.COMMON_PARAMS);
        // layers
        collector.addProcessor(new SimInfoProxyLayer(exampleEnvironment, filter));
        collector.addProcessor(new FillColorProxyLayer(Color.BLACK, ".Undead land.Other", filter));
        collector.addProcessor(new PentagramLayer(exampleEnvironment, filter));
        collector.addProcessor(new BrainzProxyLayer(nStaticPoints, 10000, filter,
                mode != Mode.DIRECT));
        collector.addProcessor(new LightsProxyLayer(nDynamicPoints, 10000, filter));
        collector.addProcessor(new ZombieProxyLayer(exampleEnvironment, filter));
        collector.addProcessor(new ScreenTextLayer(exampleEnvironment, filter));

        // outputs
        RootPainter painter = new RootPainter();
        painter.addPainters(Vis2DBasicPainters.createBasicPainters(vis2d));

        // the chain of components
        final Forwarder chain;
        // fill the chain
        if (mode == Mode.DIRECT) {
            collector.setOutput(painter);
            chain = collector;
        } else if (mode == Mode.REALTIME) {
            collector.setOutput(new MergeUpdater());
            chain = new PullForwarder(collector, painter);
        } else if (mode == Mode.PROTOCOL) {
            collector.setOutput(new Differ());
            chain = new PullForwarder(collector, new MemoryProtocol(), new DiffUpdater(), painter);
        } else {
            chain = null;
        }

        // sampler
        MaxFPSRealTimeSampler sampler = new MaxFPSRealTimeSampler() {
            @Override
            protected void sample() {
                chain.forward();

                // TODO: should be done probably by painter
                vis2d.flip();
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

        private String exampleString = "Green zombie";
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

        public long getTime() {
            return exampleTime;
        }

    }

    private enum Mode {
        /** proxies -> differ -> protocol -> updater -> painter */
        PROTOCOL,
        /** proxies -> updater -> painter */
        REALTIME,
        /** proxies -> painter, but the current state is not stored */
        DIRECT
    }

}
