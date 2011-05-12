package incubator.visprotocol.vis.layer.terminal;

import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.vis.layer.AbstractLayer;

/**
 * Generating struct timestamp
 * 
 * @author Ondrej Milenovsky
 * */
public class TimeLayer extends AbstractLayer {

    private final TimeHolder holder;

    public TimeLayer(TimeHolder holder) {
        super(null);
        this.holder = holder;
    }

    @Override
    protected void generateFrame() {
    }

    @Override
    public Structure pull() {
        return new Structure(CommonKeys.STRUCT_PART, holder.getCurrentTimeMillis());
    }

}
