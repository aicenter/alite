package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.layer.TypeParamIdFilter;
import incubator.visprotocol.vis.layer.TypedLayer;

public class SimInfoProxyLayer extends TypedLayer {

    private final ExampleEnvironment env;
    
    public SimInfoProxyLayer(ExampleEnvironment env, TypeParamIdFilter filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure(env.getTime());
        if (hasType("Text")) {
            Element e = struct.getRoot("Undead land").getFolder("Other").getElement("Info", "Text");
            setParameter(e, "Name", "Undead land");
        }
        return struct;
    }

}
