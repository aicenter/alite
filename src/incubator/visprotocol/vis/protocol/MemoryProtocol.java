package incubator.visprotocol.vis.protocol;

import java.util.HashMap;
import java.util.Map;

public class MemoryProtocol implements Protocol {

    private Map<String, Object> elementsById;

    public MemoryProtocol() {
        elementsById = new HashMap<String, Object>();
    }

    @Override
    public void push(String id, Object elements) {
        elementsById.put(id, elements);
    }

    @Override
    public Object pull(String id) {
        return elementsById.get(id);
    }

    @Override
    public void nextStep() {
        elementsById.clear();
    }

}
