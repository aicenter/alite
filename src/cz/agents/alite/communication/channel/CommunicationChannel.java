package cz.agents.alite.communication.channel;

import cz.agents.alite.communication.Message;

public interface CommunicationChannel {

    public void sendMessage(Message message) throws CommunicationChannelException;

    public void receiveMessage(Message message);

}
