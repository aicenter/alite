package cz.agents.alite.communication.protocol;

import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.MessageHandler;

/**
 *
 * @author vokrinek
 */
public abstract class ProtocolMessageHandler implements MessageHandler {

    private final Protocol protocol;
    private final Performative performative;
    private final String session;

    public ProtocolMessageHandler(Protocol protocol, Performative performative, String session) {
        if (protocol == null) {
            throw new IllegalArgumentException("null Protocol is not permitted");
        }
        this.protocol = protocol;
        this.performative = performative;
        this.session = session;
    }

    public ProtocolMessageHandler(Protocol protocol, String session) {
        this(protocol, null, session);
    }

    public ProtocolMessageHandler(Protocol protocol) {
        this(protocol, null, null);
    }

    @Override
    public void notify(Message message) {
        if (ProtocolContent.class.equals(message.getContent().getClass())) {
            ProtocolContent content = (ProtocolContent) message.getContent();
            if (content.getProtocol().equals(protocol)) {
                if (performative == null || content.getPerformative().equals(performative)) {
                    if (session == null || content.getSession().equals(session)) {
                        handleMessage(message, content);
                    }
                }
            }
        }
    }

    abstract public void handleMessage(Message message, ProtocolContent content);
}
