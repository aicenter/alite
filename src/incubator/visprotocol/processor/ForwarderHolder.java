package incubator.visprotocol.processor;

/**
 * @author Ondrej Milenovsky
 * */
public interface ForwarderHolder extends Forwarder {

    public void addForwarder(Forwarder fw);

    public void removeForwarder(Forwarder fw);
}
