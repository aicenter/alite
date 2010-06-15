package cz.agents.alite.communication;

/**
 *
 * @author vokrinek
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
