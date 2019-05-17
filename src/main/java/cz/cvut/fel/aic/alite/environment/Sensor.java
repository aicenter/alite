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
package cz.cvut.fel.aic.alite.environment;

import cz.cvut.fel.aic.alite.common.entity.Entity;
import cz.cvut.fel.aic.alite.environment.Environment.Handler;

/**
 * Sensors define a read interface of an {@link Environment}.
 *
 * Entities/agents should not be able to access the environment and its
 * {@link Storage}s directly, they should only be able to instantiate sensors
 * and actions through an environmental {@link Handler} and than use them
 * to interact with the environment.
 *
 *
 * @author Antonin Komenda
 */
public abstract class Sensor {

	private final Environment environment;
	private final Entity relatedEntity;

	public Sensor(Environment environment, Entity relatedEntity) {
		this.environment = environment;
		this.relatedEntity = relatedEntity;
	}

	protected Environment getEnvironment() {
		return environment;
	}

	protected Entity getRelatedEntity() {
		return relatedEntity;
	}

}
