package incubator.visprotocol.vis.layer.bfu.terminal;

import incubator.visprotocol.vis.layer.bfu.BFUAbstractLayer;
import incubator.visprotocol.vis.layer.element.AbstractElement;
import incubator.visprotocol.vis.layer.element.PointElement;

/**
 * Layer to draw points
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class BFUPointLayer extends BFUAbstractLayer {

    private static int count = 0;
    
    public BFUPointLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public BFUPointLayer(boolean staticLayer) {
        this("Points " + count++, staticLayer);
    }
    
    @Override
    protected Iterable<? extends AbstractElement> getElements() {
        return getPoints();
    }
    
    protected abstract Iterable<? extends PointElement> getPoints();

}
