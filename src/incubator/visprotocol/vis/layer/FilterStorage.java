package incubator.visprotocol.vis.layer;

import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.Typer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Filter for proxy layers. Filtres unused elements, parameters and ids. Filter can be empty and it
 * won't throw exceptions.
 * 
 * @author Ondrej Milenovsky
 * */
// TODO extend to id filter
public class FilterStorage {

    private Map<String, Set<String>> types;

    private Set<String> params;

    public FilterStorage() {
        params = new HashSet<String>(CommonKeys.COMMON_PARAMS);
    }

    public FilterStorage(Map<String, Set<String>> types) {
        this();
        this.types = types;
    }

    public FilterStorage(Set<String> params) {
        this();
        this.params.addAll(params);
    }

    public FilterStorage(Map<String, Set<String>> types, Set<String> params) {
        this(params);
        this.types = types;
    }

    // SETTERS / GETTERS ////////////////////////

    public void setTypes(Map<String, Set<String>> types) {
        this.types = types;
    }

    public Map<String, Set<String>> getTypes() {
        return types;
    }

    public void setCommonParams(Set<String> commonTypes) {
        this.params = commonTypes;
    }

    public Set<String> getCommonParams() {
        return params;
    }

    public void addCommonParams(Collection<String> params) {
        this.params.addAll(params);
    }

    // TYPES ////////////////////////

    /** if the type is not filtred */
    public boolean hasType(String type) {
        if (types == null) {
            return true;
        }
        return types.containsKey(type);
    }

    // PARAMS ////////////////////////

    /** if the param of the type is not filtred */
    public boolean typeHasParam(String type, String param) {
        if ((params != null) && (params.contains(param))) {
            return true;
        }
        if ((types == null) || !types.containsKey(type)) {
            return true;
        }
        return types.get(type).contains(param);
    }

    /** if the param of the type is not filtred */
    public boolean typeHasParam(String type, Typer<?> param) {
        return typeHasParam(type, param.id);
    }

    // IDS ////////////////////////

}
