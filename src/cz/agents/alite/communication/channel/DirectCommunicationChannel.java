package cz.agents.alite.communication.channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import cz.agents.alite.communication.CommunicationReceiver;
import cz.agents.alite.communication.Message;

/**
 *
 * @author vokrinek
 */
public class DirectCommunicationChannel extends DefaultCommunicationChannel {

    private static final HashMap<String, CommunicationReceiver> channelReceivers = new HashMap<String, CommunicationReceiver>();

    public DirectCommunicationChannel(CommunicationReceiver communicator) {
        super(communicator);
        channelReceivers.put(communicator.getAddress(), communicator);
    }

    @Override
    public void sendMessage(Message message) throws CommunicationChannelException {
        Set<String> unknownReceivers = new LinkedHashSet<String>();

        Collection<String> receivers = message.getReceivers();
        for (String communicatorAddress : receivers) {
            if (channelReceivers.containsKey(communicatorAddress)) {
                channelReceivers.get(communicatorAddress).receiveMessage(message);
            } else {
                unknownReceivers.add(communicatorAddress);
            }
        }

        if (!unknownReceivers.isEmpty()) {
            throw new UnknownReceiversException(unknownReceivers);
        }
    }
}
