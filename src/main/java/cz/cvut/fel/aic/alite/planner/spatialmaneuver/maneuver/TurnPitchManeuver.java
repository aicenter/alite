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

import cz.cvut.fel.aic.alite.planner.spatialmaneuver.PathFindSpecification;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


/**
 * @author Antonin Komenda
 *
 */
public class TurnPitchManeuver extends Maneuver {

	private TurnManeuver turn;
	private PitchManeuver pitch;

	/**
	 * @param start
	 * @param direction
	 * @param zone
	 */
	public TurnPitchManeuver(TurnManeuver turn, PitchManeuver pitch, PathFindSpecification specification) {
		super(turn.start, turn.direction, turn.time, specification);

		this.turn = turn;
		this.pitch = pitch;
	}

	@Override
	public Point3d getEnd() {
		return pitch.getEnd();
	}

	@Override
	public Vector3d getEndDirection() {
		return pitch.getEndDirection();
	}

	@Override
	public double getLength() {
		return turn.getLength() + pitch.getLength();
	}

	public TurnManeuver getTurn() {
		return turn;
	}

	public PitchManeuver getPitch() {
		return pitch;
	}

	@Override
	public void setPredecessor(Maneuver predecessor) {
		super.setPredecessor(predecessor);

		turn.setPredecessor(predecessor);
		predecessor = turn;
		pitch.setPredecessor(predecessor);
		predecessor = pitch;
	}

	@Override
	public boolean isIntersectingFullZone() {
		return turn.isIntersectingFullZone() || pitch.isIntersectingFullZone();
	}

	@Override
	public boolean isValid() {
		return turn.isValid() && pitch.isValid();
	}

	@Override
	public void accept(ManeuverVisitor visitor) {
		visitor.visit(this);
	}

}
