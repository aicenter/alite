package cz.agents.alite.communication;

import cz.agents.alite.communication.content.Content;


public interface Communicator extends CommunicationSender, CommunicationReceiver {

    Message createMessage(Content content);

    Message createReply(Message message, Content content);

    void addMessageHandler(MessageHandler handler);

    void removeMessageHandler(MessageHandler handler);

}
