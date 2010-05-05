package cz.agents.alite.environment;

import cz.agents.alite.entity.Entity;

public class Action {

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
