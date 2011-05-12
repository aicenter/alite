package incubator.visprotocol.vis.layer.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Mutable structure for text. One instance can be used many times with changed parameters
 * 
 * @author Ondrej Milenovsky
 * */
public class TextElementMut extends AbstractElement {
    public Vector3D center;
    // or
    public Vector3D pos;

    public String text;
    public Color color;
    public boolean constantSize;

    public Align alignOnScreen;
    // or
    public Point2d alignRatio;

    public double fontSize;
    public String fontName;
    public int fontStyle;
    // and/or
    public Font font;

    private TextElementMut(TextElementMut textElement) {
        this.center = textElement.center;
        this.pos = textElement.pos;
        this.text = textElement.text;
        this.color = textElement.color;
        this.constantSize = textElement.constantSize;
        this.alignOnScreen = textElement.alignOnScreen;
        this.alignRatio = textElement.alignRatio;
        this.font = textElement.font;
        this.fontName = textElement.fontName;
        this.fontSize = textElement.fontSize;
        this.fontStyle = textElement.fontStyle;
    }

    public TextElementMut(String text, Vector3D center, Color color, boolean constatnSize,
            Align alignOnScreen, Font font) {
        this.text = text;
        this.center = center;
        this.color = color;
        this.constantSize = constatnSize;
        this.alignOnScreen = alignOnScreen;
        this.font = font;
        init();
    }

    public TextElementMut(String text, Vector3D center, Color color, boolean constatnSize,
            Point2d alignRatio, Font font) {
        this.text = text;
        this.center = center;
        this.color = color;
        this.constantSize = constatnSize;
        this.alignRatio = alignRatio;
        alignOnScreen = Align.RATIO;
        this.font = font;
        init();
    }

    public TextElementMut(String text, Color color, boolean constatnSize, Align alignOnScreen,
            Vector3D pos, Font font) {
        this.text = text;
        this.pos = pos;
        this.color = color;
        this.constantSize = constatnSize;
        this.alignOnScreen = alignOnScreen;
        this.font = font;
        init();
    }

    public TextElementMut(String text, Color color, boolean constatnSize, Point2d alignRatio,
            Vector3D pos, Font font) {
        this.text = text;
        this.pos = pos;
        this.color = color;
        this.constantSize = constatnSize;
        this.alignRatio = alignRatio;
        alignOnScreen = Align.RATIO;
        this.font = font;
        init();
    }

    private void init() {
        if (fontSize == 0) {
            fontSize = font.getSize();
        }
        if (fontStyle == 0) {
            fontStyle = font.getStyle();
        }
    }

    @Override
    public Element createElement(Element lastElement, String name, FilterStorage filter) {
        Element e = new Element(name, TextKeys.TYPE);
        setElementParameter(e, lastElement, TextKeys.TEXT, text, filter);
        setElementParameter(e, lastElement, TextKeys.COLOR, color, filter);
        setElementParameter(e, lastElement, TextKeys.CONSTANT_SIZE, constantSize, filter);
        if (center != null) {
            setElementParameter(e, lastElement, TextKeys.CENTER, center, filter);
        } else {
            setElementParameter(e, lastElement, TextKeys.POS, pos, filter);
        }
        setElementParameter(e, lastElement, TextKeys.ALIGN_ON_SCREEN, alignOnScreen, filter);
        setElementParameter(e, lastElement, TextKeys.ALIGN_RATIO, alignRatio, filter);

        setElementParameter(e, lastElement, TextKeys.FONT, font, filter);
        setElementParameter(e, lastElement, TextKeys.FONT_NAME, fontName, filter);
        setElementParameter(e, lastElement, TextKeys.FONT_SIZE, fontSize, filter);
        setElementParameter(e, lastElement, TextKeys.FONT_STYLE, fontStyle, filter);

        return e;
    }

    @Override
    public String getType() {
        return TextKeys.TYPE;
    }

    @Override
    public TextElementMut copy() {
        TextElementMut ret = new TextElementMut(this);
        return ret;
    }
}
