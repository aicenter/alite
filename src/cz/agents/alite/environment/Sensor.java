package cz.agents.alite.environment;

import cz.agents.alite.entity.Entity;

public class Sensor {

    private final Environment environment;
    private final Entity relatedEntity;

    public Sensor(Environment environment, Entity relatedEntity) {
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
