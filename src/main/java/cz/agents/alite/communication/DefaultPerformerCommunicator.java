package cz.agents.alite.communication;

import java.util.LinkedList;
import java.util.List;

import cz.agents.alite.communication.channel.CommunicationPerformerChannel;

public class DefaultPerformerCommunicator extends DefaultCommunicator implements PerformerCommunicator {

	
	private final List<CommunicationPerformerChannel> performerChannels = new LinkedList<CommunicationPerformerChannel>();
	
	public DefaultPerformerCommunicator(String address) {
		super(address);
	}

	@Override
	public void performReceive() {
		for(CommunicationPerformerChannel channel : performerChannels){
			channel.performReceive();
		}
	}
	
	public void addPerformerChannel(CommunicationPerformerChannel channel) {
		performerChannels.add(channel);
		addChannel(channel);
    }

}
