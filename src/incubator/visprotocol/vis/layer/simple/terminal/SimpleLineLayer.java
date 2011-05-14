package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.vis.layer.element.LineElement;
import incubator.visprotocol.vis.layer.simple.SimpleElementLayer;

/**
 * Layer to draw lines. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class SimpleLineLayer extends SimpleElementLayer {

    private static int layerCount = 0;
    
    public SimpleLineLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public SimpleLineLayer(boolean staticLayer) {
        this("Lines " + (++layerCount) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends LineElement> getElements() {
        return getLines();
    }
    
    protected abstract Iterable<? extends LineElement> getLines();

}
