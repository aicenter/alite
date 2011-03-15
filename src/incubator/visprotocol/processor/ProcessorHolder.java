package incubator.visprotocol.processor;

/**
 * @author Ondrej Milenovsky
 * */
public interface ProcessorHolder extends StructProcessor {

    public void addProcessor(StructProcessor pr);

    public void removeProcessor(StructProcessor pr);
}
