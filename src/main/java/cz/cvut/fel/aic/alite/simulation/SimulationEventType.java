package cz.cvut.fel.aic.alite.simulation;

import cz.cvut.fel.aic.alite.common.event.EventType;

/**
 * The enumeration describes events originated in the {@link Simulation}.
 *
 *
 * @author Antonin Komenda
 */
public enum SimulationEventType  implements EventType {
    SIMULATION_STARTED, SIMULATION_FINISHED
}
