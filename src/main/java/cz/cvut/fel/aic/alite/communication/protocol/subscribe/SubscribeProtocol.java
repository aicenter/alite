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
package cz.cvut.fel.aic.alite.communication.protocol.subscribe;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.protocol.DefaultProtocol;

/**
 * Subscribe protocol wrapper.

 *
 * @author Jiri Vokrinek
 */
public class SubscribeProtocol extends DefaultProtocol {

    static final String SUBSCRIBE_PROTOCOL_NAME = "SUBSCRIBE_PROTOCOL";

    /**
     *
     * @param communicator
     * @param name
     */
    public SubscribeProtocol(Communicator communicator, String name) {
        super(communicator, SUBSCRIBE_PROTOCOL_NAME + ": " + name);
    }
}
