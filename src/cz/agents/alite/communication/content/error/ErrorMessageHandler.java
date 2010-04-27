package cz.agents.alite.communication.content.error;

import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.MessageHandler;

public abstract class ErrorMessageHandler implements MessageHandler {

    public ErrorMessageHandler() {
    }

    @Override
    public void notify(Message message) {
        if (ErrorContent.class.equals(message.getContent().getClass())) {
            ErrorContent content = (ErrorContent) message.getContent();
            handleMessage(message, content);
        }
    }

    abstract public void handleMessage(Message message, ErrorContent content);
}
