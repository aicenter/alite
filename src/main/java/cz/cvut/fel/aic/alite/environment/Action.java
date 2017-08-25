package cz.cvut.fel.aic.alite.environment;

import cz.cvut.fel.aic.alite.common.entity.Entity;
import cz.cvut.fel.aic.alite.environment.Environment.Handler;

/**
 * Actions define a write interface into an {@link Environment}.
 *
 * Entities/agents should not be able to access the environment and its
 * {@link Storage}s directly, they should only be able to instantiate sensors
 * and actions through an environmental {@link Handler} and than use them
 * to interact with the environment.
 *
 *
 * @author Antonin Komenda
 */
public abstract class Action {

    private final Environment environment;
    private final Entity relatedEntity;

    public Action(Environment environment, Entity relatedEntity) {
        this.environment = environment;
        this.relatedEntity = relatedEntity;
    }

    protected Environment getEnvironment() {
        return environment;
    }

    protected Entity getRelatedEntity() {
        return relatedEntity;
    }

}
