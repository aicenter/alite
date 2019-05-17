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

import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;

/**
 * The message handler for protocol-based messaging.
 *
 * @author Jiri Vokrinek
 */
public abstract class ProtocolMessageHandler implements MessageHandler {

	private final Protocol protocol;
	private final Performative performative;
	private final String session;

	/**
	 *
	 * @param protocol
	 * @param performative
	 * @param session
	 */
	public ProtocolMessageHandler(Protocol protocol, Performative performative, String session) {
		if (protocol == null) {
			throw new IllegalArgumentException("null Protocol is not permitted");
		}
		this.protocol = protocol;
		this.performative = performative;
		this.session = session;
	}

	/**
	 *
	 * @param protocol
	 * @param session
	 */
	public ProtocolMessageHandler(Protocol protocol, String session) {
		this(protocol, null, session);
	}

	/**
	 *
	 * @param protocol
	 */
	public ProtocolMessageHandler(Protocol protocol) {
		this(protocol, null, null);
	}


	@Override
	public void notify(Message message) {
		if (ProtocolContent.class.equals(message.getContent().getClass())) {
			ProtocolContent content = (ProtocolContent) message.getContent();
			if (content.getProtocolName().equals(protocol.getName())) {
				if (performative == null || content.getPerformative().equals(performative)) {
					if (session == null || content.getSession().equals(session)) {
						handleMessage(message, content);
					}
				}
			}
		}
	}

	/**
	 * Handler for protocol content processing.
	 *
	 * @param message
	 * @param content
	 */
	abstract public void handleMessage(Message message, ProtocolContent content);
}
