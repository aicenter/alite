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
package cz.cvut.fel.aic.alite.communication.directory;

import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegisterImpl;
import java.util.HashMap;
import java.util.Map;

/**
 * Multiple directories singleton implemented using @HashMap of @CapabilityRegisterImpl 
 * and unique instance identification as keys.
 * 
 * @author Jiri Vokrinek
 */
public class DirectoryFacilitatorMultipleton extends CapabilityRegisterImpl {


	private static final Map<String, CapabilityRegisterImpl> instances = new HashMap<String, CapabilityRegisterImpl>();

	static public CapabilityRegister getInstance(String id) {
		if (!instances.containsKey(id)) {
			instances.put(id, new CapabilityRegisterImpl());
		}
		return instances.get(id);
	}
}
