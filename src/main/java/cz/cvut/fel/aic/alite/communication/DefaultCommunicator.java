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
package cz.cvut.fel.aic.alite.communication;

import cz.cvut.fel.aic.alite.communication.channel.CommunicationChannel;
import cz.cvut.fel.aic.alite.communication.channel.CommunicationChannelException;
import cz.cvut.fel.aic.alite.communication.content.Content;
import cz.cvut.fel.aic.alite.communication.content.error.ErrorContent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Jiri Vokrinek
 */
public class DefaultCommunicator implements Communicator {

	private final String address;
	private final List<CommunicationChannel> channels = new LinkedList<CommunicationChannel>();
	private final List<MessageHandler> messageHandlers = new CopyOnWriteArrayList<MessageHandler>();

	private static long counter = System.currentTimeMillis();

	/**
	 *
	 * @param address
	 */
	public DefaultCommunicator(String address) {
		this.address = address;
	}

	/**
	 * Adds communication channel to the communicator.
	 *
	 * @param channel
	 */
	public void addChannel(CommunicationChannel channel) {
		channels.add(channel);
	}


	@Override
	public String getAddress() {
		return address;
	}


	@Override
	public Message createMessage(Content content) {
		return new Message(address, content, generateId());
	}


	@Override
	public Message createReply(Message message, Content content) {
		Message reply = new Message(address, content, generateId());
		reply.addReceiver(message.getSender());
		return reply;
	}


	@Override
	public void addMessageHandler(MessageHandler handler) {
		messageHandlers.add(handler);
	}


	@Override
	public void removeMessageHandler(MessageHandler handler) {
		messageHandlers.remove(handler);
	}


	@Override
	public void sendMessage(Message message) {
		for (CommunicationChannel channel : channels) {
			try {
				channel.sendMessage(message);
			} catch (CommunicationChannelException e) {
				Message errorMessage = createMessage(new ErrorContent(e));
				errorMessage.addReceiver(getAddress());
				receiveMessage(errorMessage);
			}
		}
	};


	@Override
	public synchronized void receiveMessage(Message message) {
		for (MessageHandler messageHandler : messageHandlers) {
			messageHandler.notify(message);
		}
	}

	private long generateId() {
		return address.hashCode() + counter;
	}
	
	@Override
	public String toString() {
		return channels.toString();
	}
}
