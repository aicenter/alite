package incubator.visprotocol.vis.protocol;

import java.util.HashMap;
import java.util.Map;

public class MemoryProtocol implements Protocol {

    private Map<Class<?>, Object> elementsByClass = new HashMap<Class<?>, Object>();

    @Override
    public <T> void push(T elements) {
        elementsByClass.put(elements.getClass(), elements);
    }

    @Override
    public <T> T pull(Class<T> clazz) {
        return clazz.cast(elementsByClass.get(clazz));
    }

    @Override
    public void nextStep() {
        elementsByClass.clear();
    }

}
