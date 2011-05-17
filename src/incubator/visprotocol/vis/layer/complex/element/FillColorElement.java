package incubator.visprotocol.vis.layer.complex.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.Color;

/**
 * Structure for color. One instance can be used many times with changed parameters
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorElement extends AbstractElement {
    public final Color color;

    public FillColorElement(Color color) {
        this.color = color;
    }

    @Override
    public Element createElement(Element lastElement, String name, FilterStorage filter) {
        Element e = new Element(name, FillColorKeys.TYPE);
        setElementParameter(e, lastElement, FillColorKeys.COLOR, color, filter);
        return e;
    }

    @Override
    public String getType() {
        return FillColorKeys.TYPE;
    }
}
