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

public enum ExpandManeuverType {

	STRAIGHT("Straight"),
	STRAIGHT_BACKWARDS("StraightBackwards"),
	TURN_RIGHT("TurnRight"),
	TURN_LEFT("TurnLeft"),
	PITCH_UP("PitchUp"),
	PITCH_DOWN("PitchDown"),
	SPIRAL("Spiral"),
	TURN_TO_END("TurnToEnd"),
	TURN_PITCH_TO_END("TurnPitchToEnd"),
	PITCH_TO_LEVEL("PitchToLevel"),
	TO_END_LEVEL("ToEndLevel"),
	TO_END("ToEnd");
	private final String value;

	ExpandManeuverType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static ExpandManeuverType fromValue(String v) {
		for (ExpandManeuverType c: ExpandManeuverType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
