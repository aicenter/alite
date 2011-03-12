package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.layer.proxy.ProxyLayer;

public class SimInfoProxyLayer implements ProxyLayer {

    private long time = 0;

    public SimInfoProxyLayer() {
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        time++;
        Structure struct = new Structure(time);
        Element e = struct.getRoot("Undead land").getFolder("Other").getElement("Info", "Text");
        e.setParameter("Name", "Undead land");
        processor.push(struct);
    }

}
