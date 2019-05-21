/* 
 * Copyright (C) 2019 Czech Technical University in Prague.
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


public class SpiralManeuver extends Maneuver {

	private int loops;

	private double loopHeight;

	public SpiralManeuver(Point3d start, Vector3d direction, double time, int loops,
			PathFindSpecification specification) {
		super(start, direction, time, specification);
		this.loops = loops;
	}

	@Override
	public double getLength() {
		double radius = specification.getRadius();
		double pitchAngle = direction.angle(new Vector3d(direction.x, direction.y, 0.0));
		return loops*2.0*Math.PI*radius/Math.cos(pitchAngle);
	}

	@Override
	public Point3d getEnd() {
		Point3d end = super.getEnd();
		if (end == null) {
			double radius = specification.getRadius();

			end = new Point3d(start);
			double pitchAngle = direction.angle(new Vector3d(direction.x, direction.y, 0.0));
			if (direction.z < 0) {
				pitchAngle = -pitchAngle;
			}

			loopHeight = 2.0*Math.PI*radius*Math.tan(pitchAngle);
			end.z += loops*loopHeight;

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
		double radius = specification.getRadius();
		getEnd();

		double alphaShift = Math.atan2(direction.y, direction.x) + Math.PI/2;
		Vector3d centerShift = new Vector3d(radius*direction.y, -radius*direction.x, 0);
		Point3d prev = new Point3d(start);
		Point3d actual = new Point3d();

		for (int loop = 0; loop < loops; ++loop) {
			for (double alpha = loop*2*Math.PI; alpha <= (loop+1)*2*Math.PI+1e-5; alpha += Math.PI/4) {
				double alphaRotated = -alpha + alphaShift;
				actual.set(radius*Math.cos(alphaRotated), radius*Math.sin(alphaRotated),
						loopHeight*alpha/Math.PI/2);
				actual.add(start);
				actual.add(centerShift);
				if (specification.getZone().testLine(prev, actual)) {
					return true;
				}
				prev.set(actual);
			}
		}

		return false;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public void accept(ManeuverVisitor visitor) {
		visitor.visit(this);
	}

}
