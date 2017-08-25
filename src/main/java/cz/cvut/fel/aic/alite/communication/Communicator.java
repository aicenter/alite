package cz.cvut.fel.aic.alite.communication;

import cz.cvut.fel.aic.alite.communication.content.Content;


/**
 *
 * @author Jiri Vokrinek
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
     * Unregisters a message handler.
     *
     * @param handler
     */
    void removeMessageHandler(MessageHandler handler);

}
