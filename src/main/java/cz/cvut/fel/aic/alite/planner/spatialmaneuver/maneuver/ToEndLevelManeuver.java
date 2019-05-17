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


/**
 * @author Antonin Komenda
 *
 */
public class ToEndLevelManeuver extends Maneuver {

	private PitchToLevelManeuver endPitch = null;
	private StraightManeuver straight = null;
	private double endLevel;

	/**
	 * @param start
	 * @param direction
	 * @param zone
	 */
	public ToEndLevelManeuver(Point3d start, Vector3d direction, double time, PathFindSpecification specification) {
		super(start, direction, time, specification);

		Point3d end = specification.getTo();

		this.endLevel = end.z;

		if (   (start.z - endLevel > 0 && direction.z > 0)
			|| (start.z - endLevel < 0 && direction.z < 0)) {
			return;
		}

		double pitchRadius = specification.getPitchRadius();
		double pitchAngle = direction.angle(new Vector3d(direction.x, direction.y, 0.0));
		double straightZ = Math.abs(start.z - endLevel) - (pitchRadius - Math.cos(pitchAngle)*pitchRadius);
		double length = straightZ/direction.z;

		if (start.z - endLevel > 0) {
			length = -length;
		}

		if (length > 0) {
			straight = new StraightManeuver(start, direction, time, length, specification);
			endPitch = new PitchToLevelManeuver(straight.getEnd(), straight.getEndDirection(), straight.getEndTime(), specification);
		}
	}

	@Override
	public Point3d getEnd() {
		Point3d end = endPitch.getEnd();
		end.z = endLevel;
		return end;
	}

	@Override
	public Vector3d getEndDirection() {
		return endPitch.getEndDirection();
	}

	@Override
	public double getLength() {
		return straight.getLength() + endPitch.getLength();
	}

	public StraightManeuver getStraight() {
		return straight;
	}

	public PitchToLevelManeuver getEndPitch() {
		return endPitch;
	}

	@Override
	public void setPredecessor(Maneuver predecessor) {
		super.setPredecessor(predecessor);

		straight.setPredecessor(predecessor);
		predecessor = straight;
		endPitch.setPredecessor(predecessor);
		predecessor = endPitch;
	}

	@Override
	public boolean isIntersectingFullZone() {
		return straight.isIntersectingFullZone() || endPitch.isIntersectingFullZone();
	}

	@Override
	public boolean isValid() {
		if (straight != null && endPitch != null) {
			return straight.isValid() && endPitch.isValid();
		}
		return false;
	}

	@Override
	public void accept(ManeuverVisitor visitor) {
		visitor.visit(this);
	}

}
