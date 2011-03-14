package incubator.visprotocol.structure;

import incubator.visprotocol.structure.key.Typer;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Leaf element in structure. Type is used to determine painter. Change flag is used to create or
 * delete associated vis object. Must have some type.
 * 
 * @author Ondrej Milenovsky
 * */
public class Element implements Serializable {

    private static final long serialVersionUID = -2263640210556341841L;

    private final String id;
    private final String type;
    private final Map<String, Object> parameters;

    /** copies only id and type */
    public Element(Element e) {
        this(e.getId(), e.getType());
    }

    public Element(String id, String type) {
        this.id = id;
        this.type = type;
        parameters = new HashMap<String, Object>(2);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean containsParameter(Object id) {
        return parameters.containsKey(id.toString());
    }

    public Object getParameter(Object id) {
        return parameters.get(id.toString());
    }

    // TODO warnings
    /** returns parameter, if not contains, returns null */
    @SuppressWarnings("unchecked")
    public <C> C getParameter(Object id, Class<C> clazz) {
        return (C) parameters.get(id.toString());
    }

    @SuppressWarnings("unchecked")
    public <C> C getParameter(Typer<C> typer) {
        return (C) parameters.get(typer.id);
    }

    public void setParameter(String id, Object value) {
        parameters.put(id, value);
    }

    public <C> void setParameter(Typer<C> typer, C value) {
        parameters.put(typer.id, value);
    }

    public Object removeParameter(Object id) {
        return parameters.remove(id.toString());
    }

    /** returns true only if e.containsParam(id) and e.id == value */
    public boolean parameterEqual(String id, Object value) {
        return parameters.containsKey(id) && parameters.get(id.toString()).equals(value);
    }

    /** returns true only if e.containsParam(id) and e.id == value */
    public <C> boolean parameterEqual(Typer<C> id, C value) {
        return parameters.containsKey(id.toString()) && parameters.get(id.toString()).equals(value);
    }

    /** if the parameter is contained and is null */
    public boolean parameterNull(Object id) {
        return parameterEqual(id.toString(), null);
    }

    public Collection<String> getParamIds() {
        return parameters.keySet();
    }

    public Collection<Object> getParameters() {
        return parameters.values();
    }

    public void updateParams(Element e) {
        parameters.putAll(e.parameters);
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
    }

    /** clears all parameters */
    public void clear() {
        parameters.clear();
    }

    public void clearParams() {
        parameters.clear();
    }

    /** Makes deep copy of the folder, not of element parameters! */
    public Element deepCopy() {
        Element e = new Element(id, type);
        e.parameters.putAll(parameters);
        return e;
    }

    public boolean equalsDeep(Object obj) {
        if (!equals(obj)) {
            return false;
        }
        return parameters.equals(((Element) obj).parameters);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Element)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Element e2 = (Element) obj;
        return id.equals(e2.getId()) && type.equals(e2.getType());
    }

    public String print() {
        return print(0);
    }

    public String print(int spaces) {
        String s = getSpaces(spaces);
        String s2 = "  ";
        String ret = s + id + "\n";
        if (type != null) {
            ret += s + type + "\n";
        }
        if (!getParameters().isEmpty()) {
            ret += s + "---params---\n";
            for (String p : getParamIds()) {
                ret += s + s2 + p + " = " + getParameter(p) + "\n";
            }
        }
        return ret;
    }

    public String getSpaces(int spaces) {
        String s = "";
        for (int i = 0; i < spaces; i++) {
            if ((spaces - i) % 2 == 0) {
                s += "|";
            } else {
                s += " ";
            }
        }
        return s;
    }
}
