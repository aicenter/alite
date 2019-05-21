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
package cz.cvut.fel.aic.alite.communication.acquaintance;

import cz.cvut.fel.aic.alite.communication.acquaintance.Task.TaskListener;

/**
 *  Interface for the Plan Base definition
 *  The Plan Base is persistent entity able to maintain plans for the tasks.
 *
 * @author Jiri Vokrinek
 */
public interface PlanBase {

	/**
	 * Evaluates potential task insertion.
	 *
	 * @param task
	 * @return the cost increase of the new potential plan, null if plan not found
	 */
	PlanCost evaluateInsertion(Task task);

	/**
	 * Evaluates potential task removal.
	 * The cost of the potential task removal cannot be lower than
	 * cost of subsequent insertion of the same task.
	 *
	 * @param task
	 * @return the cost equivalent of savings when the task will be removed, null if task cannod be removed (e.g. is under execution)
	 */
	PlanCost evaluateRemoval(Task task);

	/**
	 * Adds the task to the PlanBase.
	 * The plan is created and the respective plan cost increase is computed.
	 *
	 * @param task
	 * @param taskListener callback for task execution feedback
	 * @return total plan cost increase, null if plan not found
	 */
	PlanCost insertTask(Task task, TaskListener taskListener);

	/**
	 * Remove the task from the PlanBase.
	 * The plan is updated and respective plan cost savings is computed.
	 *
	 * @param task
	 * @return total plan cost decrease.
	 */
	PlanCost removeTask(Task task);

	/**
	 * Computes the total cost of overall plan to fullfill all the inserted tasks.
	 *
	 * @return PlanCost of the total plan.
	 */
	PlanCost getTotalCost();

	/**
	 * Registeres the PlanExecutor to this PlanBase.
	 * PlanBase is responsible for creating the appropriate ExecutorFeedback and registration using PlanExecutor.registerExecutorFeedback
	 *
	 * @param executor
	 */
	public void registerExecutor(PlanExecutor executor);

	/**
	 * Exception for planning failure
	 */
	class TaskPlanError extends Exception {

		private static final long serialVersionUID = 416794555267004753L;

		TaskPlanError(String string) {
			super(string);
		}
	}
}
