package incubator.visprotocol.vis.layer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.Typer;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.element.AbstractElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Abstract layer to generate frames. In every frame the layer contains only empty structure, fill
 * it.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class AbstractLayer implements StructProcessor {

    private FilterStorage filter;

    private Folder currentFolder;
    private boolean staticLayer;

    private Structure struct = new Structure();

    private HashMap<String, Element> lastElements;

    /** creates layer with dynamoc elements */
    public AbstractLayer(FilterStorage filter) {
        this(filter, false);
    }

    /** layer can be set as static, so it will be generated only once */
    public AbstractLayer(FilterStorage filter, boolean staticLayer) {
        this.filter = filter;
        this.staticLayer = staticLayer;
        lastElements = new HashMap<String, Element>();
    }

    public FilterStorage getFilter() {
        return filter;
    }

    public Structure getStruct() {
        return struct;
    }

    @Override
    public Structure pull() {
        if (staticLayer) {
            struct = new Structure(CommonKeys.STRUCT_PART_STATIC);
        } else {
            struct = new Structure(CommonKeys.STRUCT_PART);
        }
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
        return filter.hasType(type);
    }

    protected boolean typeHasParam(String type, String param) {
        return filter.typeHasParam(type, param);
    }

    protected boolean typeHasParam(String type, Typer<?> param) {
        return filter.typeHasParam(type, param);
    }

    /** sets parameter to element if not filtred */
    protected void setParameter(Element e, String paramId, Object value) {
        if (filter.typeHasParam(e.getType(), paramId)) {
            e.setParameter(paramId, value);
        }
    }

    /** sets parameter to element if not filtred */
    protected <C> void setParameter(Element e, Typer<C> typer, C value) {
        if (filter.typeHasParam(e.getType(), typer.id)) {
            e.setParameter(typer, value);
        }
    }

    // FOLDERS //////////////////

    protected void changeFolder(String path) {
        changeFolder(StructUtils.parsePath(path));
    }

    protected void changeFolder(List<String> path) {
        changeFolder(StructUtils.getFolderOfStruct(struct, path));
    }

    protected void changeFolder(String... path) {
        changeFolder(StructUtils.getFolderOfStruct(struct, Arrays.asList(path)));
    }

    private void changeFolder(Folder folder) {
        currentFolder = folder;
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

    /** sets timestamp to structure */
    protected void setTime(long time) {
        struct.setTimeStamp(time);
    }

}
