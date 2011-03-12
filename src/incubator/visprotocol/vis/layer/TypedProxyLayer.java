package incubator.visprotocol.vis.layer;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.Typer;

import java.util.Map;
import java.util.Set;

/**
 * Proxy layer with map of used types with params. Before creating an element, always check
 * hasParam(type). Never call element.setParameter(param, value), call setParameter(element, param,
 * value).
 * 
 * @author Ondrej Milenovsky
 * */
// TODO folder filter
public abstract class TypedProxyLayer implements ProxyLayer {

    private final Map<String, Set<String>> types;

    public TypedProxyLayer(Map<String, Set<String>> types) {
        this.types = types;
    }

    protected boolean hasType(String type) {
        return types.containsKey(type);
    }

    protected boolean typeHasParam(String type, String param) {
        return types.get(type).contains(param);
    }

    protected boolean typeHasParam(String type, Typer<?> param) {
        return types.get(type).contains(param.paramId);
    }

    protected void setParameter(Element e, String paramId, Object value) {
        if (typeHasParam(e.getType(), paramId)) {
            e.setParameter(paramId, value);
        }
    }

    protected <C> void setParameter(Element e, Typer<C> typer, C value) {
        if (typeHasParam(e.getType(), typer.paramId)) {
            e.setParameter(typer, value);
        }
    }

}
