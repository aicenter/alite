package cz.agents.alite.communication.protocol;

/**
 *
 * @author Jiri Vokrinek
 */
public interface Protocol {

    //TODO: temporary constant - remove
//    public static final Protocol TEST = new Protocol() {
//
//        @Override
//        public String getName() {
//            return "TEST";
//        }
//
//        @Override
//        public boolean equals(Protocol protocol) {
//            return getName().equals(protocol.getName());
//        }
//    };

    //TODO: add state controll mechanisms

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
