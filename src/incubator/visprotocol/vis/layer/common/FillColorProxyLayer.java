package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.structure.key.struct.ChangeFlag;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;

/**
 * This layer wills whole canvas by specified color, used as background.
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorProxyLayer extends TypedLayer {

    public static final String DEFAULT_ID = "Background";
    private final Structure struct;
    private final boolean generateOnce = false;
    private boolean generated = false;

    public FillColorProxyLayer(Color color, String path, FilterStorage filter) {
        this(color, path, DEFAULT_ID, filter);
    }

    public FillColorProxyLayer(Color color, String[] path, FilterStorage filter) {
        this(color, path, DEFAULT_ID, filter);
    }

    public FillColorProxyLayer(Color color, String path, String id, FilterStorage filter) {
        super(filter);
        struct = generateStruct(color, StructUtils.createStructure(path), id);
    }

    public FillColorProxyLayer(Color color, String[] path, String id, FilterStorage filter) {
        super(filter);
        struct = generateStruct(color, StructUtils.createStructure(path), id);
    }

    private Structure generateStruct(Color color, Structure struct, String id) {
        Element e = StructUtils.getLeafFolder(struct).getElement(id, FillColorKeys.TYPE);
        setParameter(e, FillColorKeys.COLOR, color);
        setParameter(e, CommonKeys.CHANGE, ChangeFlag.NOT_CHANGE);
        return struct;
    }

    @Override
    public Structure pull() {
        if ((!generated || !generateOnce) && hasType(FillColorKeys.TYPE)
                && typeHasParam(FillColorKeys.TYPE, FillColorKeys.COLOR)) {
            generated = true;
            return struct.deepCopy();
        }
        return Structure.createEmptyInstance();
    }
}
