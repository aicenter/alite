package cz.agents.alite.simulation;

import java.util.logging.Level;
import java.util.logging.Logger;

import cz.agents.alite.common.event.Event;
import cz.agents.alite.common.event.EventProcessor;
import cz.agents.alite.environment.Sensor;

/**
 * The simulation is a wrapper of the {@link EventProcessor} with
 * an additional functionality covering adjustable simulation speed.
 *
 * An agent should never get a reference to the simulation. The simulation should
 * be directly used only by other services of the universe (environment,
 * communication, etc). The agent is controlled by the simulation only indirectly:
 * by {@link Sensor} callbacks, message receiving, and so on.
 *
 *
 * @author Antonin Komenda
 */
public class Simulation extends EventProcessor {

    private double simulationSpeed = 0;
    private long eventCount = 0;
    private long runTime;
    private int printouts = 10000;

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
        System.out.format(">>> RUNTIME: %.2fs\n", (System.currentTimeMillis() - runTime) / 1000.0);

    }

    public void setSimulationSpeed(final double simulationSpeed) {
        this.simulationSpeed = simulationSpeed;
    }

    public double getSimulationSpeed() {
        return simulationSpeed;
    }
    
    /** print every n-th event */
    public void setPrintouts(int n)
    {
        this.printouts = n;
    }

    @Override
    protected void breforeRunningTest(Event event) {
        ++eventCount;
        if (eventCount % printouts == 0) {
            System.out.format(">>> TIME: %ds / RUNTIME: %.2fs / EVENTS: %d / QUEUE: %d \n", getCurrentTime() / 1000, (System.currentTimeMillis() - runTime) / 1000.0, eventCount, getCurrentQueueLength());
        }

        if (simulationSpeed > 0) {
            try {
                Thread.sleep((long) ((event.getTime() - getCurrentTime()) * simulationSpeed));
            } catch (InterruptedException ex) {
                Logger.getLogger(EventProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
