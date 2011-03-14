package incubator.visprotocol.processor;

public interface HubProcessor extends StructProcessor {

    public void addProcessor(StructProcessor pr);

    public void removeProcessor(StructProcessor pr);
}
