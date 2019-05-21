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
package cz.cvut.fel.aic.alite.communication.protocol.request;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.protocol.DefaultProtocol;

/**
 * Request-inform protocol wrapper.
 *
 * @author Jiri Vokrinek
 */
public class RequestInform extends DefaultProtocol {

	static final String REQUEST_INFORM_PROTOCOL_NAME = "REQUEST_INFORM_PROTOCOL";

	/**
	 * 
	 * @param communicator
	 * @param name
	 */
	public RequestInform(Communicator communicator, String name) {
		super(communicator, REQUEST_INFORM_PROTOCOL_NAME + ": " + name);
	}
}
