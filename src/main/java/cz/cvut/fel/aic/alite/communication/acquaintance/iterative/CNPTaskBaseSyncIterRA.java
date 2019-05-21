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
package cz.cvut.fel.aic.alite.communication.acquaintance.iterative;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.acquaintance.CNPTaskBase;
import cz.cvut.fel.aic.alite.communication.acquaintance.PlanCost;
import cz.cvut.fel.aic.alite.communication.acquaintance.Task;
import cz.cvut.fel.aic.alite.communication.acquaintance.Task.TaskListener;
import cz.cvut.fel.aic.alite.communication.protocol.cnp.CnpInitiator;
import cz.cvut.fel.aic.alite.communication.protocol.cnp.CnpInitiator.CancelCallback;

/**
 *  ReallocateAll dynamic improvement strategy for {@link CNPTaskBaseSyncIter}.
 *
 * @author Jiri Vokrinek
 */
public class CNPTaskBaseSyncIterRA extends CNPTaskBaseSyncIter {

	public CNPTaskBaseSyncIterRA(Communicator communicator, CapabilityRegister directory, String name) {
		super(communicator, directory, name);
	}

	public CNPTaskBaseSyncIterRA(Communicator communicator, CapabilityRegister directory) {
		super(communicator, directory);
	}

	@Override
	void tryToImprove(final Task task, final PlanCost estR) {

		final CnpInitiator cnp = tasksOwned.remove(task);
		final String lastResource = cnp.getWinner();
		final TaskListener taskListener = taskListeners.remove(task);


		cnp.cancel(new CancelCallback() {

			public void cancelConfirmed() {
				invokeTaskDirect(task, taskListener, new AllocationCallback() {

					boolean improved = false;

					public void allocated() {
						if (estR.isLower((PlanCost) tasksOwned.get(task).getWinnerResponse())) {
							Logger.getLogger(CNPTaskBase.class.getName()).log(Level.ERROR, "REALLOCATION");
						}
						if (!lastResource.equals(tasksOwned.get(task).getWinner())) {
							//improved by delegation
							improved = true;
						} else if (((PlanCost) tasksOwned.get(task).getWinnerResponse()).isLower(estR)) {
							//improved by replanning
							improved = true;
						}
						invokeImprovement(improved);
					}

					public void failed() {
						taskListener.taskUnreachable();
						invokeImprovement(true);
					}
				});
			}
		});
	}

	@Override
	protected void invokeTaskDirect(Task task, TaskListener taskListener, AllocationCallback allocationCallback) {
		super.invokeTaskDirect(task, taskListener, allocationCallback);
	}
}
