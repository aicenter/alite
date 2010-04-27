package spaceplan.zone;

import java.util.LinkedList;

import javax.vecmath.Point3d;

public class GroupZone implements Zone {

    private static final long serialVersionUID = 970667587315494845L;

    private final LinkedList<Zone> subZones = new LinkedList<Zone>();

    @Override
    public boolean testLine(Point3d point1, Point3d point2, Point3d outPoint) {
        for (Zone zone : subZones) {
            if (zone.testLine(point1, point2, outPoint)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean testPoint(Point3d point) {
        for (Zone zone : subZones) {
            if (zone.testPoint(point)) {
                return true;
            }
        }

        return false;
    }

    public boolean add(Zone zone) {
        return subZones.add(zone);
    }

    public LinkedList<Zone> getSubZones() {
        return subZones;
    }

    @Override
    public void accept(ZoneVisitor visitor) {
        visitor.visit(this);
    }

}
