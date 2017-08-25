package cz.cvut.fel.aic.alite.environment.eventbased;

import cz.cvut.fel.aic.alite.common.entity.Entity;
import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessor;
import cz.cvut.fel.aic.alite.environment.Action;
import cz.cvut.fel.aic.alite.environment.Storage;

/**
 * The EventBasedActions can use the event processor to postpone effects of their
 * act() methods.
 *
 * Each act() method of a particular event-based action can add an event into
 * the related event processor. Later, if the event is received by the
 * handleEvent() method, the process initiated by the act() method should be
 * appropriately finished (the data in {@link Storage}s has to be updated).
 *
 *
 * @author Antonin Komenda
 */
public abstract class EventBasedAction extends Action implements EventHandler {

    private final EventBasedEnvironment environment;

    public EventBasedAction(EventBasedEnvironment environment, Entity relatedEntity) {
        super(environment, relatedEntity);

        this.environment = environment;
    }

    protected EventBasedEnvironment getEnvironment() {
        return environment;
    }

    @Override
    public EventProcessor getEventProcessor() {
        return environment.getEventProcessor();
    }

    @Override
    public void handleEvent(Event event) {
    }

}
