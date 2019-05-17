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
package cz.cvut.fel.aic.alite.communication.protocol;

import cz.cvut.fel.aic.alite.communication.Communicator;

/**
 * Default protocol implementation providing basic methods.
 *
 * @author Jiri Vokrinek
 */
public abstract class DefaultProtocol implements Protocol {

	private final String name;
	/**
	 * The {@link Communicator} used by the protocol.
	 */
	protected final Communicator communicator;

	/**
	 *
	 * @param communicator
	 * @param name
	 */
	public DefaultProtocol(Communicator communicator, String name) {
		this.communicator = communicator;
		this.name = name;
	}


	public String getName() {
		return name;
	}

	/**
	 * Comparator for protocols. The protocol is equal if
	 * {@code this.getName().equals(protocol.getName())}
	 *
	 * @param protocol
	 * @return true if protocols are equal
	 */
	public boolean equals(Protocol protocol) {
		if (protocol == null) {
			return false;
		}
		if (getName().equals(((Protocol) protocol).getName())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Session ID generator. It is generated as
	 * {@code "" + this.hashCode()}
	 *
	 * @return unique session ID
	 */
	protected String generateSession() {
		//todo: is this correct?
		return "" + this.hashCode();
	}
}
