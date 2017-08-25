package cz.cvut.fel.aic.alite.communication.channel;

import cz.cvut.fel.aic.alite.communication.Message;

/**
 *
 * @author Jiri Vokrinek
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
