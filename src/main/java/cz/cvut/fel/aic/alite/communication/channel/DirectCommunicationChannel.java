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
package cz.cvut.fel.aic.alite.communication.channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import cz.cvut.fel.aic.alite.communication.CommunicationReceiver;
import cz.cvut.fel.aic.alite.communication.Message;

/**
 * Direct Call communication channel.
 * Uses direct calling of the receiveMessage method.
 *
 * @author Jiri Vokrinek
 */
public class DirectCommunicationChannel extends DefaultCommunicationChannelBroadcast {

	private final ReceiverTable channelReceivers;
	@Deprecated
	private final static ReceiverTable obsoleteSingletonTable = new DefaultReceiverTable();

	/**
	 * Use DirectCommunicationChannel(CommunicationReceiver communicator, Map<String, CommunicationReceiver> channelReceiversTable) instead.
	 * 
	 * @param communicator
	 * @throws CommunicationChannelException  
	 */
	@Deprecated
	public DirectCommunicationChannel(CommunicationReceiver communicator) throws CommunicationChannelException {
		this(communicator, obsoleteSingletonTable);
	}

	/**
	 * 
	 * @param communicator
	 * @param channelReceiversTable A map for manipulating receivers on this communication channel. All the instances of shared communication channel have to share the same instance of channelReceiversTable.
	 * @throws CommunicationChannelException
	 */
	public DirectCommunicationChannel(CommunicationReceiver communicator, ReceiverTable channelReceiverTable) throws CommunicationChannelException {
		super(communicator);
		channelReceivers = channelReceiverTable;
		if (null != channelReceivers.put(communicator.getAddress(), communicator)) {
			throw new DuplicateReceiverAddressException(communicator.getAddress());
		}
	}

	@Override
	public void sendMessage(Message message) throws CommunicationChannelException {
		Set<String> unknownReceivers = new LinkedHashSet<String>();

		if (message.getReceivers().contains(BROADCAST_ADDRESS)) {
			for (CommunicationReceiver receiver : channelReceivers.values()) {
				callDirectReceive(receiver, message);
			}
		} else {
			Collection<String> receivers = message.getReceivers();
			for (String communicatorAddress : receivers) {
				if (channelReceivers.contains(communicatorAddress)) {
					callDirectReceive(channelReceivers.get(communicatorAddress), message);
				} else {
					unknownReceivers.add(communicatorAddress);
				}
			}

			if (!unknownReceivers.isEmpty()) {
				throw new UnknownReceiversException(unknownReceivers);
			}
		}
	}

	/**
	 * Passes a message to the communication receiver using direct call.
	 *
	 * @param receiver
	 * @param message
	 */
	protected void callDirectReceive(CommunicationReceiver receiver, Message message) {
		receiver.receiveMessage(message);
	}

	public interface ReceiverTable {

		public CommunicationReceiver put(String address, CommunicationReceiver communicator);

		public Iterable<CommunicationReceiver> values();

		public boolean contains(String communicatorAddress);

		public CommunicationReceiver get(String communicatorAddress);
	}

	public static class DefaultReceiverTable implements ReceiverTable {

		private final HashMap<String, CommunicationReceiver> table = new HashMap<String, CommunicationReceiver>();

		@Override
		public CommunicationReceiver put(String address, CommunicationReceiver communicator) {
			return table.put(address, communicator);
		}

		@Override
		public Iterable<CommunicationReceiver> values() {
			return table.values();
		}

		@Override
		public boolean contains(String communicatorAddress) {
			return table.containsKey(communicatorAddress);
		}

		@Override
		public CommunicationReceiver get(String communicatorAddress) {
			return table.get(communicatorAddress);
		}
	}
}
