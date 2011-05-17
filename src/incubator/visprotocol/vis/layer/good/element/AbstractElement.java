package incubator.visprotocol.vis.layer.good.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.Typer;
import incubator.visprotocol.vis.layer.FilterStorage;

/**
 * @author Ondrej Milenovsky
 * */
public abstract class AbstractElement {

    /** converts self to element, excludes same parameters as in last element */
    public abstract Element createElement(Element lastElement, String name, FilterStorage filter);

    public abstract String getType();

    /** sets parameter to element if not filtred */
    protected <C> void setParameter(Element e, Typer<C> typer, C value, FilterStorage filter) {
        if ((filter == null) || filter.typeHasParam(e.getType(), typer.id)) {
            e.setParameter(typer, value);
        }
    }

    /** sets parameter to element if not filtred */
    protected void setParameter(Element e, String paramId, Object value, FilterStorage filter) {
        if ((filter == null) || filter.typeHasParam(e.getType(), paramId)) {
            e.setParameter(paramId, value);
        }
    }

    /** sets parameter if not already set */
    protected <C> boolean setElementParameter(Element e, Element last, Typer<C> param, C value,
            FilterStorage filter) {
        return setElementParameter(e, last, param.toString(), value, filter);
    }

    /** sets parameter if not already set */
    protected boolean setElementParameter(Element e, Element last, String param, Object value,
            FilterStorage filter) {
        if (value == null) {
            return false;
        }
        if ((last == null) || !last.parameterEqual(param, value)) {
            setParameter(e, param, value, filter);
            return true;
        }
        return false;
    }

    public AbstractElement copy() {
        throw new RuntimeException("Not implemented");
    }

}
