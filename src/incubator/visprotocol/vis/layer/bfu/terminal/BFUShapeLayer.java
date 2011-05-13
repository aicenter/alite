package incubator.visprotocol.vis.layer.bfu.terminal;

import incubator.visprotocol.vis.layer.bfu.BFUElementLayer;
import incubator.visprotocol.vis.layer.element.ShapeElement;

/**
 * Layer to draw shapes (ovals and rectangles). Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class BFUShapeLayer extends BFUElementLayer {

    private static int layerCount = 0;
    
    public BFUShapeLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public BFUShapeLayer(boolean staticLayer) {
        this("Shapes " + (layerCount++) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends ShapeElement> getElements() {
        return getShapes();
    }
    
    protected abstract Iterable<? extends ShapeElement> getShapes();

}
