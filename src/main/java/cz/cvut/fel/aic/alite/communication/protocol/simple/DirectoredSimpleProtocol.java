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
package cz.cvut.fel.aic.alite.communication.protocol.simple;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.protocol.simple.SimpleProtocol;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.communication.directory.DirectoryFacilitatorSingleton;
import java.util.Set;

/**
 *
 * @author Jiri Vokrinek
 */
public abstract class DirectoredSimpleProtocol extends SimpleProtocol {

	private final CapabilityRegister directory;

	/**
	 * Default constructor. It registers this protocol in the {@link DirectoryFacilitatorSingleton}
	 *
	 * @param communicator
	 * @param directory
	 * @param name
	 */
	public DirectoredSimpleProtocol(Communicator communicator, CapabilityRegister directory, String name) {
		super(communicator, name);
		this.directory = directory;
		directory.register(communicator.getAddress(), getName());
	}

	/**
	 * Searches the {@link DirectoryFacilitatorSingleton} for the agents that instantiated the same
	 * {@link DirectoredSimpleProtocol} name.
	 *
	 * @return set of agents addresses
	 */
	public Set<String> getNeighbourAdressess() {
		return directory.getIdentities(getName());
	}
}
