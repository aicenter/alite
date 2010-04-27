package cz.agents.alite.cross.protocol;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.directory.DirectoryFacilitator;
import cz.agents.alite.communication.protocol.cnp.CnpResponder;

/**
 *
 * @author vokrinek
 */
public abstract class DirectoredCnpResponder extends CnpResponder {

    public DirectoredCnpResponder(Communicator communicator, String name) {
        super(communicator, name);
        DirectoryFacilitator.register(communicator.getAddress(), getName());
    }
}
