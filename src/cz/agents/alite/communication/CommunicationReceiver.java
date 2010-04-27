/**
 *
 */
package cz.agents.alite.communication;

public interface CommunicationReceiver {

    void receiveMessage(Message message);

    String getAddress();
}
