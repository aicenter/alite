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
package cz.cvut.fel.aic.alite.communication.protocol.cnp;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.protocol.cnp.CnpInitiator;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import java.util.Set;

/**
 *
 * @author Jiri Vokrinek
 */
public abstract class DirectoredCnpInitiator extends CnpInitiator {

	private final CapabilityRegister directory;

	/**
	 *
	 * @param communicator
	 * @param directory
	 * @param name
	 * @param contentData
	 * @param participantAddress
	 */
	public DirectoredCnpInitiator(Communicator communicator, CapabilityRegister directory, String name, Object contentData, Set<String> participantAddress) {
		super(communicator, name, contentData, participantAddress);
		this.directory = directory;
	}

	/**
	 *
	 * @param communicator
	 * @param directory
	 * @param name	  Custom protocol name to differentiate several CNPs
	 * @param contentData
	 */
	public DirectoredCnpInitiator(Communicator communicator, CapabilityRegister directory, String name, Object contentData) {
		this(communicator, directory, name, contentData, directory.getIdentities(buildName(name)));
	}

	/**
	 *
	 * @param name
	 * @return Set of addresses registered to serviceType that corresponds to protocol name
	 */
	public Set<String> getCorrespondingAddresses(String name) {
		return directory.getIdentities(buildName(name));
	}

	public static String buildName(String name) {
		return CnpInitiator.buildName(name);
	}
}
