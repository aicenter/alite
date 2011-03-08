package incubator.visprotocol.vis.data;

import javax.vecmath.Point3d;

public class PersonData  {

    public final String name;
    public final Point3d position;
    public final int health;

    public PersonData(String name, Point3d position, int health) {
        super();
        this.name = name;
        this.position = position;
        this.health = health;
    }

}
