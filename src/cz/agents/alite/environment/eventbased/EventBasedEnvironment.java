package cz.agents.alite.environment.eventbased;

import java.util.Random;

import cz.agents.alite.entity.Entity;
import cz.agents.alite.environment.Environment;
import cz.agents.alite.event.EventProcessor;


public class EventBasedEnvironment extends Environment {

    private final EventProcessor eventProcessor;
    private final Handler handler;
    private Random random = new Random();

    public EventBasedEnvironment(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;

        handler = new Handler();
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    public EventBasedEnvironment.Handler handler() {
        return handler;
    }

    public class Handler {

        protected Handler() {
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

        public Random getRandom() {
            return random;
        }
    }

}
