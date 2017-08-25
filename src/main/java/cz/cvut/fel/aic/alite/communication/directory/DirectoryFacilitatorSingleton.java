package cz.cvut.fel.aic.alite.communication.directory;

import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegisterImpl;

/**
 * Singleton for encapsulation of @CapabilityRegisterImpl
 * 
 * @author Jiri Vokrinek
 */
public class DirectoryFacilitatorSingleton extends CapabilityRegisterImpl {

    private static final DirectoryFacilitatorSingleton instance = new DirectoryFacilitatorSingleton();

    static public CapabilityRegister getInstance() {
        return instance;
    }
}
