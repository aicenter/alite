/* 
 * Copyright (C) 2017 Czech Technical University in Prague.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point3d;

public class GroupZone implements Zone, Serializable {

    private static final long serialVersionUID = 970667587315494845L;

    private final LinkedList<Zone> subZones = new LinkedList<Zone>();

    @Deprecated
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

    @Override
    public boolean testLine(Point3d point1, Point3d point2) {
        for (Zone zone : subZones) {
            if (zone.testLine(point1, point2)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Point3d> findLineIntersections(Point3d point1, Point3d point2) {

        List<Point3d> intersections = new LinkedList<Point3d>();

        for (Zone zone : subZones) {
            intersections.addAll(zone.findLineIntersections(point1, point2));
        }

        return intersections;
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
