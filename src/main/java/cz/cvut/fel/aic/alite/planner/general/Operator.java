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
package cz.cvut.fel.aic.alite.planner.general;
/**
 * An operator that can be applied in a a given planning domain.
 * An object that implements interface Operator also represent the state
 * reached after the given operator instance is applied to the planning problem.
 * We call this state S.
 */

public interface Operator extends Comparable<Operator>{
    /** Actions (operator instances) applicable in state S, i.e. the state in which this operator was applied. **/
    public Operator[] getNeighbors();
    /** Determines if state S, i.e. the state in which this operator was applied, is the goal state. **/
    public boolean isGoal();


    /** Returns the cost of getting to state S, i.e. the state in which this operator was applied. In A*, this will be g. **/
    public double getCost();
    /** Returns the heuristic guess of getting from state S, i.e. the state in which this operator was applied top the goal state. In A*, this will be h. */
    public double getHeuristicEstimate();

    /** Sets the predecessor of this operator, so that the sequence can be recovered from the last operator.*/
    public void setPredecessor(Operator pred);
    public Operator getPredecessor();

}
