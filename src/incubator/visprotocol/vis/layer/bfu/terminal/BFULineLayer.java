package incubator.visprotocol.vis.layer.bfu.terminal;

import incubator.visprotocol.vis.layer.bfu.BFUElementLayer;
import incubator.visprotocol.vis.layer.element.LineElement;

/**
 * Layer to draw lines. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class BFULineLayer extends BFUElementLayer {

    private static int layerCount = 0;
    
    public BFULineLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public BFULineLayer(boolean staticLayer) {
        this("Lines " + (layerCount++) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends LineElement> getElements() {
        return getLines();
    }
    
    protected abstract Iterable<? extends LineElement> getLines();

}
