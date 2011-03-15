package incubator.visprotocol.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Calls forward() on all forwarders in order they were added.
 * 
 * @author Ondrej Milenovsky
 * */
public class MultiForwarder implements Forwarder {

    private final List<Forwarder> forwarders;

    public MultiForwarder() {
        forwarders = new ArrayList<Forwarder>(2);
    }

    public MultiForwarder(Forwarder... forwarders) {
        this.forwarders = Arrays.asList(forwarders);
    }

    public MultiForwarder(List<Forwarder> forwarders) {
        this.forwarders = forwarders;
    }

    public void addForwarder(Forwarder fw) {
        forwarders.add(fw);
    }

    public void removeForwarder(Forwarder fw) {
        int i = forwarders.indexOf(fw);
        if (i >= 0) {
            forwarders.remove(i);
        }
    }

    public boolean isEmpty() {
        return forwarders.isEmpty();
    }

    public int size() {
        return forwarders.size();
    }

    public Forwarder getForwarder(int i) {
        return forwarders.get(i);
    }

    public Collection<Forwarder> getForwarders() {
        return forwarders;
    }

    @Override
    public void forward() {
        for (Forwarder fw : getForwarders()) {
            fw.forward();
        }
    }
}
