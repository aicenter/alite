package cz.agents.alite.simulation;

import java.util.LinkedList;
import java.util.List;
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

	private final List<EventListener> eventListeners = new LinkedList<EventListener>();

	private final long sleepTimeIfWaitToOtherEvent;
	private final boolean SLEEP_TIME_FLAG; // reason for using flag is back compatibility

	public Simulation(long sleepTimeIfWaitToOtherEvent) {
		super();
		this.sleepTimeIfWaitToOtherEvent = sleepTimeIfWaitToOtherEvent;
		this.SLEEP_TIME_FLAG = true;
	}

	public Simulation() {
		this.SLEEP_TIME_FLAG = false;
		this.sleepTimeIfWaitToOtherEvent = 0;
	}

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

	public long getEventCount() {
		return eventCount;
	}

	public void addEventListener(EventListener listener) {
		eventListeners.add(listener);
	}

	public void removeEventListener(EventListener listener) {
		eventListeners.remove(listener);
	}

	/** sets listener for synchronized drawing vis frames between events */
	public void setDrawListener(DrawListener listener) {
		drawListener = listener;
	}

	public DrawListener getDrawListener() {
		return drawListener;
	}

	/**
	 * request drawing frame, if not running, returns false and not draws it !
	 * if running, draws after some time
	 */
	public boolean requestDraw() {
		if (!isRunning()) {
			return false;
		} else {
			drawFrame = true;
			return true;
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

	/** 0 means maximum */
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
		callEventListeners();
		// draws frame for vis
		if (drawFrame && (drawListener != null)
				&& (System.currentTimeMillis() > lastDrawed + drawReload)) {
			timeToSleep = drawFrame(timeToSleep);
		}

		if(SLEEP_TIME_FLAG){
        	if(timeToSleep > sleepTimeIfWaitToOtherEvent){
        		timeToSleep = sleepTimeIfWaitToOtherEvent;
        	}else{
        		timeToSleep = 0;
        	}
        	
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

	private void callEventListeners() {
		for (EventListener listener : eventListeners) {
			listener.eventFired();
		}
	}

	private long drawFrame(long timeToSleep) {
		drawFrame = false;
		long startTime = System.currentTimeMillis();
		final long drawDeadline = System.currentTimeMillis()
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
