package cz.agents.alite.planner.spatialmaneuver.zone;

import javax.vecmath.Point3d;

/**
 * @author Antonin Komenda
 *
 */
public interface Zone {

    public boolean testPoint(final Point3d point);

    public boolean testLine(final Point3d point1, final Point3d point2, final Point3d outPoint);

    public void accept(ZoneVisitor visitor);

}
