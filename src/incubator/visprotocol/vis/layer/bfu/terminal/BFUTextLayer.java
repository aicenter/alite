package incubator.visprotocol.vis.layer.bfu.terminal;

import incubator.visprotocol.vis.layer.bfu.BFUElementLayer;
import incubator.visprotocol.vis.layer.element.TextElementMut;

/**
 * Layer to draw text. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class BFUTextLayer extends BFUElementLayer {

    private static int layerCount = 0;
    
    public BFUTextLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public BFUTextLayer(boolean staticLayer) {
        this("Texts " + (layerCount++) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends TextElementMut> getElements() {
        return getTexts();
    }
    
    protected abstract Iterable<? extends TextElementMut> getTexts();

}
