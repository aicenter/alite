package incubator.visprotocol.creator;

import org.apache.commons.math.geometry.Vector3D;

public interface PersonProvider {

    public String getPersonName();

    public Vector3D getPersonPosition();

    public int getPersonHealth();

}
