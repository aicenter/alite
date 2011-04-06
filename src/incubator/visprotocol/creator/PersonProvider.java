package incubator.visprotocol.creator;

import javax.vecmath.Point3d;

public interface PersonProvider {

    public String getPersonName();

    public Point3d getPersonPosition();

    public int getPersonHealth();

}
