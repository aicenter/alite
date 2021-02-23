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
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;

/**
 * The general subscription protocol. For each subscribe instance the method {@link handleInform(Object inform)}
 * is called on all subscribers except the invoker.
 *
 * @author Jiri Vokrinek
 */
public abstract class SubscribeProtocolReceiver extends SubscribeProtocolSender {

	private final MessageHandler messagehandler;

	/**
	 *
	 * @param communicator
	 * @param directory
	 * @param name
	 */
	public SubscribeProtocolReceiver(final Communicator communicator, CapabilityRegister directory, String name) {
		super(communicator, directory, name);
		directory.register(agentName, getName());
		messagehandler = new ProtocolMessageHandler(this) {

			@Override
			public void handleMessage(Message message, ProtocolContent content) {
				processMessage(content);
			}
		};
		communicator.addMessageHandler(messagehandler);
	}

	private void processMessage(ProtocolContent content) {
//		String session = content.getSession();
		Object body = content.getData();
		switch (content.getPerformative()) {
			case INFORM:
				handleInform(body);
				break;
			default:
		}
	}

	/**
	 * This methods is called if some other agent sends the subscribed inform to this protocol.
	 *
	 * @param inform
	 */
	abstract protected void handleInform(Object inform);
}
