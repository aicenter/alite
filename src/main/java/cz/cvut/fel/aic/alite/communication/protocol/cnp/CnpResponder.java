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
package cz.cvut.fel.aic.alite.communication.protocol.cnp;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 *
 * Responder part of the basic Contract-net-protocol.
 * When created, it registeres protocol handler to the given communicator.
 * The CnpInitiator maintains all the active CNPs of the given type.
 * To deactivate the protocol, call the appropriate method.
 *
 * @author Jiri Vokrinek
 */
//TODO: state machine
public abstract class CnpResponder extends Cnp {

	private final LinkedHashMap<String, Object> sentProposals = new LinkedHashMap<String, Object>();
	private final LinkedHashMap<String, Message> cnpRequests = new LinkedHashMap<String, Message>();
	private final LinkedHashSet<String> openCnp = new LinkedHashSet<String>();
	private MessageHandler messagehandler;

	/**
	 *
	 * @param communicator
	 * @param name	  Custom protocol name to differentiate several CNPs
	 */
	public CnpResponder(Communicator communicator, String name) {
		super(communicator, name);
		initProtocol();
	}

	/**
	 * Prepare proposal for the given request identified by the session
	 *
	 * @param request
	 * @param session
	 * @return Object representing the proposal; null to REFUSE
	 */
	abstract protected Object prepareProposal(Object request, String session);

	/**
	 * Accepts proposal with given session identification.
	 *
	 * @param session
	 */
	abstract protected void proposalAccepted(String session);

	/**
	 * Rejects proposal with given session identification.
	 *
	 * @param session
	 */
	abstract protected void proposalRejected(String session);

	/**
	 * The acceped proposal with given session identification is cancelled.
	 *
	 * @param session
	 */
	abstract protected void canceled(String session);

	/**
	 * Call when the protocol of corresponding session is successfully terminated.
	 * The DONE message is sent to the CnpInitiator.
	 *
	 * @param session session identifying the protocol
	 */
	public void informDone(String session) {
		Message message = cnpRequests.remove(session);
		sentProposals.remove(session);
		communicator.sendMessage(createReply(message, Performative.DONE, null, session));
	}

	/**
	 * Call when the protocol of corresponding session is unsuccessfully terminated.
	 * The FAILURE message is sent to the CnpInitiator.
	 *
	 * @param session session identifying the protocol
	 */
	public void informFail(String session) {
		Message message = cnpRequests.remove(session);
		sentProposals.remove(session);
		communicator.sendMessage(createReply(message, Performative.FAILURE, null, session));
	}

	/**
	 * Check of the protocol activity.
	 *
	 * @param session
	 * @return true if the protocol session is running; false when finnished.
	 */
	public boolean isActive(String session) {
		return cnpRequests.containsKey(session);
	}

	/**
	 * Gets the previously sent proposal.
	 *
	 * @param session session identifying the proposal
	 * @return content body of the proposal
	 */
	public Object getSentProposal(String session) {
		return sentProposals.get(session);
	}

	/**
	 * Gets the previously obtained request.
	 *
	 * @param session session identifying the request
	 * @return content body of the request
	 */
	public Object getRequest(String session) {
		return cnpRequests.get(session).getContent().getData();

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
			case CANCEL:
				if (openCnp.contains(session)) {
					openCnp.remove(session);
					proposalRejected(session);
					cnpRequests.remove(session);
					sentProposals.remove(session);
				} else {
					canceled(session);
				}
				communicator.sendMessage(createReply(message, Performative.INFORM, null, session));
				break;
			case ACCEPT_PROPOSAL:
				openCnp.remove(session);
				proposalAccepted(session);
				//TODO: potential DISCONFIRM branching
				communicator.sendMessage(createReply(message, Performative.CONFIRM, null, session));
				break;
			case REJECT_PROPOSAL:
				openCnp.remove(session);
				proposalRejected(session);
				cnpRequests.remove(session);
				sentProposals.remove(session);
				break;
			case CALL_FOR_PROPOSAL:
				Object response = prepareProposal(body, session);
				Message msg;
				if (response != null) {
					openCnp.add(session);
					cnpRequests.put(session, message);
					sentProposals.put(session, response);
					msg = createReply(message, Performative.PROPOSE, response, session);
				} else {
					msg = createReply(message, Performative.REFUSE, null, session);
				}
				communicator.sendMessage(msg);
				break;
			default:
		}
	}

	private Message createReply(Message message, Performative performative, Object body, String session) {
		return communicator.createReply(message, new ProtocolContent(this, performative, body, session));
	}
}
