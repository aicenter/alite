package cz.agents.alite.environment;

import java.util.Random;

import cz.agents.alite.cross.environment.eventbased.EventBasedAction;
import cz.agents.alite.cross.environment.eventbased.EventBasedSensor;
import cz.agents.alite.entity.Entity;

public class Environment {

    private final Handler handler;
    private Random random = new Random();

    public Environment() {
        handler = new Handler();
    }

    public Environment.Handler handler() {
        return handler;
    }

    public class Handler {

        protected Handler() {
        }

        public <C extends EventBasedAction> C addAction(Class<C> clazz, Entity entity) {
            C instance = null;

            try {
                instance = clazz.getConstructor(Environment.class, Entity.class)
                        .newInstance(Environment.this, entity);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return instance;
        }

        public <C extends EventBasedSensor> C addSensor(Class<C> clazz, Entity entity) {
            C instance = null;

            try {
                instance = clazz.getConstructor(Environment.class, Entity.class)
                        .newInstance(Environment.this, entity);
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
