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
import java.util.HashMap;
import java.util.List;

/**
 *  Default PlanBase for testing and basic operations.
 *  The plan cost is equal to number of assigned tasks.
 *  PlanCost of any partial plan (for a task) is equal to 1.
 *  This PlanBase can be used for balancing the number of tasks between agents
 *  in case the cost of any task execution is equal and constant.
 *
 *  The attached executor has to accept the Plan in the form of MyPlan extends LinkedList<Task> defind in this class.
 *
 * @author Jiri Vokrinek
 */
public class DefaultPlanBase implements PlanBase {

	private final DefaultPlan tasks = new DefaultPlan();
	private final HashMap<Task, TaskListener> listeners = new HashMap<Task, TaskListener>();
	private PlanExecutor executor;

	@Override
	public PlanCost evaluateInsertion(Task task) {
		return new DefaultPlanCost(tasks.size());
	}

	@Override
	public PlanCost evaluateRemoval(Task task) {
		return new DefaultPlanCost(tasks.size() - 1);
	}

	@Override
	public PlanCost insertTask(Task task, TaskListener taskListener) {
		tasks.add(task);
		listeners.put(task, taskListener);
		executor.executePlan(tasks);
		return new DefaultPlanCost(tasks.size() - 1);
	}

	@Override
	public PlanCost removeTask(Task task) {
		tasks.remove(task);
		listeners.remove(task);
		executor.executePlan(tasks);
		return new DefaultPlanCost(tasks.size());
	}

	@Override
	public PlanCost getTotalCost() {
		return new DefaultPlanCost(tasks.size());
	}

	@Override
	public void registerExecutor(PlanExecutor executor) {
		this.executor = executor;
		executor.registerExecutorFeedback(new MyExecutorFeedback(this));
	}
	
	protected DefaultPlan getTasks() {
		return tasks;
	}

	void taskDone(Task task) {
		tasks.remove(task);
		listeners.remove(task).taskCompleted();
	}

	void taskFailed(Task task) {
		tasks.remove(task);
		listeners.remove(task).taskUnreachable();
	}

	@SuppressWarnings("unchecked")
	void planFailed() {
		List<Task> oldPlan = (List<Task>) tasks.clone();
		tasks.clear();
		for (Task task : oldPlan) {
			listeners.remove(task).taskUnreachable();
		}
	}

	/**
	 * Execution feedback for DefaultPlanBase
	 */
	public class MyExecutorFeedback implements PlanExecutor.ExecutorFeedback {

		private final DefaultPlanBase pb;

		/**
		 *
		 * @param pb
		 */
		public MyExecutorFeedback(DefaultPlanBase pb) {
			this.pb = pb;
		}

		@Override
		public void done(Task task) {
			pb.taskDone(task);
		}

		@Override
		public void failed(Task task) {
			pb.taskFailed(task);
		}

		@Override
		public void planUnreachable() {
			pb.planFailed();
		}
	}
}

