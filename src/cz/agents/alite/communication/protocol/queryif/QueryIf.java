package cz.agents.alite.communication.protocol.queryif;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.protocol.DefaultProtocol;

/**
 * Qerry-if protocol wrapper.
 *
 * @author Jiri Vokrinek
 */
public class QueryIf extends DefaultProtocol {

    static final String QUERY_IF_PROTOCOL_NAME = "QUERY_IFF_PROTOCOL";

    /**
     * 
     * @param communicator
     * @param name
     */
    public QueryIf(Communicator communicator, String name) {
        super(communicator, QUERY_IF_PROTOCOL_NAME + ": " + name);
    }
}
