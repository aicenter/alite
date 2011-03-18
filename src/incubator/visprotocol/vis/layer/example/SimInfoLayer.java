package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;

/**
 * Generating struct timestamp
 * 
 * @author Ondrej Milenovsky
 * */
public class SimInfoLayer extends TypedLayer {

    private final ExampleEnvironment env;
    
    public SimInfoLayer(ExampleEnvironment env, FilterStorage filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure(CommonKeys.STRUCT_PART, env.getTime());
        if (hasType("Name")) {
            Element e = struct.getRoot("World").getFolder("Other").getElement("Info", "Name");
            setParameter(e, "Name", "Undead land");
        }
        struct.setType(CommonKeys.STRUCT_PART);
        return struct;
    }

}
