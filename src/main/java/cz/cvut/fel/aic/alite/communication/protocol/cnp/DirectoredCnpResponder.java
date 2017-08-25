package cz.cvut.fel.aic.alite.communication.protocol.cnp;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.protocol.cnp.CnpResponder;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;

/**
 *
 * @author Jiri Vokrinek
 */
public abstract class DirectoredCnpResponder extends CnpResponder {

    /**
     *
     * @param communicator
     * @param directory
     * @param name
     */
    public DirectoredCnpResponder(Communicator communicator, CapabilityRegister directory, String name) {
        super(communicator, name);
        directory.register(communicator.getAddress(), getName());
    }
}
