package cz.agents.alite.directory;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author vokrinek
 */
public class DirectoryFacilitator {

    private static final HashMap<String, Set<String>> directory = new HashMap<String, Set<String>>();

    /**
     *
     * Registers communication address
     *
     * @param communicatorAddress
     * @param serviceType
     */
    static public void register(String communicatorAddress, String serviceType) {
        Set<String> recS = directory.get(serviceType);
        if (recS == null) {
            LinkedHashSet<String> page = new LinkedHashSet<String>();
            page.add(communicatorAddress);
            directory.put(serviceType, page);
        } else {
            recS.add(communicatorAddress);
        }
    }

    /**
     * 
     * @param serviceType
     * @return Set of all address records of the specified serviceType registered in the DirectoryFacilitator
     */
    static public Set<String> getAddresses(String serviceType) {
        Set<String> get = directory.get(serviceType);
        if (get == null) {
            return new LinkedHashSet<String>();
        }
        return new LinkedHashSet<String>(get);
    }

    /**
     *  Provides copy of the address directory.
     *
     * @return Set of all address records registered in the DirectoryFacilitator
     */
    static public Set<String> getAddresses() {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (Set<String> page : directory.values()) {
            result.addAll(page);
        }
        return result;
    }
}
