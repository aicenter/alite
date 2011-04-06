package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.FilterStorage;

/**
 * Generating struct timestamp
 * 
 * @author Ondrej Milenovsky
 * */
public class SimInfoLayer extends AbstractLayer {

    private final TimeHolder holder;

    public SimInfoLayer(TimeHolder holder, FilterStorage filter) {
        super(filter);
        this.holder = holder;
    }


    // @Override
    // public Structure pull() {
    // Structure struct = new Structure(CommonKeys.STRUCT_PART, env.getTime());
    // if (hasType("Name")) {
    // Element e = struct.getRoot("World").getFolder("Other").getElement("Info", "Name");
    // setParameter(e, "Name", "Undead land");
    // }
    // struct.setType(CommonKeys.STRUCT_PART);
    // return struct;
    // }

    @Override
    protected void generateFrame() {
        setTime(holder.getCurrentTimeMillis());
    }

}
