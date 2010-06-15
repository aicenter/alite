package cz.agents.alite.communication;


/**
 *
 * @author vokrinek
 */
public abstract class DefaultMessageHandler implements MessageHandler {


    @Override
    public void notify(Message message) {
        handleMessage(message);
    }

    /**
     * Method called when the message handler receives message.
     * @param message
     */
    abstract public void handleMessage(Message message);
}
