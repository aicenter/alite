package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;

/**
 * This layer wills whole canvas by specified color, used as background.
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorLayer extends TypedLayer {

    public static final String DEFAULT_ID = "Background";
    private final Structure struct;

    public FillColorLayer(Color color, String path, FilterStorage filter) {
        this(color, path, DEFAULT_ID, filter);
    }

    public FillColorLayer(Color color, String[] path, FilterStorage filter) {
        this(color, path, DEFAULT_ID, filter);
    }

    public FillColorLayer(Color color, String path, String id, FilterStorage filter) {
        super(filter);
        struct = generateStruct(color, StructUtils.createStructure(path), id);
    }

    public FillColorLayer(Color color, String[] path, String id, FilterStorage filter) {
        super(filter);
        struct = generateStruct(color, StructUtils.createStructure(path), id);
    }

    private Structure generateStruct(Color color, Structure struct, String id) {
        Folder f = StructUtils.getLeafFolder(struct);
        setParameter(f, CommonKeys.NOT_CHANGE, true);
        Element e = f.getElement(id, FillColorKeys.TYPE);
        setParameter(e, FillColorKeys.COLOR, color);
        setParameter(e, CommonKeys.NOT_CHANGE, true);
        struct.setType(CommonKeys.STRUCT_PART);
        return struct;
    }

    @Override
    public Structure pull() {
        if (hasType(FillColorKeys.TYPE) && typeHasParam(FillColorKeys.TYPE, FillColorKeys.COLOR)) {
            return struct.deepCopy();
        }
        return new Structure(CommonKeys.STRUCT_PART);
    }
}
