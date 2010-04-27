package cz.agents.alite.cross.protocol;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.directory.DirectoryFacilitator;
import cz.agents.alite.communication.protocol.cnp.CnpInitiator;
import java.util.Set;

/**
 *
 * @author vokrinek
 */
public abstract class DirectoredCnpInitiator extends CnpInitiator {

    public DirectoredCnpInitiator(Communicator communicator, String name, Object contentData, Set<String> participantAddress) {
        super(communicator, name, contentData, participantAddress);
    }

    /**
     *
     * @param communicator
     * @param name      Custom protocol name to differentiate several CNPs
     * @param contentData
     */
    public DirectoredCnpInitiator(Communicator communicator, String name, Object contentData) {
        this(communicator, name, contentData, getCorrespondingAddresses(name));
    }

    /**
     *
     * @return Set of addresses registered to serviceType that corresponds to protocol name
     */
    public static Set<String> getCorrespondingAddresses(String name) {
        return DirectoryFacilitator.getAddresses(buildName(name));
    }
}
