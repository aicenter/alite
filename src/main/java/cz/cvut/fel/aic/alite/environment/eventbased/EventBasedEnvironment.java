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
import cz.cvut.fel.aic.alite.common.event.EventProcessor;
import cz.cvut.fel.aic.alite.environment.Action;
import cz.cvut.fel.aic.alite.environment.Environment;
import cz.cvut.fel.aic.alite.environment.Sensor;


/**
 * EventBasedEnvironments provides additionally a event processor, which can
 * be used to describe successive the processes in the environment.
 *
 * The event processor is typically used in {@link EventBasedAction}s and
 * {@link EventBasedSensor}s of the environment.
 *
 *
 * @author Antonin Komenda
 */
public abstract class EventBasedEnvironment extends Environment {

	private final EventProcessor eventProcessor;
	private final EventBasedHandler handler;

	public EventBasedEnvironment(EventProcessor eventProcessor) {
		this.eventProcessor = eventProcessor;

		handler = new EventBasedHandler();
	}

	public EventProcessor getEventProcessor() {
		return eventProcessor;
	}

	public EventBasedEnvironment.EventBasedHandler handler() {
		return handler;
	}

	public class EventBasedHandler extends Handler {

		protected EventBasedHandler() {
		}

		@Override
		public <C extends Action> C addAction(Class<C> clazz, Entity entity) {
			return instantiateEnvironmentClass(clazz, entity, new Class<?>[] {EventBasedEnvironment.class, Entity.class});
		}

		@Override
		public <C extends Sensor> C addSensor(Class<C> clazz, Entity entity) {
			return instantiateEnvironmentClass(clazz, entity, new Class<?>[] {EventBasedEnvironment.class, Entity.class});
		}

	}

}
