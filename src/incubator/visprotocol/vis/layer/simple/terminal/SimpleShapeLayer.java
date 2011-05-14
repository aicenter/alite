package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.vis.layer.element.ShapeElement;
import incubator.visprotocol.vis.layer.simple.SimpleElementLayer;

/**
 * Layer to draw shapes (ovals and rectangles). Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class SimpleShapeLayer extends SimpleElementLayer {

    private static int layerCount = 0;
    
    public SimpleShapeLayer(String name, boolean staticLayer) {
        super(name, staticLayer);
    }
    
    public SimpleShapeLayer(boolean staticLayer) {
        this("Shapes " + (++layerCount) + staticText(staticLayer), staticLayer);
    }
    
    @Override
    protected final Iterable<? extends ShapeElement> getElements() {
        return getShapes();
    }
    
    protected abstract Iterable<? extends ShapeElement> getShapes();

}
