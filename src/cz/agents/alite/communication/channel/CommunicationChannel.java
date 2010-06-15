package cz.agents.alite.communication.channel;

import cz.agents.alite.communication.Message;

/**
 *
 * @author vokrinek
 */
public interface CommunicationChannel {

    /**
     * Send a message using this communication channel.
     *
     * @param message
     * @throws CommunicationChannelException
     */
    public void sendMessage(Message message) throws CommunicationChannelException;

    /**
     * Receive a message using this communication channel.
     *
     * @param message
     */
    public void receiveMessage(Message message);

}
