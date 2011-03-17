package incubator.visprotocol.protocol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;

/**
 * Implementation using queue.
 * 
 * @author Ondrej Milenovsky
 * */
public class QueueProtocol extends MultipleInputProcessor {

    private final Queue<Structure> queue;

    public QueueProtocol(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public QueueProtocol(List<StructProcessor> inputs) {
        super(inputs);
        queue = new LinkedList<Structure>();
    }

    public void push(Structure struct) {
        queue.add(struct);
    }

    @Override
    public Structure pull() {
        for (StructProcessor pr : getInputs()) {
            push(pr.pull());
        }
        return queue.poll();
    }

}
