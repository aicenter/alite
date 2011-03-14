package incubator.visprotocol.vis.layer;

import incubator.visprotocol.structure.key.Typer;

import java.util.Map;
import java.util.Set;

/**
 * Filter for proxy layers. Filtres unused elements, parameters and ids. Filter can be empty and it
 * won't throw exceptions.
 * 
 * @author Ondrej Milenovsky
 * */
// TODO extend to id filter
public class TypeParamIdFilter {

    private Map<String, Set<String>> types;

    public TypeParamIdFilter() {
    }

    public TypeParamIdFilter(Map<String, Set<String>> types) {
        this.types = types;
    }

    // SETTERS / GETTERS ////////////////////////

    public void setTypes(Map<String, Set<String>> types) {
        this.types = types;
    }

    public Map<String, Set<String>> getTypes() {
        return types;
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
        if ((types == null) || !types.containsKey(type)) {
            return true;
        }
        return types.get(type).contains(param);
    }

    /** if the param of the type is not filtred */
    public boolean typeHasParam(String type, Typer<?> param) {
        if ((types == null) || !types.containsKey(type)) {
            return true;
        }
        return types.get(type).contains(param.paramId);
    }

    // IDS ////////////////////////

}
