package cz.agents.alite.communication.channel;

import java.util.Set;

public class UnknownReceiversException extends CommunicationChannelException {

    private static final long serialVersionUID = 8896341303688182411L;

    private final Set<String> unknownReceivers;

    public UnknownReceiversException(Set<String> unknownReceivers) {
        this.unknownReceivers = unknownReceivers;
    }

    public Set<String> getUnknownReceivers() {
        return unknownReceivers;
    }

}
