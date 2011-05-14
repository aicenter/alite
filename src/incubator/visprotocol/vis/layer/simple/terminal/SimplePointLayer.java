package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.vis.layer.element.PointElement;
import incubator.visprotocol.vis.layer.simple.SimpleElementLayer;

/**
 * Layer to draw points. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class SimplePointLayer extends SimpleElementLayer {

    private static int layerCount = 0;
    
    public SimplePointLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public SimplePointLayer(boolean staticLayer) {
        this("Points " + (++layerCount) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends PointElement> getElements() {
        return getPoints();
    }
    
    protected abstract Iterable<? extends PointElement> getPoints();

}
