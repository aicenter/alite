package cz.agents.alite.simulation;

import java.util.logging.Level;
import java.util.logging.Logger;

import cz.agents.alite.common.event.Event;
import cz.agents.alite.common.event.EventProcessor;
import cz.agents.alite.environment.Sensor;

/**
 * The simulation is a wrapper of the {@link EventProcessor} with an additional
 * functionality covering adjustable simulation speed.
 * 
 * An agent should never get a reference to the simulation. The simulation
 * should be directly used only by other services of the universe (environment,
 * communication, etc). The agent is controlled by the simulation only
 * indirectly: by {@link Sensor} callbacks, message receiving, and so on.
 * 
 * 
 * @author Antonin Komenda
 */
public class Simulation extends EventProcessor {

    private double simulationSpeed = 0;
    private long eventCount = 0;
    private long runTime;
    private int printouts = 10000;

    /** drawing frame requested */
    private boolean drawFrame = false;
    private DrawListener drawListener;
    /** timeout for draw listener */
    private long drawTimeout = 1000;
    /** min delay between drawings */
    private long drawReload = 40;
    /** last time drawed */
    private long lastDrawed = 0;
    /** this is not used as class variable but it must be */
    private long drawDeadline;
    /** draw when not running */
    private boolean drawNotRunning = false;

    public void run() {
	runTime = System.currentTimeMillis();
	System.out.println("\n>>> SIMULATION START\n");

	fireEvent(SimulationEventType.SIMULATION_STARTED, null, null, null);
	super.run();
	fireEvent(SimulationEventType.SIMULATION_FINISHED, null, null, null);

	System.out.println("\n>>> SIMULATION FINISH");
	System.out.println();
	System.out.format(">>> END TIME: %dms\n", getCurrentTime());
	System.out.format(">>> EVENT COUNT: %d\n", eventCount);
	System.out.println();
	System.out.format(">>> RUNTIME: %.2fs\n",
		(System.currentTimeMillis() - runTime) / 1000.0);

    }

    /** sets listener for synchronized drawing vis frames between events */
    public void setDrawListener(DrawListener listener) {
	drawListener = listener;
    }

    public DrawListener getDrawListener() {
	return drawListener;
    }

    /** if simulation not running, draw or not ? */
    public void setDrawNotRunning(boolean drawNotRunning) {
	this.drawNotRunning = drawNotRunning;
    }

    /**
     * request drawing frame, if not running, draws now with inf deadline and
     * return true, immediate simulation start can cause problems
     */
    public boolean requestDraw() {
	if (!isRunning() && drawNotRunning) {
	    drawListener.drawFrame(Long.MAX_VALUE);
	    return true;
	} else {
	    drawFrame = true;
	    return false;
	}
    }

    /**
     * sets draw timeout, 0 means timeout is given by simulaiton speed
     */
    public void setDrawTimeout(long timeout) {
	if (timeout < 0) {
	    throw new IllegalArgumentException("Timeout must be >= 0");
	}
	this.drawTimeout = timeout;
    }

    /**
     * sets min interval between drawings, time for simulation to reload for new
     * drawing
     */
    public void setDrawReload(long drawReload) {
	if (drawReload < 0) {
	    throw new IllegalArgumentException("Reload time must be >= 0");
	}
	this.drawReload = drawReload;
    }

    public void setSimulationSpeed(final double simulationSpeed) {
	this.simulationSpeed = simulationSpeed;
    }

    public double getSimulationSpeed() {
	return simulationSpeed;
    }

    /** print every n-th event */
    public void setPrintouts(int n) {
	this.printouts = n;
    }

    @Override
    protected void breforeRunningTest(Event event) {
	++eventCount;
	if (eventCount % printouts == 0) {
	    System.out
		    .format(
			    ">>> TIME: %ds / RUNTIME: %.2fs / EVENTS: %d / QUEUE: %d \n",
			    getCurrentTime() / 1000, (System
				    .currentTimeMillis() - runTime) / 1000.0,
			    eventCount, getCurrentQueueLength());
	}

	long timeToSleep = (long) ((event.getTime() - getCurrentTime()) * simulationSpeed);
	// draws frame for vis
	if (drawFrame && (drawListener != null)
		&& (System.currentTimeMillis() > lastDrawed + drawReload)) {
	    timeToSleep = drawFrame(timeToSleep);
	}

	if ((simulationSpeed > 0) && (timeToSleep > 0)) {
	    try {
		Thread.sleep(timeToSleep);
	    } catch (InterruptedException ex) {
		Logger.getLogger(EventProcessor.class.getName()).log(
			Level.SEVERE, null, ex);
	    }
	}
    }

    private long drawFrame(long timeToSleep) {
	drawFrame = false;
	long startTime = System.currentTimeMillis();
	drawDeadline = System.currentTimeMillis()
		+ Math.max(timeToSleep, drawTimeout);
	// start drawing thread
	Thread thread2 = new Thread(new Runnable() {
	    @Override
	    public void run() {
		drawListener.drawFrame(drawDeadline);
	    }
	});
	thread2.start();
	// wait
	try {
	    long time = Math.max(drawDeadline - System.currentTimeMillis(), 1);
	    thread2.join(time);
	} catch (InterruptedException e) {
	    Logger.getLogger(EventProcessor.class.getName()).log(Level.INFO,
		    null, e);
	}
	lastDrawed = System.currentTimeMillis();
	// recompute event delay
	return timeToSleep + startTime - System.currentTimeMillis();
    }

}
