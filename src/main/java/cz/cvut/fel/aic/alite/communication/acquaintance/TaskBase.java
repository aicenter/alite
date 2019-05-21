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
import cz.cvut.fel.aic.alite.communication.protocol.cnp.CnpInitiator;

/**
 *  Interface for the Task Base definition
 *
 * @author Jiri Vokrinek
 */
public interface TaskBase {

	/**
	 * Invoke a new task processing.
	 * TaskBase takes care of the taks planning using registered PlanBases.
	 *
	 * @param task task to be planned
	 * @param taskListener completition task listener for task
	 * @throws cz.agents.alite.acquaintance.TaskBase.UnknownTaskTypeException
	 */
	void invokeTask(Task task, TaskListener taskListener, AllocationCallback allocationCallback) throws UnknownTaskTypeException;

	/**
	 * Cancels tasks. The task will be removed from respective PlanBase.
	 *
	 * @param task
	 * @return true if TaskBase contained the task
	 */
	boolean cancelTask(Task task, CnpInitiator.CancelCallback cancelCallback);

	/**
	 * Check for task existence.
	 * Note in case of decentralized TaskBases the task can be maintained by more than one TaskBase.
	 *
	 * @param task
	 * @return true if the task is maintained by this TaskBase, else otherwise.
	 */
	boolean containsTask(Task task);

	/**
	 * Registers PlanBase for the given task type.
	 * In case of decentralized TaskBases the TaskBase has to prapagate this capability to the others.
	 *
	 * @param taskType
	 * @param planner
	 */
	void registerType(String taskType, PlanBase planner);

	/**
	 * Check for capability to plan given taskType by a PlanBase registered in this TaskBase or other decentralized TaskBases.
	 *
	 * @param taskType
	 * @return true if the taskType can be planned
	 */
	boolean isTypeRegistered(String taskType);

	/**
	 * Generates new unique ID for a task.
	 *
	 * @return new unique task ID
	 */
	String generateNewTaskID();

	/**
	 * Exception for unknown task type
	 */
	public class UnknownTaskTypeException extends Exception {

		private static final long serialVersionUID = -344501137439336175L;

		public UnknownTaskTypeException(String string) {
			super(string);
		}
	}

	/**
	 * Interface for allocation callback
	 */
	public interface AllocationCallback {

		/**
		 * Called after successfull allocation of the task.
		 */
		void allocated();

		/**
		 * Called when allocation of the task is unsuccessfull.
		 */
		void failed();
	}
}
