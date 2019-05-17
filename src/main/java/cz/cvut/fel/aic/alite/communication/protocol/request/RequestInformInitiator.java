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
package cz.cvut.fel.aic.alite.communication.protocol.request;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;

/**
 *
 * Initiator part of the basic Request-inform protocol.
 * When created, it registeres protocol handler to the given communicator.
 * In the end of the protocol the handler is automatically derregistered.
 *
 * @author Jiri Vokrinek
 */
public abstract class RequestInformInitiator extends RequestInform {

	private final Object contentData;
	private final String participantAddres;
	private final String session;
	private MessageHandler messagehandler;
	private boolean active = false;

	/**
	 *
	 * @param communicator
	 * @param name	  Custom protocol name to differentiate several Request-informs
	 * @param contentData
	 * @param participantAddres
	 */
	public RequestInformInitiator(Communicator communicator, String name, Object contentData, String participantAddres) {
		super(communicator, name);
		this.contentData = contentData;
		this.participantAddres = participantAddres;
		this.session = generateSession();
		initProtocol();
	}

	/**
	 * Check of the protocol activity.
	 *
	 * @return true if the protocol is running; false when finnished.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Cancels the protocol and terminates it.
	 * The inform will not be processed.
	 */
	public void cancel() {
		active = false;
		//TODO: is this safe?
		communicator.removeMessageHandler(messagehandler);
	}

	/**
	 * Processes obtained inform.
	 * The protocol is automaticaly terminated.
	 *
	 * @param inform
	 * @param session 
	 */
	abstract protected void processInform(Object inform, String session);

	private void initProtocol() {

		messagehandler = new ProtocolMessageHandler(this, session) {

			@Override
			public void handleMessage(Message message, ProtocolContent content) {
				processMessage(message, content);
			}
		};
		communicator.addMessageHandler(messagehandler);
		ProtocolContent content = new ProtocolContent(this, Performative.REQUEST, getContentData(), session);
		Message message = communicator.createMessage(content);
		message.addReceiver(participantAddres);
		active = true;
		communicator.sendMessage(message);
	}

	private void processMessage(Message message, ProtocolContent content) {
		switch (content.getPerformative()) {
			case INFORM:
				active = false;
				communicator.removeMessageHandler(messagehandler);
				processInform(content.getData(), session);
				break;
			default:
		}
	}

	/**
	 * @return the contentData
	 */
	public Object getContentData() {
		return contentData;
	}
}
