package incubator.visprotocol.vis.layer.simple;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.Typer;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.VisLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract layer for simple layers, all specified layers extend this. Layers that generate static
 * data should override isStaticLayer! Non static layers should generate names! Asynchronous access
 * will cause crash!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class SimpleAbstractLayer implements VisLayer {

    private static int layerCount = 0;
    
    private boolean elementNotEnded = false;

    private FilterStorage filter;

    private final HashMap<String, Element> lastElements;

    private List<String> path = new ArrayList<String>(2);

    private Folder currentFolder;
    private int elementCount;

    public SimpleAbstractLayer() {
        this("Layer " + ++layerCount);
    }

    public SimpleAbstractLayer(String name) {
        lastElements = new HashMap<String, Element>();
        path.add("World");
        path.add(name);
    }

    public SimpleAbstractLayer(String... newPath) {
        this();
        String root = path.get(0);
        path = new ArrayList<String>(newPath.length + 1);
        path.add(root);
        for (int i = 0; i < newPath.length; i++) {
            path.add(newPath[i]);
        }
    }

    @Override
    public final void setFilter(FilterStorage filter) {
        this.filter = filter;
    }

    @Override
    public final void setRoot(String root) {
        path.set(0, root);
    }

    @Override
    public final String getId() {
        return StructUtils.printPath(path);
    }

    /** if this layer generates always same elements (faster than not static), default is false */
    protected boolean isStaticLayer() {
        return false;
    }

    @Override
    public final Structure pull() {
        // TODO static maybe will be deleted when deeper path
        Structure struct;
        if (isStaticLayer()) {
            struct = new Structure(CommonKeys.STRUCT_PART_STATIC);
        } else {
            struct = new Structure(CommonKeys.STRUCT_PART);
        }
        currentFolder = StructUtils.getFolderOfStruct(struct, path);
        if (isStaticLayer()) {
            currentFolder.setParameter(CommonKeys.NOT_CHANGE, true);
        }
        elementCount = 0;
        generateFrame();
        lastElements.clear();
        return struct;
    }

    /** fill current folder with elements using addElement() */
    protected abstract void generateFrame();

    /** create names for elements */
    protected Iterable<String> getNames() {
        return null;
    }

    /** if elements change width when zooming, default is true */
    protected boolean isConstantSize() {
        return true;
    }

    /** generated name if names not specified */
    protected abstract String getDefaultElementName();

    // FILTER /////////////////////////

    private final boolean hasType(String type) {
        if (filter == null) {
            return true;
        }
        return filter.hasType(type);
    }

    /** sets parameter to element if not filtred */
    private final void setParameter(Element e, String paramId, Object value) {
        if ((filter == null) || filter.typeHasParam(e.getType(), paramId)) {
            e.setParameter(paramId, value);
        }
    }

    /** sets parameter to element if not filtred */
    private final <C> void setParameter(Element e, Typer<C> typer, C value) {
        if ((filter == null) || filter.typeHasParam(e.getType(), typer.id)) {
            e.setParameter(typer, value);
        }
    }

    // ELEMENTS //////////////////

    private final void addElement(Element e) {
        String type = e.getType();
        if (!hasType(type)) {
            return;
        }
        if (!lastElements.containsKey(type)) {
            lastElements.put(type, new Element("", type));
        }
        Element last = lastElements.get(type);
        // TODO remove same params
        last.updateParams(e);
        currentFolder.addElement(e);
    }

    /**
     * Create element with specified name (can be null, so some will be generated), specified type.
     * Usage: addElement("a", "type").with(color, blue).with(size, 10)...end(); Do not forget the
     * END !!!
     */
    protected final Param addElement(String name, String type) {
        if (name == null) {
            if (!isStaticLayer()) {
                System.err.println("Non static layer should have specified names for all elements");
            }
            name = getDefaultElementName() + ++elementCount;
        }
        Element e = new Element(name, type);
        return new Param(e);
    }

    // OTHER ////////////

    /** converts one object to Iterable containing that object */
    @SuppressWarnings("unchecked")
    protected final <C> Iterable<C> oneItem(C item) {
        return (List<C>) Arrays.asList(item);
    }

    protected class Param {
        private final Element e;

        public Param(Element element) {
            if (elementNotEnded) {
                throw new RuntimeException("Last element was not ended !");
            }
            elementNotEnded = true;
            e = element;
        }

        public <C> Param with(Typer<C> typer, C value) {
            setParameter(e, typer, value);
            return this;
        }

        public Param with(String paramId, Object value) {
            setParameter(e, paramId, value);
            return this;
        }

        public void end() {
            elementNotEnded = false;
            addElement(e);
        }

    }

}
