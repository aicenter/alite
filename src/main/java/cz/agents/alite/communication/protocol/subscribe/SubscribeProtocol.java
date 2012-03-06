package cz.agents.alite.communication.protocol.subscribe;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.protocol.DefaultProtocol;

/**
 * Subscribe protocol wrapper.

 *
 * @author Jiri Vokrinek
 */
public class SubscribeProtocol extends DefaultProtocol {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4930724114099793345L;
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
