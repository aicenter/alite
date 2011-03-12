package incubator.visprotocol.structprocessor;

import javax.annotation.processing.Processor;

public interface DemuxProcessor extends Processor {
    public void addProcessor(StructProcessor pr);

    public void removeProcessor(StructProcessor pr);

}
