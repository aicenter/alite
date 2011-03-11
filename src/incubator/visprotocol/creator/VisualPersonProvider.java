package incubator.visprotocol.creator;

import javax.vecmath.Point3d;

public interface VisualPersonProvider {

    public String getPersonName();

    public Point3d getPersonPosition();

    public int getPersonHealth();

}
