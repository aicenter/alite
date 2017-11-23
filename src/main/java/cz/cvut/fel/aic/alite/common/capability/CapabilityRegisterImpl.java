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
package cz.cvut.fel.aic.alite.common.capability;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Provides basic implementation of @CapabilityRegister using @HashMap
 * 
 * @author Jiri Vokrinek
 */
public class CapabilityRegisterImpl implements CapabilityRegister {

    private final HashMap<String, Set<String>> register = new HashMap<String, Set<String>>();

    public void register(String identity, String capabilityName) {
        Set<String> recS = register.get(capabilityName);
        if (recS == null) {
            LinkedHashSet<String> page = new LinkedHashSet<String>();
            page.add(identity);
            register.put(capabilityName, page);
        } else {
            recS.add(identity);
        }
    }

    public Set<String> getIdentities(String capabilityName) {
        Set<String> get = register.get(capabilityName);
        if (get == null) {
            return new LinkedHashSet<String>();
        }
        return new LinkedHashSet<String>(get);

    }

    public Set<String> getIdentities() {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (Set<String> page : register.values()) {
            result.addAll(page);
        }
        return result;
    }
}
