package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.structprocessor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.TypedProxyLayer;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class BackgroundProxyLayer extends TypedProxyLayer {

    public static final String DEFAULT_ID = "Background";
    private final Structure struct;

    public BackgroundProxyLayer(Color color, String path, Map<String, Set<String>> types) {
        this(color, path, DEFAULT_ID, types);
    }

    public BackgroundProxyLayer(Color color, String path, String id, Map<String, Set<String>> types) {
        super(types);
        struct = generateStruct(color, path, id);
    }

    private Structure generateStruct(Color color, String path, String id) {
        Structure ret = StructUtils.createStructure(path);
        Element e = StructUtils.getLeaf(ret).getElement(id, FillColorKeys.TYPE);
        setParameter(e, FillColorKeys.COLOR, color);
        return ret;
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        if (hasType(FillColorKeys.TYPE) && typeHasParam(FillColorKeys.TYPE, FillColorKeys.COLOR)) {
            processor.push(struct);
        }
    }

}
