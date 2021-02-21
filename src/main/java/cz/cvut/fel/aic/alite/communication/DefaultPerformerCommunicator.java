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

import java.util.LinkedList;
import java.util.List;

import cz.cvut.fel.aic.alite.communication.channel.CommunicationPerformerChannel;
import org.slf4j.LoggerFactory;

/**
 * Used to implement passive communication - in order to receive any messages 
 * via a MessageHandler, the performReceive() method must be invoked. 
 * The messages (if any) are delivered via registered callbacks using the same thread which called 
 * the performReceive() method. The call is non-blocking.
 * 
 * @author stolba
 *
 */
public class DefaultPerformerCommunicator extends DefaultCommunicator implements PerformerCommunicator {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DefaultPerformerCommunicator.class);

	private final List<CommunicationPerformerChannel> performerChannels = new LinkedList<CommunicationPerformerChannel>();
	
	public DefaultPerformerCommunicator(String address) {
		super(address);
	}

	@Override
	public boolean performReceiveNonblock() {
		boolean received=false;
		for(CommunicationPerformerChannel channel : performerChannels){
			received = received || channel.performReceiveNonblock();
		}
		return received;
	}
	
	@Override
	public void performReceiveBlock(long timeout) {
		for(CommunicationPerformerChannel channel : performerChannels){
			channel.performReceiveBlock(timeout);
		}
	}
	
	/**
	 * Add a passive channel.
	 * WARNING: The performReceive() method must be called in order to receive any messages!
	 * @param channel
	 */
	public void addPerformerChannel(CommunicationPerformerChannel channel) {
		performerChannels.add(channel);
		addChannel(channel);
	}
	
	@Override
	public void addMessageHandler(MessageHandler handler) {
		if(!performerChannels.isEmpty()){
			LOGGER.info("The performReceive() method must be (periodically) called in order to receive any messages!");
		}
		super.addMessageHandler(handler);
	}

	@Override
	public void performClose() {
		for(CommunicationPerformerChannel channel : performerChannels){
			channel.performClose();
		}
	}

	

}
