package incubator.visprotocol.factory;

import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.good.AbstractLayer;

/**
 * Will toggle layers when pressing a key
 * 
 * @author Ondrej Milenovsky
 * */
public class KeyLayerToggler {

    @SuppressWarnings("unused")
    private FilterStorage filter;
    
    public KeyLayerToggler(FilterStorage filter) {
        this.filter = filter;
    }
    
    public void registerKey(AbstractLayer layer, char key) {
        registerKey(layer.getId(), key);
    }
    
    public void registerKey(String layerPath, char key) {
        // TODO
    }
}
