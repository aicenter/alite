package incubator.visprotocol.vis.layer;

import incubator.visprotocol.processor.StructProcessor;

/**
 * Vis layer added to VisFactory
 * 
 * @author Ondrej Milenovsky
 * */
public interface VisLayer extends StructProcessor {
    public void setFilter(FilterStorage filter);
    public void setRoot(String root);
    public String getId();
}
