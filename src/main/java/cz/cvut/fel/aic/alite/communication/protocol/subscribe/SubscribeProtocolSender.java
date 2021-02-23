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
package cz.cvut.fel.aic.alite.communication.protocol.subscribe;

import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import java.util.Set;

/**
 * The general subscription protocol. For each subscribe instance the method {@link handleInform(Object inform)}
 * is called on all subscribers except the invoker.
 *
 * {@link SubscribeProtocolSender} allows only information sending. It does not make the subscription.
 *
 * @author Jiri Vokrinek
 */
public abstract class SubscribeProtocolSender extends SubscribeProtocol {

	final String agentName;
	private final CapabilityRegister directory;

	/**
	 *
	 * @param communicator
	 * @param directory
	 * @param name
	 */
	public SubscribeProtocolSender(Communicator communicator, CapabilityRegister directory, String name) {
		super(communicator, name);
		this.directory = directory;
		this.agentName = communicator.getAddress();
	}

	/**
	 * Sends a message to subscrabers of this protocol.
	 *
	 * @param inform a content object to be sent.
	 */
	public void sendInform(Object inform) {
		ProtocolContent content = new ProtocolContent(this, Performative.INFORM, inform, generateSession());
		Message message = communicator.createMessage(content);
		Set<String> addresses = directory.getIdentities(getName());
		addresses.remove(agentName);
		message.addReceivers(addresses);
		communicator.sendMessage(message);
	}
}
