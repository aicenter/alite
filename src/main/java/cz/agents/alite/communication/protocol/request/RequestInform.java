package cz.agents.alite.communication.protocol.request;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.protocol.DefaultProtocol;

/**
 * Request-inform protocol wrapper.
 *
 * @author Jiri Vokrinek
 */
public class RequestInform extends DefaultProtocol {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2442871114714805160L;
	static final String REQUEST_INFORM_PROTOCOL_NAME = "REQUEST_INFORM_PROTOCOL";

    /**
     * 
     * @param communicator
     * @param name
     */
    public RequestInform(Communicator communicator, String name) {
        super(communicator, REQUEST_INFORM_PROTOCOL_NAME + ": " + name);
    }
}
