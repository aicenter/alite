package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.FilterStorage;

/**
 * Generating struct timestamp
 * 
 * @author Ondrej Milenovsky
 * */
public class TimeLayer extends AbstractLayer {

    private final TimeHolder holder;

    public TimeLayer(TimeHolder holder, FilterStorage filter) {
        super(filter);
        this.holder = holder;
    }

    @Override
    protected void generateFrame() {
        setTime(holder.getCurrentTimeMillis());
    }

}
