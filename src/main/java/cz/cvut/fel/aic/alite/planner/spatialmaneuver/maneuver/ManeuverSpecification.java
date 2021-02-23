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

import cz.cvut.fel.aic.alite.planner.spatialmaneuver.zone.GroupZone;
import java.io.Serializable;
import javax.vecmath.Point3d;



/**
 * Parameters of maneuvers.
 *
 * @author Antonin Komenda
 *
 */
public class ManeuverSpecification implements Serializable {

	private static final long serialVersionUID = -5410255302427891330L;

	private final LevelConstants levelConstants[];

	private final ExpandManeuvers expandManeuvers;

	private final GroupZone levelZone;

	public ManeuverSpecification(GroupZone levelZone, LevelConstants[] levelConstants, ExpandManeuvers expandManeuvers) {
		super();

		this.levelZone = levelZone;
		this.levelConstants = levelConstants;
		this.expandManeuvers = expandManeuvers;
	}

	public LevelConstants getLevelConstants(Point3d point) {
		if (levelZone != null) {
			boolean test = levelZone.testPoint(point);
			return levelConstants[test? 1:0];
		} else {
			return levelConstants[0];
		}
	}

	public ExpandManeuvers getExpandManeuvers() {
		return expandManeuvers;
	}

	/**
	 * Maneuver constants for one level of zone.
	 *
	 * @author Antonin Komenda
	 */
	public static class LevelConstants implements Serializable {

		private static final long serialVersionUID = -3789624766149541991L;

		public double straightLength;
		public int turnSteps;
		public double epsilonEqualsPosition;
		public double epsilonEqualsDirection;
		public int hashBitSize;

		public LevelConstants() {
			super();
		}

		public LevelConstants(double straightLength, int turnSteps, int hashBitSize) {
			this(straightLength, turnSteps, straightLength - 0.01, turnSteps + 0.01, hashBitSize);
		}

		/**
		 *
		 * @param straightLength double length
		 * @param turnSteps int (turn angle = PI*2 / turnSteps)
		 * @param epsilonEqualsPosition double length
		 * @param epsilonEqualsDirection double angle in 2*Math.PI/epsilonEqualsDirection (similar to turn steps)
		 * @param hashBitSize int bit count
		 */
		public LevelConstants(double straightLength, int turnSteps, double epsilonEqualsPosition,
				double epsilonEqualsDirection, int hashBitSize) {
			this.straightLength = straightLength;
			this.turnSteps = turnSteps;
			this.epsilonEqualsPosition = epsilonEqualsPosition*epsilonEqualsPosition;
			this.epsilonEqualsDirection = 2.0*(1 - Math.cos(2.0*Math.PI/epsilonEqualsDirection));
			this.hashBitSize = hashBitSize;
		}

	}

}

