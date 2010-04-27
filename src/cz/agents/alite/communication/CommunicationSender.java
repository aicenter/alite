package cz.agents.alite.communication;

public interface CommunicationSender {

    void sendMessage(Message message);

    String getAddress();
}
