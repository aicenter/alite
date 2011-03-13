package incubator.visprotocol.protocol;

import java.util.LinkedList;
import java.util.Queue;

import incubator.visprotocol.structure.Structure;

/**
 * just queue
 * 
 * @author Ondrej Milenovsky
 * */
public class QueueProtocol implements Protocol {

    private final Queue<Structure> queue;

    public QueueProtocol() {
        queue = new LinkedList<Structure>();
    }

    @Override
    public void push(Structure struct) {
        queue.add(struct);
    }

    @Override
    public Structure pull() {
        return queue.poll();
    }

}
