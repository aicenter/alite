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
package cz.cvut.fel.aic.alite.environment.eventbased;

import cz.cvut.fel.aic.alite.common.entity.Entity;
import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessor;
import cz.cvut.fel.aic.alite.environment.Sensor;

/**
 * The EventBasedSensors can use the event processor to postpone getting of the
 * sensory data.
 *
 * A sensor, which needs a time to process the data, can be modeled as
 * an event-based sensor. The sense() methods of the sensor would not return
 * directly the requested data, bud only adds an event into the event processor.
 * Later, the event would be received by the same instance of the sensor and
 * would invoke a sensory callback, which will asynchronously return the data
 * into the requesting entity/agent.
 *
 *
 * @author Antonin Komenda
 */
public abstract class EventBasedSensor extends Sensor implements EventHandler {

	private final EventBasedEnvironment environment;

	public EventBasedSensor(EventBasedEnvironment environment, Entity relatedEntity) {
		super(environment, relatedEntity);

		this.environment = environment;
	}

	protected EventBasedEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public EventProcessor getEventProcessor() {
		return environment.getEventProcessor();
	}

	@Override
	public void handleEvent(Event event) {
	}

}
