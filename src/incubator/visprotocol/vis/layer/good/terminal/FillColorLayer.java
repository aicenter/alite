package incubator.visprotocol.vis.layer.good.terminal;

import incubator.visprotocol.vis.layer.good.AbstractLayer;
import incubator.visprotocol.vis.layer.good.element.FillColorElement;

import java.awt.Color;

/**
 * This layer wills whole canvas by specified color, used as background.
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorLayer extends AbstractLayer {

    public static final String DEFAULT_ID = "Background";
    private final Color color;

    public FillColorLayer(Color color) {
        this(color, DEFAULT_ID);
    }

    public FillColorLayer(Color color, String name) {
        super(name, true);
        this.color = color;
    }

    @Override
    protected void generateFrame() {
        addElement(DEFAULT_ID, new FillColorElement(color));
    }
}
