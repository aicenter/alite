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

public interface ManeuverVisitor {

	public void visit(PitchManeuver maneuver);

	public void visit(PitchToLevelManeuver maneuver);

	public void visit(SmoothManeuver maneuver);

	public void visit(SpiralManeuver maneuver);

	public void visit(StartManeuver maneuver);

	public void visit(StraightManeuver maneuver);

	public void visit(ToEndLevelManeuver maneuver);

	public void visit(ToEndManeuver maneuver);

	public void visit(ToManeuver maneuver);

	public void visit(TurnManeuver maneuver);

	public void visit(TurnPitchManeuver maneuver);

}
