package cz.agents.alite.communication.channel;

import cz.agents.alite.communication.CommunicationReceiver;
import cz.agents.alite.communication.Message;

/**
 *
 * @author vokrinek
 */
public abstract class DefaultCommunicationChannel implements CommunicationChannel {

    private final CommunicationReceiver communicationReceiver;

    public DefaultCommunicationChannel(CommunicationReceiver communicator) {
        this.communicationReceiver = communicator;
    }

    public abstract void sendMessage(Message message) throws CommunicationChannelException;

    public void receiveMessage(Message message) {
        communicationReceiver.receiveMessage(message);
    }

}
