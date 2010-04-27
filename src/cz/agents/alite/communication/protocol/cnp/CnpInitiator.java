package cz.agents.alite.communication.protocol.cnp;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.MessageHandler;
import cz.agents.alite.communication.protocol.Performative;
import cz.agents.alite.communication.protocol.ProtocolContent;
import cz.agents.alite.communication.protocol.ProtocolMessageHandler;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Initiator part of the basic Contract-net-protocol.
 * When created, it registeres protocol handler to the given communicator.
 * In the end of the protocol the handler is automatically derregistered.
 *
 * @author vokrinek
 */
//TODO: state machine
public abstract class CnpInitiator extends Cnp {

    private final Object contentData;
    private final Set<String> participantAddress;
    private final HashSet<String> pendingParticipants;
    private final LinkedHashMap<String, Object> responses = new LinkedHashMap<String, Object>();
    private final LinkedHashMap<String, Message> messages = new LinkedHashMap<String, Message>();
    private final String session;
    private MessageHandler messagehandler;
    private boolean active = false;
    private String winner = null;

    /**
     *
     * @param communicator
     * @param name      Custom protocol name to differentiate several CNPs
     * @param contentData
     * @param participantAddress
     */
    public CnpInitiator(Communicator communicator, String name, Object contentData, Set<String> participantAddress) {
        super(communicator, name);
        this.contentData = contentData;
        this.participantAddress = new LinkedHashSet<String>(participantAddress);
        this.session = generateSession();
        this.pendingParticipants = new LinkedHashSet<String>(participantAddress);
        initProtocol();
    }

    /**
     * Check of the protocol activity.
     *
     * @return true if the protocol is running; false when finnished.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Cancels the protocol and terminates it.
     */
    public void cancel() {
        ProtocolContent content = new ProtocolContent(this, Performative.CANCEL, null, session);
        Message message = communicator.createMessage(content);
        if (winner != null) {
            message.addReceiver(winner);
        } else {
            message.addReceivers(participantAddress);
        }
        communicator.sendMessage(message);
        active = false;
        communicator.removeMessageHandler(messagehandler);
    }

    /**
     * Evaluates obtained proposals.
     * After evaluation the ACCEPT message is automatically sent
     * to the winner and REJECT to the others.
     *
     * @param responses Map of <participant address, response>
     * @return sellected winning participant address; null if no proposal is acceptable
     */
    abstract protected String evaluateReplies(LinkedHashMap<String, Object> responses);

    /**
     * Method is called when accepted responder reports FAIL
     * Then the protocol is terminated
     *
     */
    abstract protected void failed();

    /**
     * Method is called when accepted responder reports DONE
     * Then the protocol is terminated
     */
    abstract protected void done();

    private void initProtocol() {

        messagehandler = new ProtocolMessageHandler(this, session) {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                processMessage(message, content);
            }
        };
        communicator.addMessageHandler(messagehandler);
        ProtocolContent content = new ProtocolContent(this, Performative.CALL_FOR_PROPOSAL, contentData, session);
        Message message = communicator.createMessage(content);
        message.addReceivers(participantAddress);
        active = true;
        communicator.sendMessage(message);
    }

    private String generateSession() {
        //todo: is this correct?
        return "" + this.hashCode();
    }

    private void processMessage(Message message, ProtocolContent content) {
        switch (content.getPerformative()) {
            case REFUSE:
                pendingParticipants.remove(message.getSender());
                participantAddress.remove(message.getSender());
                checkAnswers();
                break;
            case PROPOSE:
                responses.put(message.getSender(), content.getData());
                messages.put(message.getSender(), message);
                pendingParticipants.remove(message.getSender());
                checkAnswers();
                break;
            case FAILURE:
                failed();
                active = false;
                communicator.removeMessageHandler(messagehandler);
                break;
            case DONE:
                done();
                active = false;
                communicator.removeMessageHandler(messagehandler);
                break;
            default:
        }
    }

    private void checkAnswers() {
        if (pendingParticipants.isEmpty()) {
            winner = evaluateReplies(responses);
            if (winner != null) {
                participantAddress.remove(winner);
                Message msg = createReply(winner, Performative.ACCEPT_PROPOSAL);
                communicator.sendMessage(msg);
            }
            for (String participant : participantAddress) {
                Message msg = createReply(participant, Performative.REJECT_PROPOSAL);
                communicator.sendMessage(msg);
            }
        }
    }

    private Message createReply(String address, Performative performative) {
        return communicator.createReply(messages.get(address), new ProtocolContent(this, performative, null, session));
    }
}
