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
package cz.cvut.fel.aic.alite.simulation;

import cz.cvut.fel.aic.alite.common.entity.Entity;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.environment.Action;
import cz.cvut.fel.aic.alite.environment.Environment;
import cz.cvut.fel.aic.alite.environment.Sensor;
import cz.cvut.fel.aic.alite.environment.Storage;

/**
 * Simulation entity represents an object directly interacting in
 * the {@link Simulation}.
 *
 * Also, it is an default implementation of the {@link EventHandler} interface usable
 * together with the {@link Simulation}.
 *
 * Simulation entities can be aggregated in a environment {@link Storage}, each
 * representing one element behaving in the scope of the storage (e.g. a traffic light in
 * a traffic light storage).
 *
 * Note: An agents is not an extension of a {@link SimulationEntity}, since
 * the agent do not represent the physical body. The agent is only a "brain"
 * controlling its body through the {@link Sensor}s and {@link Action}s of an
 * {@link Environment}.
 *
 *
 * @author Antonin Komenda
 */
public abstract class SimulationEntity extends Entity implements EventHandler {

	private Simulation simulation;

	public SimulationEntity(String name, Simulation simulation) {
		super(name);

		this.simulation = simulation;
	}

	@Override
	public Simulation getEventProcessor() {
		return simulation;
	}

}
