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
package cz.cvut.fel.aic.alite.communication.protocol.request;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;
import java.util.LinkedHashMap;

/**
 *
 * Responder part of the basic Request-inform.
 * When created, it registeres protocol handler to the given communicator.
 * The RequestInformInitiator maintains all the active requests of the given type.
 * To deactivate the protocol, call the appropriate method.
 *
 * @author Jiri Vokrinek
 */
public abstract class RequestInformResponder extends RequestInform {

	private final LinkedHashMap<String, Message> requests = new LinkedHashMap<String, Message>();
	private MessageHandler messagehandler;

	/**
	 *
	 * @param communicator
	 * @param name	  Custom protocol name to differentiate several RequestInforms
	 */
	public RequestInformResponder(Communicator communicator, String name) {
		super(communicator, name);
		initProtocol();
	}

	/**
	 * Prepare inform for the given request identified by the session.
	 * When the inform is prepared call {@link inform(String session, Object inform)} method
	 *
	 * @param request
	 * @param session
	 */
	abstract protected void handleRequest(Object request, String session);

	/**
	 * Invokes the inform part of the protocol. Does nothing if session is not active.
	 *
	 * @param session
	 * @param inform
	 */
	public void inform(String session, Object inform) {
		Message message = requests.remove(session);
		if (message != null) {
			communicator.sendMessage(createReply(message, Performative.INFORM, inform, session));
		}
	}

	/**
	 * Check of the protocol activity.
	 *
	 * @param session
	 * @return true if the protocol session is running; false when finnished.
	 */
	public boolean isActive(String session) {
		return requests.containsKey(session);
	}

	/**
	 * Searches for request
	 *
	 * @param session
	 * @return request of corresponding session, null if request is not active
	 */
	public Object getRequest(String session) {
		return requests.get(session).getContent().getData();

	}

	private void initProtocol() {

		messagehandler = new ProtocolMessageHandler(this) {

			@Override
			public void handleMessage(Message message, ProtocolContent content) {
				processMessage(message, content);
			}
		};
		communicator.addMessageHandler(messagehandler);
	}

	private void processMessage(Message message, ProtocolContent content) {
		String session = content.getSession();
		Object body = content.getData();
		switch (content.getPerformative()) {
			case REQUEST:
				requests.put(session, message);
				handleRequest(body, session);
				break;
			default:
		}
	}

	private Message createReply(Message message, Performative performative, Object body, String session) {
		return communicator.createReply(message, new ProtocolContent(this, performative, body, session));
	}
}
