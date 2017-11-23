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

import cz.cvut.fel.aic.alite.planner.spatialmaneuver.PathFindSpecification;

public class StartManeuver extends StraightManeuver {

    private boolean isEnding = false;

    public StartManeuver(PathFindSpecification specification) {
        super(specification.getFrom(), specification.getFromDirection(), 0, 0, specification);

        setPredecessor(this);
        computeGh();

        if (specification.getTo().epsilonEquals(start, 1)
                && specification.getToDirection().epsilonEquals(direction, 1)) {
            isEnding = true;
        }
    }

    @Override
    public boolean isEnding() {
        return isEnding;
    }

    @Override
    public void accept(ManeuverVisitor visitor) {
        visitor.visit(this);
    }

}
