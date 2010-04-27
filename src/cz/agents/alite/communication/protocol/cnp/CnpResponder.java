package cz.agents.alite.communication.protocol.cnp;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.MessageHandler;
import cz.agents.alite.communication.protocol.Performative;
import cz.agents.alite.communication.protocol.ProtocolContent;
import cz.agents.alite.communication.protocol.ProtocolMessageHandler;

/**
 *
 * Responder part of the basic Contract-net-protocol.
 * When created, it registeres protocol handler to the given communicator.
 * The CnpInitiator maintains all the active CNPs of the given type.
 * To deactivate the protocol, call the appropriate method.
 *
 * @author vokrinek
 */
//TODO: state machine
public abstract class CnpResponder extends Cnp {

    //private final LinkedHashMap<String, Object> proposals = new LinkedHashMap<String, Object>();
    private final LinkedHashMap<String, Message> cnpRequests = new LinkedHashMap<String, Message>();
    private final LinkedHashSet<String> openCnp = new LinkedHashSet<String>();
    private MessageHandler messagehandler;

    /**
     *
     * @param communicator
     * @param name      Custom protocol name to differentiate several CNPs
     * @param contentData
     */
    public CnpResponder(Communicator communicator, String name) {
        super(communicator, name);
        initProtocol();
    }

    /**
     * Prepare proposal for the given request identified by the session
     *
     * @param request
     * @param session
     * @return Object representing the proposal; null to REFUSE
     */
    abstract protected Object prepareProposal(Object request, String session);

    /**
     * Accepts proposal with given session identification.
     *
     * @param session
     */
    abstract protected void proposalAccepted(String session);

    /**
     * Rejects proposal with given session identification.
     *
     * @param session
     */
    abstract protected void proposalRejected(String session);

    /**
     * The accepred proposal with given session identification is cancelled.
     *
     * @param session
     */
    abstract protected void canceled(String session);

    public void informDone(String session){
        Message message = cnpRequests.remove(session);
        if (message != null) {
            communicator.sendMessage(createReply(message, Performative.DONE, null, session));
        } else {
            // TODO: logger
            System.out.println("Method CnpResponder.informDone do not know session '" + session + "'!");
        }
    }

    public void informFail(String session){
        Message message = cnpRequests.remove(session);
        if (message != null) {
            communicator.sendMessage(createReply(message, Performative.FAILURE, null, session));
        } else {
            // TODO: logger
            System.out.println("Method CnpResponder.informFail do not know session '" + session + "'!");
        }
    }

    /**
     * Check of the protocol activity.
     *
     * @return true if the protocol session is running; false when finnished.
     */
    public boolean isActive(String session) {
        return cnpRequests.containsKey(session);
    }

    private void initProtocol() {

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
            case CANCEL:
                if (openCnp.contains(session)) {
                    openCnp.remove(session);
                    cnpRequests.remove(session);
                    proposalRejected(session);
                } else {
                    canceled(session);
                }
                break;
            case ACCEPT_PROPOSAL:
                openCnp.remove(session);
                proposalAccepted(session);
                break;
            case REJECT_PROPOSAL:
                openCnp.remove(session);
                cnpRequests.remove(session);
                proposalRejected(session);
                break;
            case CALL_FOR_PROPOSAL:
                Object response = prepareProposal(body, session);
                Message msg;
                if (response != null) {
                    openCnp.add(session);
                    cnpRequests.put(session, message);
                    msg = createReply(message, Performative.PROPOSE, response, session);
                } else {
                    msg = createReply(message, Performative.REFUSE, null, session);
                }
                communicator.sendMessage(msg);
                break;
            default:
        }
    }

    private Message createReply(Message message, Performative performative, Object body, String session) {
        return communicator.createReply(message, new ProtocolContent(this, performative, body, session));
    }

}
