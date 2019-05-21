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
package cz.cvut.fel.aic.alite.planner.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Path planner using the A* algorithm
 */
public class Planner {

	private Planner() {
	}

	/**
	 * Finds path between two points using A* algorithm.
	 *
	 * @param problem specification of the planning problem
	 * @return last operator in the resulting sequence (found path)
	 */
	public static Operator findPath(Problem specification) {
		//specification.check();
		return findPathProcess(specification);
	}

	private static Operator findPathProcess(Problem problem) {
		Operator neighbors[];
		HashMap<Operator, Operator> nodeCache = new HashMap<Operator, Operator>();
		PriorityQueue<Operator> open = new PriorityQueue<Operator>();
		HashSet<Operator> close = new HashSet<Operator>();
		Operator current, operatorCached;

		current = problem.getStartingOperator();
		open.add(current);

		while(!open.isEmpty()) {
			current = open.poll();
			close.add(current);

			if (current.isGoal()) {
				break;
			}

			// prepare all neighbours for expansion
			neighbors = current.getNeighbors();

			for (Operator neighbor : neighbors) {
				if (neighbor == null) {
					// all neighbors processed
					break;
				}
				if (close.contains(neighbor)) {
					// skip node
					continue;
				}

				if ((operatorCached = nodeCache.get(neighbor)) != null) {
					// check if current is better than old
					if (neighbor.compareTo(operatorCached) <= 0) {
						// skip
						continue;
					}
					// replace it
					open.remove(neighbor);
				}

				open.add(neighbor);
				nodeCache.put(neighbor, neighbor);
			}
		}

		if (!current.isGoal()) {
			throw new RuntimeException("PathNotFoundException");
		}

		return current;
	}
}
