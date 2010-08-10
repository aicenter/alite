package cz.agents.alite.communication.protocol.queryif;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.protocol.Performative;
import cz.agents.alite.communication.protocol.ProtocolContent;
import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.communication.MessageHandler;
import cz.agents.alite.communication.protocol.ProtocolMessageHandler;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The general QueryIf protocol. For each responder instance the method {@link sendQuery(Object query)}
 * is called on all subscribers except the invoker.
 *
 * @author Jiri Vokrinek
 */
public abstract class QueryIfSender extends QueryIf {

    final String agentName;
    private final Object query;
    private final Set<String> participantAddress;
    private final HashSet<String> pendingParticipants;
    private final String session;
    private boolean yesAnswerReceived = false;
    private boolean noAnswerReceived = false;
    //
    private MessageHandler messagehandler;

    /**
     *
     * @param communicator
     * @param directory
     * @param name
     * @param query
     */
    public QueryIfSender(Communicator communicator, CapabilityRegister directory, String name, Object query) {
        super(communicator, name);
        this.agentName = communicator.getAddress();
        this.query = query;
        this.session = generateSession();
        Set<String> addresses = directory.getIdentities(getName());
        participantAddress = new LinkedHashSet<String>(addresses);
        participantAddress.remove(agentName);
        this.pendingParticipants = new LinkedHashSet<String>(participantAddress);
        initProtocol();
    }

    private void initProtocol() {

        messagehandler = new ProtocolMessageHandler(this, session) {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                processMessage(message, content);
            }
        };
        communicator.addMessageHandler(messagehandler);
        ProtocolContent content = new ProtocolContent(this, Performative.QUERY_IF, query, session);
        Message message = communicator.createMessage(content);
        message.addReceivers(participantAddress);
        communicator.sendMessage(message);
    }

    private void processMessage(Message message, ProtocolContent content) {
        switch (content.getPerformative()) {
            case QUERY_REF:
                pendingParticipants.remove(message.getSender());
                checkAnswers((Boolean) content.getData());
                break;
            default:
        }
    }

    private void checkAnswers(boolean answer) {
        if (answer) {
            yesAnswerReceived = true;
        } else {
            noAnswerReceived = true;
        }

        if (pendingParticipants.isEmpty()) {
            evaluateReplies(yesAnswerReceived, noAnswerReceived);
        }
    }

    /**
     * Evaluates obtained reply.
     *
     * @param yesAnswer true if any responder answered YES, false otherwise
     * @param noAnswer true if any responder answered NO, false otherwise
     */
    abstract protected void evaluateReplies(boolean yesAnswer, boolean noAnswer);
}
