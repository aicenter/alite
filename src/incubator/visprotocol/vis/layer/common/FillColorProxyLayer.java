package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

/**
 * This layer wills whole canvas by specified color, used as background.
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorProxyLayer extends TypedLayer {

    public static final String DEFAULT_ID = "Background";
    private final Structure struct;

    public FillColorProxyLayer(Color color, String path, Map<String, Set<String>> types) {
        this(color, path, DEFAULT_ID, types);
    }

    public FillColorProxyLayer(Color color, String[] path, Map<String, Set<String>> types) {
        this(color, path, DEFAULT_ID, types);
    }

    public FillColorProxyLayer(Color color, String path, String id, Map<String, Set<String>> types) {
        super(types);
        struct = generateStruct(color, StructUtils.createStructure(path), id);
    }

    public FillColorProxyLayer(Color color, String[] path, String id, Map<String, Set<String>> types) {
        super(types);
        struct = generateStruct(color, StructUtils.createStructure(path), id);
    }

    private Structure generateStruct(Color color, Structure struct, String id) {
        Element e = StructUtils.getLeafFolder(struct).getElement(id, FillColorKeys.TYPE);
        setParameter(e, FillColorKeys.COLOR, color);
        return struct;
    }

    @Override
    public Structure pull() {
        if (hasType(FillColorKeys.TYPE) && typeHasParam(FillColorKeys.TYPE, FillColorKeys.COLOR)) {
            return struct;
        }
        return Structure.EMPTY_INSTANCE;
    }

}
