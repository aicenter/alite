package incubator.visprotocol.processor;

public interface ProcessorHolder extends StructProcessor {

    public void addProcessor(StructProcessor pr);

    public void removeProcessor(StructProcessor pr);
}
