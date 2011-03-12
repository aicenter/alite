package incubator.visprotocol.vis.structure;

import incubator.visprotocol.vis.structure.key.Typer;

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

    public boolean containsParameter(String id) {
        return parameters.containsKey(id);
    }

    public Object getParameter(String id) {
        return parameters.get(id);
    }

    // TODO warnings
    /** returns parameter, if not contains, returns null */
    @SuppressWarnings("unchecked")
    public <C> C getParameter(String id, Class<C> clazz) {
        return (C) parameters.get(id);
    }
    
    @SuppressWarnings("unchecked")
    public <C> C getParameter(Typer<C> typer) {
        return (C)parameters.get(typer.paramId);
    }

    public void setParameter(String id, Object value) {
        parameters.put(id, value);
    }

    public Collection<String> getParamIds() {
        return parameters.keySet();
    }

    public Collection<Object> getParameters() {
        return parameters.values();
    }

    /**
     * elements must equal ! taken all parameters and change flag and updated current state
     */
    public void update(Element e) {
        if (!equals(e)) {
            throw new RuntimeException("Merging " + getId() + " and " + e.getId()
                    + " different elements");
        }
        parameters.putAll(e.parameters);
    }

    public boolean isEmpty() {
        return parameters.isEmpty();
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
