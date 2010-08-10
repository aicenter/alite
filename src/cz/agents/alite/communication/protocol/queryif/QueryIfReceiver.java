package cz.agents.alite.communication.protocol.queryif;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.MessageHandler;
import cz.agents.alite.communication.protocol.ProtocolContent;
import cz.agents.alite.communication.protocol.ProtocolMessageHandler;
import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.communication.protocol.Performative;

/**
 * The general QueryIf protocol. For each receiver instance the method {@link handleQuery(Object query)}
 * is called when sender invokes a query.
 *
 * @author Jiri Vokrinek
 */
public abstract class QueryIfReceiver extends QueryIf {

    private final MessageHandler messagehandler;
    final String agentName;

    /**
     *
     * @param communicator
     * @param directory
     * @param name
     */
    public QueryIfReceiver(final Communicator communicator, CapabilityRegister directory, String name) {
        super(communicator, name);
        this.agentName = communicator.getAddress();
        directory.register(agentName, getName());
        messagehandler = new ProtocolMessageHandler(this) {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                processMessage(message, content);
            }
        };
        communicator.addMessageHandler(messagehandler);
    }

    private void processMessage(Message message, ProtocolContent content) {
        String session = content.getSession();
        Object body = content.getData();
        switch (content.getPerformative()) {
            case QUERY_IF:
                boolean answer = handleQuery(body);
                Message msg = createReply(message, Performative.QUERY_REF, answer, session);
                communicator.sendMessage(msg);
                break;
            default:
        }
    }

    private Message createReply(Message message, Performative performative, Object body, String session) {
        return communicator.createReply(message, new ProtocolContent(this, performative, body, session));
    }

    /**
     * This methods is called if some other agent sends QueryIf.
     *
     * @param query
     * @return true if the query answer is YES, false is query answer is NO
     */
    abstract protected boolean handleQuery(Object query);
}
