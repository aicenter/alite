package cz.cvut.fel.aic.alite.communication.channel;

import cz.cvut.fel.aic.alite.communication.CommunicationReceiver;

/**
 *
 * @author Jiri Vokrinek
 */
public abstract class DefaultCommunicationChannelBroadcast extends DefaultCommunicationChannel implements CommunicationChannelBroadcast {

    /**
     *
     * @param communicator
     */
    public DefaultCommunicationChannelBroadcast(CommunicationReceiver communicator) throws DuplicateReceiverAddressException {
        super(communicator);
        if (BROADCAST_ADDRESS.equals(communicator.getAddress())) {
            throw new DuplicateReceiverAddressException(communicator.getAddress());
        }

    }
}
