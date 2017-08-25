package cz.cvut.fel.aic.alite.communication;

/**
 *
 * @author Jiri Vokrinek
 */
public interface CommunicationSender {

    /**
     * Sends a message.
     *
     * @param message
     */
    void sendMessage(Message message);

    /**
     * Gets communication address.
     * @return
     */
    String getAddress();
}
