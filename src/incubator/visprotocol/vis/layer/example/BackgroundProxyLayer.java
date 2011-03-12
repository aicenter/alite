package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.vis.layer.proxy.ProxyLayer;

import java.awt.Color;

public class BackgroundProxyLayer implements ProxyLayer {
    private final Color color;

    public BackgroundProxyLayer(Color color) {
        this.color = color;
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        Structure struct = new Structure();
        Element e = struct.getRoot("Undead land").getFolder("Other").getElement("Background",
                FillColorKeys.TYPE);
        e.setParameter(FillColorKeys.COLOR, color);
        processor.push(struct);
    }

}
