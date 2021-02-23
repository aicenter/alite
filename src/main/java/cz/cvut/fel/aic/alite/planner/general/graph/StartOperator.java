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
package cz.cvut.fel.aic.alite.planner.general.graph;

import cz.cvut.fel.aic.alite.planner.general.Operator;
import java.util.Set;
import org.jgrapht.graph.AbstractGraph;

public class StartOperator<N, E, G extends AbstractGraph<N, E>> implements Operator {

	protected N node;

	protected G graph;
	protected GraphPlanningProblem<N, E, G> problem;

	boolean hcomputed = false;
	double h;

	public StartOperator(N node, GraphPlanningProblem<N, E, G> graphPlanningProblem) {
		this.node = node;
		this.problem = graphPlanningProblem;
		this.graph = problem.getGraph();
	}

	@Override
	public double getCost() {
		return 0.0;
	}

	@Override
	public double getHeuristicEstimate() {
		if (! hcomputed) {
			// compute h
			h = problem.getHeuristicEstimate(node);
			hcomputed = true;
		}

		return h;
	}

	@Override
	public Operator[] getNeighbors() {
		Set<E>edges = graph.edgesOf(node);
		Operator[] neighbors = new Operator[edges.size()];
		int i = 0;
		for (E edge : edges) {
			neighbors[i] = new EdgeMoveOperator<N, E, G>(node, edge, problem);
			neighbors[i].setPredecessor(this);
			i++;
		}

		return neighbors;
	}

	@Override
	public Operator getPredecessor() {
		return null;
	}

	@Override
	public boolean isGoal() {
		return node.equals(problem.goalNode);
	}

	@Override
	public void setPredecessor(Operator pred) {
		throw new UnsupportedOperationException("Start operator cannot have a predecessor.");
	}

	@Override
	public int compareTo(Operator o) {
		return (int)Math.round(Math.signum((getCost()+getHeuristicEstimate()) - (o.getCost()+o.getHeuristicEstimate())));
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		if (o instanceof StartOperator) {
			return node.equals(((StartOperator)node).node);
		}
		return false;
	}
}
