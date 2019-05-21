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

/**
 * PlanExecutor interface.
 * It represents the entity that realizes the plans.
 *
 * @author Jiri Vokrinek
 */
public interface PlanExecutor {

	/**
	 * Binds the executor to the specific feedback (usually implemented by PlanBase).
	 * @param feedback
	 */
	public void registerExecutorFeedback(ExecutorFeedback feedback);

	/**
	 * Executes given plan.
	 *
	 * @param plan
	 */
	public void executePlan(Plan plan);

	/**
	 * Extends actual plan by given plan.
	 *
	 * @param plan
	 */
	public void addPlan(Plan plan);

	/**
	 * Invalidates curen plan and stops the execution.
	 */
	public void scratchPlan();

	/**
	 * The encapsulating interface for feedback from executor to PlanBase.
	 * Enrich the API of your implementation for capturing the specifics of your PlanExecutor to PlanBase interactions.
	 */
	public interface ExecutorFeedback {

		/**
		 * Call when Task execution is done.
		 *
		 * @param task
		 */
		public void done(Task task);

		/**
		 * Call when Task execution fails.
		 *
		 * @param task
		 */
		public void failed(Task task);

		/**
		 * Call when Plan cannot be executed.
		 *
		 */
		public void planUnreachable();
	}
}
