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
package cz.cvut.fel.aic.alite.common.event;

/**
 * This class is adapter meant to reduce usesless declerations of obsolete code. It implements
 * method {@link EventHandler#getEventProcessor()} which is obsolete and will be removed in Alite 2.
 * It is designed as temporary solution for Alite 1.
 *
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public abstract class EventHandlerAdapter implements EventHandler {

	/**
	 * This method is obsolete and is not even used. It returns null now.
	 * @return null pointer
	 */
	@Override
	public EventProcessor getEventProcessor() {
		return null;
	}
}
