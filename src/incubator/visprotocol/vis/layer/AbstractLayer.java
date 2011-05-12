package incubator.visprotocol.vis.layer;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.Typer;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.element.AbstractElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract layer to generate frames. In every frame the layer contains only empty structure, fill
 * it.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class AbstractLayer implements VisLayer {

    private FilterStorage filter;

    private Folder currentFolder;
    private boolean staticLayer;

    private Structure struct = new Structure();

    private HashMap<String, Element> lastElements;

    private List<String> path = new ArrayList<String>(2);

    /** creates layer with dynamic elements */
    public AbstractLayer(String name) {
        this(name, false);
    }

    /** layer can be set as static, so it will be generated only once */
    public AbstractLayer(String name, boolean staticLayer) {
        this.staticLayer = staticLayer;
        lastElements = new HashMap<String, Element>();
        path.add("World");
        path.add(name);
    }

    /** sets path of folder, discards name */
    public void setPath(String... newPath) {
        String root = path.get(0);
        path = new ArrayList<String>(newPath.length + 1);
        path.add(root);
        for (int i = 0; i < newPath.length; i++) {
            path.add(newPath[i]);
        }
    }

    public FilterStorage getFilter() {
        return filter;
    }

    public void setFilter(FilterStorage filter) {
        this.filter = filter;
    }

    public Structure getStruct() {
        return struct;
    }

    public void setRoot(String root) {
        path.set(0, root);
    }

    public String getName() {
        return StructUtils.printPath(path);
    }

    @Override
    public Structure pull() {
        if (staticLayer) {
            struct = new Structure(CommonKeys.STRUCT_PART_STATIC);
        } else {
            struct = new Structure(CommonKeys.STRUCT_PART);
        }
        changeFolder(path);
        generateFrame();
        Structure ret = struct;
        clear();
        return ret;
    }

    private void clear() {
        struct = null;
        currentFolder = null;
        lastElements.clear();
    }

    protected abstract void generateFrame();

    // FILTER /////////////////////////

    protected boolean hasType(String type) {
        if (filter == null) {
            return true;
        }
        return filter.hasType(type);
    }

    protected boolean typeHasParam(String type, String param) {
        if (filter == null) {
            return true;
        }
        return filter.typeHasParam(type, param);
    }

    protected boolean typeHasParam(String type, Typer<?> param) {
        if (filter == null) {
            return true;
        }
        return filter.typeHasParam(type, param);
    }

    /** sets parameter to element if not filtred */
    protected void setParameter(Element e, String paramId, Object value) {
        if ((filter == null) || filter.typeHasParam(e.getType(), paramId)) {
            e.setParameter(paramId, value);
        }
    }

    /** sets parameter to element if not filtred */
    protected <C> void setParameter(Element e, Typer<C> typer, C value) {
        if ((filter == null) || filter.typeHasParam(e.getType(), typer.id)) {
            e.setParameter(typer, value);
        }
    }

    // FOLDERS //////////////////

    private void changeFolder(List<String> path) {
        currentFolder = StructUtils.getFolderOfStruct(struct, path);
        if (staticLayer) {
            currentFolder.setParameter(CommonKeys.NOT_CHANGE, true);
        }
    }

    // ELEMENTS //////////////////

    private void addElement(Element e) {
        Element last = lastElements.get(e.getType());
        last.updateParams(e);
        currentFolder.addElement(e);
    }

    /** makes swallow copy of the element so it can be used again */
    protected void addElement(String name, AbstractElement element) {
        String type = element.getType();
        if (!hasType(type)) {
            return;
        }
        if (!lastElements.containsKey(type)) {
            lastElements.put(type, new Element("", type));
        }
        Element last = lastElements.get(type);
        Element e = element.createElement(last, name, filter);
        addElement(e);
    }

}
