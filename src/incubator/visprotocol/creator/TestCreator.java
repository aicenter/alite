package incubator.visprotocol.creator;

import incubator.visprotocol.vis.layer.PersonLayer;
import incubator.visprotocol.vis.layer.RandomPointsLayer;
import incubator.visprotocol.vis.layer.proxy.RootProxyLayer;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.Vis2DParams;
import incubator.visprotocol.vis.output.painter.RootPainter;
import incubator.visprotocol.vis.output.painter.vis2d.PointPainterVis2D;
import incubator.visprotocol.vis.output.vis2d.MoveTransformator;
import incubator.visprotocol.vis.output.vis2d.ZoomTransformator;
import incubator.visprotocol.vis.protocol.MemoryProtocol;
import incubator.visprotocol.vis.protocol.Protocol;
import incubator.visprotocol.vis.sampler.MaxFPSRealTimeSampler;

import java.awt.Color;
import java.awt.Graphics;
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
	new ZoomTransformator(vis2d);
	new MoveTransformator(vis2d);

	// layers
	final RootProxyLayer rootProxyLayer = new RootProxyLayer();
	// using the PersonLayer (similarly to the old vis) create a
	// PointProxyLayer building the Points for the visualization protocol
	// TODO works only the last layer
	rootProxyLayer.addLayer(PersonLayer.create(exampleEnvironment));
	rootProxyLayer.addLayer(RandomPointsLayer.create(1000));
	// XXX: in future all the layers of the old vis should be replicated
	// using the proxies here

	// protocol
	// use protocol storing the visual elements (Points) into memory
	final Protocol protocol = new MemoryProtocol();
	// XXX: in future there can be various protocols storing the
	// visualization elements, using network to provide visualization
	// stream, etc.

	// outputs
	final RootPainter rootPainter = new RootPainter();
	// create a Painter drawing the visual elements (Points) using the
	// Vis2DOutput (2D output based on old Vis class)
	rootPainter.addPainter(new PointPainterVis2D(vis2d));
	// create a Painter drawing the visual elements (Points) using sysout
	// (for demonstarion of multi-output use)
	// TODO: generalize the painter to string painter (it can be used as
	// logger with different outputs - sysout, errlog, ...)
	// rootPainter.addPainter(new PointPainterSysout());
	// XXX: in the future there can be other Outputs: GoogleEarth, 3D jME,
	// Aglobe Visio, etc

	// sampler
	MaxFPSRealTimeSampler sampler = new MaxFPSRealTimeSampler() {

	    @Override
	    protected void sample() {
		// TODO: should be BackgroundProxyLayer with Vis2D painter and
		// probably even sysout painter
		Graphics graphics = vis2d.getGraphics2D();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, 1000, 1000);

		// fill the used protocol using the proxy layers
		rootProxyLayer.fillProtocol(protocol);
		// draw the elements using the painters
		rootPainter.paint(protocol);

		// inform the protocol, that the "frame" was completed
		protocol.nextStep();
		// XXX: in future the nextStep can be called in different time
		// points (depending on type of visualization: synchronized,
		// asynchronous, network, ...)

		// TODO: should be done probably by painter
		vis2d.flip();
	    }

	};
	sampler.start();
    }

    private void createAndRunSimulation() {
	Random random = new Random();
	while (true) {
	    exampleEnvironment.exampleString = "string"
		    + Long.toString(random.nextLong());
	    exampleEnvironment.examplePosition = new Point3d(random
		    .nextDouble() * 20.0 + 100.0,
		    random.nextDouble() * 20.0 + 100.0,
		    random.nextDouble() * 20.0 + 100.0);
	    exampleEnvironment.exampleInteger = random.nextInt();

	    exampleEnvironment.exampleTime++;

	    try {
		Thread.sleep(DELAY);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public static class ExampleEnvironment implements VisualPersonProvider {

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
