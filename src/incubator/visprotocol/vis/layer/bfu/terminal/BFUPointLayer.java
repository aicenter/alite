package incubator.visprotocol.vis.layer.bfu.terminal;

import incubator.visprotocol.vis.layer.bfu.BFUElementLayer;
import incubator.visprotocol.vis.layer.element.PointElement;

/**
 * Layer to draw points. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class BFUPointLayer extends BFUElementLayer {

    private static int layerCount = 0;
    
    public BFUPointLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public BFUPointLayer(boolean staticLayer) {
        this("Points " + (layerCount++) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends PointElement> getElements() {
        return getPoints();
    }
    
    protected abstract Iterable<? extends PointElement> getPoints();

}
