package incubator.visprotocol.structure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Leaf element in structure. Type is used to determine painter. Change flag is
 * used to create or delete associated vis object. Must have some type.
 * 
 * @author Ondrej Milenovsky
 * */
public class Element implements Serializable {

    private static final long serialVersionUID = -2263640210556341841L;

    private final String id;
    private final String type;
    private final Map<String, Object> parameters;
    private ChangeFlag changeFlag;

    public Element(String id, String type) {
        this.id = id;
        this.type = type;
        changeFlag = ChangeFlag.NONE;
        parameters = new HashMap<String, Object>(2);
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public ChangeFlag getChangeFlag() {
        return changeFlag;
    }

    public void setChangeFlag(ChangeFlag changeFlag) {
        this.changeFlag = changeFlag;
    }

    public boolean containsParameter(String id) {
        return parameters.containsKey(id);
    }

    /** returns parameter, if not contains, returns null */
    @SuppressWarnings("unchecked")
    public <C extends Object> C getParameter(String id, Class<C> clazz) {
        return (C) parameters.get(id);
    }

    public void setParameter(String id, Object value) {
        updated();
        parameters.put(id, value);
    }

    public Set<String> getParamIds() {
        return parameters.keySet();
    }

    public Collection<Object> getParameters() {
        return parameters.values();
    }

    protected void updated() {
        if ((changeFlag == ChangeFlag.NONE) || (changeFlag == ChangeFlag.NO_CHANGE)) {
            changeFlag = ChangeFlag.UPDATE;
        }
    }

}
