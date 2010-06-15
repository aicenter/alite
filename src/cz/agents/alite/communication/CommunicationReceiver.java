package cz.agents.alite.communication;

/**
 *
 * @author vokrinek
 */
public interface CommunicationReceiver {

    /**
     * Receives a message obtained by the communication.
     *
     * @param message
     */
    void receiveMessage(Message message);

    /**
     * Gets communication address.
     * @return
     */
    String getAddress();
}
