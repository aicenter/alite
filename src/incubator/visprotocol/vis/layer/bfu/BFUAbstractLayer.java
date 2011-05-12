package incubator.visprotocol.vis.layer.bfu;

import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.VisLayer;
import incubator.visprotocol.vis.layer.element.AbstractElement;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Abstract layer for BFU layers
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class BFUAbstractLayer implements VisLayer {

    private final Layer layer;
    private int count;

    public BFUAbstractLayer(String name, boolean staticLayer) {
        layer = new Layer(name, staticLayer);
    }

    /** fill all elements to draw */
    protected abstract Iterable<? extends AbstractElement> getElements();

    /** fill names to all elements in same order */
    protected Iterable<String> getNames() {
        return null;
    }

    private Iterable<String> generateNames(Iterable<? extends AbstractElement> elements) {
        ArrayList<String> names = new ArrayList<String>();
        for (AbstractElement element : elements) {
            names.add(generateName(element));
        }
        return names;
    }

    private String generateName(AbstractElement element) {
        return element.getType() + " " + count++;
    }

    private void resetCounter() {
        count = 0;
    }

    @Override
    public final Structure pull() {
        return layer.pull();
    }
    
    @Override
    public final String getName() {
        return layer.getName();
    }
    
    @Override
    public final void setFilter(FilterStorage filter) {
        layer.setFilter(filter);
    }
    
    @Override
    public final void setRoot(String root) {
        layer.setRoot(root);
    }
    
    private class Layer extends AbstractLayer {
        public Layer(String name, boolean staticLayer) {
            super(name, staticLayer);
        }

        @Override
        protected void generateFrame() {
            resetCounter();
            Iterable<? extends AbstractElement> elements = getElements();
            Iterable<String> names = getNames();
            if (names == null) {
                names = generateNames(elements);
            }

            Iterator<? extends AbstractElement> itElements = elements.iterator();
            Iterator<String> itNames = names.iterator();
            while (itElements.hasNext()) {
                String name;
                AbstractElement element = itElements.next();
                if (!itNames.hasNext()) {
                    name = generateName(element);
                    System.err.println("Names.size < elements.size, generating name: " + name);
                } else {
                    name = itNames.next();
                }
                addElement(name, element);
            }
        }
    }

}
