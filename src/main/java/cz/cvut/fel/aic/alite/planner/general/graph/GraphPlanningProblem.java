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
package cz.cvut.fel.aic.alite.planner.general.graph;

import org.jgrapht.graph.AbstractGraph;

import cz.cvut.fel.aic.alite.planner.general.Operator;
import cz.cvut.fel.aic.alite.planner.general.Problem;

public class GraphPlanningProblem<N, E, G extends AbstractGraph<N,E>> extends Problem {
	
	N startNode;
	N goalNode;
	G graph;
	public Heuristic<N> heuristic;
	
	public GraphPlanningProblem(N startNode, N goalNode, G graph, Heuristic<N> heuristic) {
		super();
		this.startNode = startNode;
		this.goalNode = goalNode;
		this.graph = graph;
		this.heuristic = heuristic;
	}

	@Override
	public Operator getStartingOperator() {
		return new StartOperator<N, E, G>(startNode, this);
	}
	
	public G getGraph() {
		return graph;
	}
	
	public double getHeuristicEstimate(N current) {
		return heuristic.getEstimate(current, goalNode);
	} 

}
