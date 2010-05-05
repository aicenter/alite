package cz.agents.alite.environment.eventbased;

import cz.agents.alite.entity.Entity;
import cz.agents.alite.environment.Sensor;
import cz.agents.alite.event.Event;
import cz.agents.alite.event.EventHandler;
import cz.agents.alite.event.EventProcessor;

public abstract class EventBasedSensor extends Sensor implements EventHandler {

    private final EventBasedEnvironment environment;
    private final Entity relatedEntity;

    public EventBasedSensor(EventBasedEnvironment environment, Entity relatedEntity) {
        this.environment = environment;
        this.relatedEntity = relatedEntity;
    }

    protected EventBasedEnvironment getEnvironment() {
        return environment;
    }

    protected Entity getRelatedEntity() {
        return relatedEntity;
    }

    @Override
    public EventProcessor getEventProcessor() {
        return environment.getEventProcessor();
    }

    @Override
    public void handleEvent(Event event) {
    }

}
