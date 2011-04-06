package incubator.visprotocol.vis.layer.element;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.FilterStorage;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

/**
 * Structure for text. One instance can be used many times with changed parameters
 * 
 * @author Ondrej Milenovsky
 * */
public class TextElement extends AbstractElement {
    public Point3d center;
    // or
    public Point3d pos;

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

    private TextElement() {
    }

    public TextElement(String text, Point3d center, Color color, boolean constatnSize,
            Align alignOnScreen, Font font) {
        this.text = text;
        this.center = center;
        this.color = color;
        this.constantSize = constatnSize;
        this.alignOnScreen = alignOnScreen;
        this.font = font;
        init();
    }

    public TextElement(String text, Point3d center, Color color, boolean constatnSize,
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

    public TextElement(String text, Color color, boolean constatnSize, Align alignOnScreen,
            Point3d pos, Font font) {
        this.text = text;
        this.pos = pos;
        this.color = color;
        this.constantSize = constatnSize;
        this.alignOnScreen = alignOnScreen;
        this.font = font;
        init();
    }

    public TextElement(String text, Color color, boolean constatnSize, Point2d alignRatio,
            Point3d pos, Font font) {
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
    public TextElement copy() {
        TextElement ret = new TextElement();
        ret.center = center;
        ret.pos = pos;
        ret.text = text;
        ret.color = color;
        ret.constantSize = constantSize;
        ret.alignOnScreen = alignOnScreen;
        ret.alignRatio = alignRatio;
        ret.font = font;
        ret.fontName = fontName;
        ret.fontSize = fontSize;
        ret.fontStyle = fontStyle;
        return ret;
    }
}
