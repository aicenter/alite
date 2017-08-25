package cz.cvut.fel.aic.alite.communication.protocol.subscribe;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.protocol.DefaultProtocol;

/**
 * Subscribe protocol wrapper.

 *
 * @author Jiri Vokrinek
 */
public class SubscribeProtocol extends DefaultProtocol {

    static final String SUBSCRIBE_PROTOCOL_NAME = "SUBSCRIBE_PROTOCOL";

    /**
     *
     * @param communicator
     * @param name
     */
    public SubscribeProtocol(Communicator communicator, String name) {
        super(communicator, SUBSCRIBE_PROTOCOL_NAME + ": " + name);
    }
}
