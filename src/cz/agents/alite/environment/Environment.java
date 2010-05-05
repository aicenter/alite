package cz.agents.alite.environment;

import java.util.Random;

import cz.agents.alite.cross.environment.eventbased.EventBasedAction;
import cz.agents.alite.cross.environment.eventbased.EventBasedSensor;
import cz.agents.alite.entity.Entity;


/**
 * The Environment is a base class for all basic environments.
 *
 * The successors of the Environment class should aggregate and instantiate
 * several {@link Storage}s, which will hold the particular data structures
 * defining the environment and should also provide getters to access the
 * storages. The getters should not be included in the {@link Handler}, since
 * the entities/agents should not have an access to the storages directly.
 *
 * On the contrary, {@link Sensor}s and {@link Action}s created by the entities
 * through the handler will have the access to the getters of the storages by a
 * reference to the environment, so they can read and update the state of the
 * environment represented in the storages.
 *
 * Additionally, the Environment also provides a shared environmental random
 * generator, which should be used for any randomization in the logic of the
 * sensors and actions (if it it so, it is possible to create deterministically
 * reproducible runs of the system - the random seed can be set at one place).
 *
 *
 * @author Antonin Komenda
 */
public abstract class Environment {

    private final Handler handler;
    private Random random = new Random();

    public Environment() {
        handler = new Handler();
    }

    public Environment.Handler handler() {
        return handler;
    }

    public Random getRandom() {
        return random;
    }

    /**
     * The Environment.Handler provides an interface to the environment for the
     * entities (agents) behaving in the world.
     *
     * Since the entities are not typically allowed to access all the properties
     * of an environment, each environment provides its handler to enable the
     * entities only create an instances of actions and sensors.
     *
     * Additionally, the Handler also provides a shared environmental random
     * generator, which should be used for any randomization in the logic
     * of the entities/agents (if it it so, it is possible to create
     * deterministically reproducible runs of the system - the random seed
     * can be set at one place).
     *
     *
     * @author Antonin Komenda
     */
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
