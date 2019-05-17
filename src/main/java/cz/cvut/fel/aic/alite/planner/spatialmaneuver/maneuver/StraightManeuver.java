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
package cz.cvut.fel.aic.alite.planner.spatialmaneuver.maneuver;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import cz.cvut.fel.aic.alite.planner.spatialmaneuver.PathFindSpecification;


public class StraightManeuver extends Maneuver {

	private double length;

	public StraightManeuver(Point3d start, Vector3d direction, double time, double length,
			PathFindSpecification specification) {
		super(start, direction, time, specification);

		this.length = length;

		if (Math.abs(direction.z) < 1e-5) direction.z = 0;
	}

	public boolean isHorizontal() {
		return direction.z == 0.0;
	}

	@Override
	public double getLength() {
		return Math.abs(length);
	}

	public void setLength(double length) {
		this.length = length;
		invalidate();
	}

	public boolean isForward() {
		return length >= 0;
	}

	@Override
	public Point3d getEnd() {
		Point3d end = super.getEnd();
		if (end == null) {
			end = new Point3d(start);
			Vector3d dir =  new Vector3d(direction);
			dir.scale(length);
			end.add(dir);
			setEnd(end);
		}
		return end;
	}

	@Override
	public Vector3d getEndDirection() {
		Vector3d endDirection = super.getEndDirection();
		if (endDirection == null) {
			endDirection = new Vector3d(direction);
			setEndDirection(endDirection);
		}
		return endDirection;
	}

	@Override
	public boolean isIntersectingFullZone() {
		return specification.getZone().testLine(start, getEnd());
	}

	@Override
	public boolean isValid() {
		return !Double.isNaN(length);
	}

	@Override
	public void accept(ManeuverVisitor visitor) {
		visitor.visit(this);
	}

}
