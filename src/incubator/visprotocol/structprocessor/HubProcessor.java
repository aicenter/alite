package incubator.visprotocol.structprocessor;

public interface HubProcessor extends StructProcessor {

    public void addProcessor(StructProcessor pr);

    public void removeProcessor(StructProcessor pr);
}
