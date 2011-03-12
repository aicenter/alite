package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.vis.layer.proxy.TypedProxyLayer;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class BackgroundProxyLayer extends TypedProxyLayer {
    private final Color color;

    public BackgroundProxyLayer(Color color, Map<String, Set<String>> types) {
        super(types);
        this.color = color;
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        Structure struct = new Structure();
        if (hasType(FillColorKeys.TYPE)) {
            Element e = struct.getRoot("Undead land").getFolder("Other").getElement("Background",
                    FillColorKeys.TYPE);
            if (typeHasParam(FillColorKeys.TYPE, FillColorKeys.COLOR)) {
                setParameter(e, FillColorKeys.COLOR, color);
            }
        }
        processor.push(struct);
    }

}
