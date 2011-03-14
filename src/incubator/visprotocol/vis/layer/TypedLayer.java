package incubator.visprotocol.vis.layer;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.Typer;

/**
 * Proxy layer using filter, methods are forwarded, so you call hasType("type") instead
 * filter.hasType("type") + some useful methods. Before creating an element, always check
 * hasParam(type). Never call element.setParameter(param, value), call setParameter(element, param,
 * value).
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class TypedLayer implements StructProcessor {

    private TypeParamIdFilter filter;

    public TypedLayer(TypeParamIdFilter filter) {
        this.filter = filter;
    }

    public void setFilter(TypeParamIdFilter filter) {
        this.filter = filter;
    }

    public TypeParamIdFilter getFilter() {
        return filter;
    }

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
        if (filter.typeHasParam(e.getType(), typer.paramId)) {
            e.setParameter(typer, value);
        }
    }

    @Override
    @Deprecated
    public void push(Structure newPart) {
        throw new RuntimeException("No push");
    }

}
