package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.layer.TypeParamIdFilter;
import incubator.visprotocol.vis.layer.TypedLayer;

public class SimInfoProxyLayer extends TypedLayer {

    private long time = 0;

    public SimInfoProxyLayer(TypeParamIdFilter filter) {
        super(filter);
    }

    @Override
    public Structure pull() {
        time++;
        Structure struct = new Structure(time);
        if (hasType("Text")) {
            Element e = struct.getRoot("Undead land").getFolder("Other").getElement("Info", "Text");
            setParameter(e, "Name", "Undead land");
        }
        return struct;
    }

}
