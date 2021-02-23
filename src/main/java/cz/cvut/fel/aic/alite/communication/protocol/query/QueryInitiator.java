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
package cz.cvut.fel.aic.alite.communication.protocol.query;

import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The general Query protocol. For each responder instance the method {@link sendQuery(Object query)}
 * is called on all subscribers except the initiator.
 *
 * @author Jiri Vokrinek
 * @author Antonin Komenda
 */
public abstract class QueryInitiator extends Query {

	final String entityAddress;
	private final Object query;
	private final Set<String> responderAddresses;
	private final Set<String> pendingResponders;
	private final String session;
	private final Set<Object> answers = new HashSet<Object>();

	private MessageHandler messagehandler;

	/**
	 *
	 * @param communicator
	 * @param directory
	 * @param name
	 * @param query
	 */
	public QueryInitiator(Communicator communicator, CapabilityRegister directory, String name, Object query) {
		super(communicator, name);
		this.entityAddress = communicator.getAddress();
		this.query = query;
		this.session = generateSession();
		Set<String> addresses = directory.getIdentities(getName());
		responderAddresses = new LinkedHashSet<String>(addresses);
		responderAddresses.remove(entityAddress);
		this.pendingResponders = new LinkedHashSet<String>(responderAddresses);
		initProtocol();
	}

	private void initProtocol() {

		messagehandler = new ProtocolMessageHandler(this, session) {

			@Override
			public void handleMessage(Message message, ProtocolContent content) {
				processMessage(message, content);
			}
		};
		communicator.addMessageHandler(messagehandler);
		ProtocolContent content = new ProtocolContent(this, Performative.QUERY, query, session);
		Message message = communicator.createMessage(content);
		message.addReceivers(responderAddresses);
		communicator.sendMessage(message);
	}

	private void processMessage(Message message, ProtocolContent content) {
		switch (content.getPerformative()) {
			case INFORM:
				pendingResponders.remove(message.getSender());
				answers.add(content.getData());
				checkAnswers();
				break;
			default:
		}
	}

	private void checkAnswers() {
		if (pendingResponders.isEmpty()) {
			evaluateReplies(answers);
			communicator.removeMessageHandler(messagehandler);
		}
	}

	/**
	 * Evaluates obtained replied queries.
	 * @param answers a set of the answered queries from the responders
	 */
	abstract protected void evaluateReplies(Set<Object> answers);
}
