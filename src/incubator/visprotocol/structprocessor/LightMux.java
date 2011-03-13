package incubator.visprotocol.structprocessor;

import incubator.visprotocol.structure.Structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Pulles structures from all subprocessors and pushes to output, then pulls structure from the
 * output.
 * 
 * @author Ondrej Milenovsky
 * */
public class LightMux implements HubProcessor, Forwarder {

    private final List<StructProcessor> processors;

    private final StructProcessor output;

    public LightMux(StructProcessor output) {
        processors = new ArrayList<StructProcessor>();
        this.output = output;
    }

    @Override
    public void addProcessor(StructProcessor pr) {
        processors.add(pr);
    }

    @Override
    public void removeProcessor(StructProcessor pr) {
        int i = processors.indexOf(pr);
        if (i >= 0) {
            processors.remove(i);
        }
    }

    @Override
    public void push(Structure newPart) {
        output.push(newPart);
    }

    /** pull from all subprocessors, push to output, return output.pull() */
    @Override
    public Structure pull() {
        forward();
        return output.pull();
    }

    /** pushes from subprocessors to the output */
    @Override
    public void forward() {
        for (StructProcessor pr : processors) {
            output.push(pr.pull());
        }
    }

}
