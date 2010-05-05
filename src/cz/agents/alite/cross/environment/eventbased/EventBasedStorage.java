package cz.agents.alite.cross.environment.eventbased;

import cz.agents.alite.environment.Storage;
import cz.agents.alite.event.Event;
import cz.agents.alite.event.EventHandler;
import cz.agents.alite.event.EventProcessor;

/**
 * An EventBasedStorage can use the event processor to implement a spontaneous
 * changes of the held data.
 *
 * An example can be a time ticker storage. The storage would repeatedly add an
 * event into the event processor and for each receiving of the message would
 * increase a time tick counter in it.
 *
 *
 * @author Antonin Komenda
 */
public class EventBasedStorage extends Storage implements EventHandler {

    private final EventBasedEnvironment environment;

    public EventBasedStorage(EventBasedEnvironment environment) {
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
