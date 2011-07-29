package cz.agents.alite.planner.spatialmaneuver.zone;

import javax.vecmath.Point3d;

public class EmptyZone implements Zone {

    @Override
    public boolean testLine(Point3d point1, Point3d point2, Point3d outPoint) {
        return false;
    }

    @Override
    public boolean testPoint(Point3d point) {
        return false;
    }

    @Override
    public void accept(ZoneVisitor visitor) {
        visitor.visit(this);
    }

}
