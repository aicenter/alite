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

import java.util.LinkedList;
import java.util.List;

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
	public boolean testLine(Point3d point1, Point3d point2) {
		return false;
	}

	@Override
	public List<Point3d> findLineIntersections(Point3d point1, Point3d point2) {
		return new LinkedList<Point3d>();
	}

    @Override
    public void accept(ZoneVisitor visitor) {
        visitor.visit(this);
    }

}
