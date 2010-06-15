package cz.agents.alite.communication.directory;

import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.common.capability.CapabilityRegisterImpl;

/**
 *
 * @author vokrinek
 */
public class DirectoryFacilitatorSingleton extends CapabilityRegisterImpl {

    private static final DirectoryFacilitatorSingleton instance = new DirectoryFacilitatorSingleton();

    static public CapabilityRegister getInstance() {
        return instance;
    }
}
