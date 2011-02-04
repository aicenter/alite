package cz.agents.alite.communication.channel;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import cz.agents.alite.communication.CommunicationReceiver;
import cz.agents.alite.communication.Message;

/**
 * Direct Call communication channel.
 * Uses direct calling of the receiveMessage method.
 * By default it uses static capability register for communication channels maintaining.
 *
 * @author Jiri Vokrinek
 */
public class DirectCommunicationChannel extends DefaultCommunicationChannelBroadcast {

    private static HashMap<String, CommunicationReceiver> channelReceivers = null;// = new HashMap<String, CommunicationReceiver>();

    /**
     *
     * @param communicator
     */
    public DirectCommunicationChannel(CommunicationReceiver communicator) throws CommunicationChannelException {
        super(communicator);

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
                if (channelReceivers.containsKey(communicatorAddress)) {
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
}
