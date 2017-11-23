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
 * A specification of the planning problem. In contrast to usual way of
 * specifying planning problems, the starting state and final state are not
 * specified explicitly here. Instead, a dummy operator yielding the starting 
 * state is introduced. Similarly, each operator instance is able to determine
 * if it leads to the goal state. 
 * 
 * The intermediate states are represented as the effects of individual operator instances.
 */
public abstract class Problem {	
	public abstract Operator getStartingOperator();
}
