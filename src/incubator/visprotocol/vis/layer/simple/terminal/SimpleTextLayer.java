package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.vis.layer.element.TextElementMut;
import incubator.visprotocol.vis.layer.simple.SimpleElementLayer;

/**
 * Layer to draw text. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class SimpleTextLayer extends SimpleElementLayer {

    private static int layerCount = 0;
    
    public SimpleTextLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public SimpleTextLayer(boolean staticLayer) {
        this("Texts " + (++layerCount) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends TextElementMut> getElements() {
        return getTexts();
    }
    
    protected abstract Iterable<? extends TextElementMut> getTexts();

}
