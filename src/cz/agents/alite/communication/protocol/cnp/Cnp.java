package cz.agents.alite.communication.protocol.cnp;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.protocol.Protocol;

/**
 *
 * @author vokrinek
 */
public class Cnp implements Protocol {

    static final String CNP_PROTOCOL_NAME = "CONTRACT_NET_PROTOCOL";
    final Communicator communicator;
    private final String name;

    public Cnp(Communicator communicator, String name) {
        this.communicator = communicator;
        this.name = name;
    }

    @Override
    public String getName() {
        return buildName(name);
    }

    protected static String buildName(String name) {
        return CNP_PROTOCOL_NAME + ": " + name;
    }

    @Override
    public boolean equals(Protocol protocol) {
        if (protocol == null) {
            return false;
        }

        if (getName().equals(((Protocol) protocol).getName())) {
            return true;
        } else {
            return false;
        }

    }
}
