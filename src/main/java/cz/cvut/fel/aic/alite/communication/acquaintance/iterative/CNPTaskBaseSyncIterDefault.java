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
package cz.cvut.fel.aic.alite.communication.acquaintance.iterative;

import cz.cvut.fel.aic.alite.communication.acquaintance.PlanCost;
import cz.cvut.fel.aic.alite.communication.acquaintance.Task;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.communication.Communicator;

/**
 *
 * @author Jiri Vokrinek
 */
public class CNPTaskBaseSyncIterDefault extends CNPTaskBaseSyncIter{

	public CNPTaskBaseSyncIterDefault(Communicator communicator, CapabilityRegister directory, String name) {
		super(communicator, directory, name);
	}

	public CNPTaskBaseSyncIterDefault(Communicator communicator, CapabilityRegister directory) {
		super(communicator, directory);
	}

	@Override
	void tryToImprove(Task task, PlanCost estR) {
//		 System.out.println(this.toString()+" Improving "+task+" with cost "+estR.toString());
		invokeImprovement(false);
	}

}
