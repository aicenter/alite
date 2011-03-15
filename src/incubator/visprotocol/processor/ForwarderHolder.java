package incubator.visprotocol.processor;

public interface ForwarderHolder extends Forwarder {

    public void addForwarder(Forwarder fw);

    public void removeForwarder(Forwarder fw);
}
