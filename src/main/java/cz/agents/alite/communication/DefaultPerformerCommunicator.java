package cz.agents.alite.communication;

import java.util.LinkedList;
import java.util.List;

import cz.agents.alite.communication.channel.CommunicationChannel;
import cz.agents.alite.communication.channel.PerformerCommunicationChannel;

public class DefaultPerformerCommunicator extends DefaultCommunicator implements CommunicationPerformer {

	
	private final List<PerformerCommunicationChannel> performerChannels = new LinkedList<PerformerCommunicationChannel>();
	
	public DefaultPerformerCommunicator(String address) {
		super(address);
	}

	@Override
	public void performReceive() {
		for(PerformerCommunicationChannel channel : performerChannels){
			channel.performReceive();
		}
	}
	
	public void addPerformerChannel(PerformerCommunicationChannel channel) {
		performerChannels.add(channel);
    }

}
