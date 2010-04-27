package cz.agents.alite.communication.protocol;

/**
 *
 * @author vokrinek
 */
public interface Protocol {

    //TODO: temporary constant - remove
    public static final Protocol TEST = new Protocol() {

        @Override
        public String getName() {
            return "TEST";
        }

        @Override
        public boolean equals(Protocol protocol) {
            return getName().equals(protocol.getName());
        }
    };

    //TODO: add state controll mechanisms

    public String getName();

    public boolean equals(Protocol protocol);
}
