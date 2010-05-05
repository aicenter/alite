package cz.agents.alite.cross.environment.eventbased;

import cz.agents.alite.entity.Entity;
import cz.agents.alite.environment.Environment;
import cz.agents.alite.event.EventProcessor;


/**
 * EventBasedEnvironments provides additionally a event processor, which can
 * be used to describe successive the processes in the environment.
 *
 * The event processor is typically used in {@link EventBasedAction}s and
 * {@link EventBasedSensor}s of the environment.
 *
 *
 * @author Antonin Komenda
 */
public abstract class EventBasedEnvironment extends Environment {

    private final EventProcessor eventProcessor;
    private final EventBasedHandler handler;

    public EventBasedEnvironment(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;

        handler = new EventBasedHandler();
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    public EventBasedEnvironment.EventBasedHandler handler() {
        return handler;
    }

    public class EventBasedHandler extends Handler {

        protected EventBasedHandler() {
        }

        public <C extends EventBasedAction> C addAction(Class<C> clazz, Entity entity) {
            C instance = null;

            try {
                instance = clazz.getConstructor(EventBasedEnvironment.class, Entity.class)
                        .newInstance(EventBasedEnvironment.this, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return instance;
        }

        public <C extends EventBasedSensor> C addSensor(Class<C> clazz, Entity entity) {
            C instance = null;

            try {
                instance = clazz.getConstructor(EventBasedEnvironment.class, Entity.class)
                        .newInstance(EventBasedEnvironment.this, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return instance;
        }

    }

}
