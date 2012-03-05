package cz.agents.alite.communication.protocol;

import java.io.Serializable;

/**
 *
 * @author Jiri Vokrinek
 */
public interface Protocol extends Serializable {

    /**
     * Returns name of the protocol. It should be buid as PROTOCOL_PREFFIX + protocol instance name
     *
     * @return name of the protocol
     */
    public String getName();


    /**
     * Comparator for protocols.
     *
     * @param protocol
     * @return true if protocols are equal
     */
    public boolean equals(Protocol protocol);
}
