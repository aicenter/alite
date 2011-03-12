package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.structprocessor.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.layer.TypedProxyLayer;

import java.util.Map;
import java.util.Set;

public class SimInfoProxyLayer extends TypedProxyLayer {

    private long time = 0;

    public SimInfoProxyLayer(Map<String, Set<String>> types) {
        super(types);
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        time++;
        Structure struct = new Structure(time);
        if (hasType("Text")) {
            Element e = struct.getRoot("Undead land").getFolder("Other").getElement("Info", "Text");
            setParameter(e, "Name", "Undead land");
        }
        processor.push(struct);
    }

}
