package cz.agents.alite.entity;

/**
 * An Entity is a named object.
 *
 *
 * @author Antonin Komenda
 *
 */
public class Entity {

    private final String name;

    public Entity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
