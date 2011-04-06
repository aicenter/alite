package incubator.visprotocol.vis.layer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.structure.key.Typer;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.element.AbstractElement;

import java.util.Arrays;
import java.util.List;

/**
 * Proxy layer using filter, methods are forwarded, so you call hasType(type) instead
 * filter.hasType(type) + some useful methods. Before creating an element, always check
 * hasParam(type). Never call element.setParameter(param, value), call setParameter(element, param,
 * value). Before creating an element, always check hasType(type).
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class TypedLayer implements StructProcessor {

    private FilterStorage filter;
    private Folder currentFolder;
    private Structure struct = new Structure();

    public TypedLayer(FilterStorage filter) {
        this.filter = filter;
    }

    public void setFilter(FilterStorage filter) {
        this.filter = filter;
    }

    public FilterStorage getFilter() {
        return filter;
    }

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

    protected void changeFolder(String[] path) {
        changeFolder(StructUtils.getFolderOfStruct(struct, Arrays.asList(path)));
    }

    protected void changeFolder(Folder folder) {
        currentFolder = folder;
    }

    /** current folder will be once generated and then never changed */
    protected void setStaticFolder() {
        currentFolder.setParameter(CommonKeys.NOT_CHANGE, true);
    }

    // ELEMENTS //////////////////

    /** makes swallow copy of the element so it can be used again */
    protected Element addElement(String name, AbstractElement element) {
        Element last = StructUtils.getLastElement(currentFolder, PointKeys.TYPE);
        Element e = element.createElement(last, name, filter);
        currentFolder.addElement(e);
        return e;
    }

}
