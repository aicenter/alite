package cz.agents.alite.communication;

import cz.agents.alite.communication.content.Content;


/**
 *
 * @author vokrinek
 */
public interface Communicator extends CommunicationSender, CommunicationReceiver {

    /**
     * Creates appropriate message using content.
     *
     * @param content
     * @return
     */
    Message createMessage(Content content);

    /**
     * Creates valid reply to the message using given content.
     *
     * @param message
     * @param content
     * @return
     */
    Message createReply(Message message, Content content);

    /**
     * Registers a message handler to receive messages.
     *
     * @param handler
     */
    void addMessageHandler(MessageHandler handler);

    /**
     * Degisters a message handler.
     *
     * @param handler
     */
    void removeMessageHandler(MessageHandler handler);

}
