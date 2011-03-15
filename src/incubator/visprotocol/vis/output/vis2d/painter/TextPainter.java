package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.vecmath.Point2d;

/**
 * Painter to paint single text from element
 * 
 * @author Ondrej Milenovsky
 * */
public class TextPainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(TextKeys.COLOR.id,
            TextKeys.ALIGN_ON_SCREEN.id, TextKeys.CENTER.id, TextKeys.CONSTANT_SIZE.id,
            TextKeys.FONT.id, TextKeys.FONT_STYLE.id, TextKeys.FONT_NAME.id, TextKeys.FONT_SIZE.id,
            TextKeys.TEXT.id, TextKeys.POS.id, TextKeys.ALIGN_RATIO.id));

    /** align ratios for basic aligns */
    public static final Map<Align, Point2d> ALIGN_RATIOS = new HashMap<Align, Point2d>();
    static {
        ALIGN_RATIOS.put(Align.UPPER_LEFT, new Point2d(0, 0));
        ALIGN_RATIOS.put(Align.UPPER_CENTER, new Point2d(0.5, 0));
        ALIGN_RATIOS.put(Align.UPPER_RIGHT, new Point2d(1, 0));
        ALIGN_RATIOS.put(Align.CENTER_LEFT, new Point2d(0, 0.5));
        ALIGN_RATIOS.put(Align.CENTER, new Point2d(0.5, 0.5));
        ALIGN_RATIOS.put(Align.CENTER_RIGHT, new Point2d(1, 0.5));
        ALIGN_RATIOS.put(Align.LOWER_LEFT, new Point2d(0, 1));
        ALIGN_RATIOS.put(Align.LOWER_CENTER, new Point2d(0.5, 1));
        ALIGN_RATIOS.put(Align.LOWER_RIGHT, new Point2d(1, 1));
    }

    private final Vis2DOutput vis2d;

    private Color color = Color.BLACK;
    private Point2d pos = new Point2d();
    private boolean constantSize = false;
    private Font font = new Font("Arial", Font.PLAIN, 10);
    private Align align = Align.NONE;
    private String text = "Welcome to hell!";
    private double fontSize = 10;
    private Point2d alignRatio = new Point2d(0.5, 0.5);

    public TextPainter(Vis2DOutput vis2dOutput) {
        this.vis2d = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2d.getGraphics2D();

        color = StructUtils.updateValue(e, TextKeys.COLOR, color);
        constantSize = StructUtils.updateValue(e, TextKeys.CONSTANT_SIZE, constantSize);
        text = StructUtils.updateValue(e, TextKeys.TEXT, text);
        align = StructUtils.updateValue(e, TextKeys.ALIGN_ON_SCREEN, align);
        if (align == Align.RATIO) {
            alignRatio = StructUtils.updateValue(e, TextKeys.ALIGN_RATIO, alignRatio);
        }

        // font ///////
        font = StructUtils.updateValue(e, TextKeys.FONT, font);

        String fontName = font.getName();
        int fontStyle = font.getStyle();
        fontSize = font.getSize();
        boolean fontChanged = false;
        if (e.containsParameter(TextKeys.FONT_STYLE)) {
            fontStyle = e.getParameter(TextKeys.FONT_STYLE);
            fontChanged = true;
        }
        if (e.containsParameter(TextKeys.FONT_NAME)) {
            fontName = e.getParameter(TextKeys.FONT_NAME);
            fontChanged = true;
        }
        if (e.containsParameter(TextKeys.FONT_SIZE)) {
            fontSize = e.getParameter(TextKeys.FONT_SIZE);
            fontChanged = true;
        }
        if (fontChanged) {
            font = new Font(fontName, fontStyle, (int) fontSize);
        }
        // end font ///////

        Font drawFont = font;
        if (!constantSize) {
            fontSize = vis2d.transW(fontSize);
            drawFont = new Font(fontName, fontStyle, (int) fontSize);
        }

        graphics2d.setFont(drawFont);
        graphics2d.setColor(color);

        double sizeX = vis2d.getFontMetrics(drawFont).stringWidth(text);
        double sizeY = fontSize;

        // position
        boolean posChanged = false;
        if (e.containsParameter(TextKeys.POS)) {
            pos = e.getParameter(TextKeys.POS);
            posChanged = true;
        } else if (e.containsParameter(TextKeys.CENTER)) {
            pos = e.getParameter(TextKeys.CENTER);
            posChanged = true;
            if (align == Align.NONE) {
                pos = new Point2d(pos.x -= vis2d.getFontMetrics(font).stringWidth(text) / 2.0,
                        pos.y -= sizeY / 2.0);
            }
        }
        if (!posChanged && (align != Align.NONE)) {
            pos = new Point2d(0, 0);
        }

        int x1;
        int y1;

        if (align == Align.NONE) {
            x1 = vis2d.transX(pos.x);
            y1 = (int) (vis2d.transY(pos.y) + sizeY / 2.0);
        } else {
            if (align != Align.RATIO) {
                alignRatio = ALIGN_RATIOS.get(align);
            }
            x1 = (int) ((vis2d.getPaintWidth() - sizeX) * alignRatio.x + pos.x);
            y1 = (int) ((vis2d.getPaintHeight() - sizeY) * alignRatio.y + pos.y + sizeY);
        }

        int x2 = x1 + (int) sizeX;
        int y2 = y1 + (int) sizeY;

        if ((align != Align.NONE) || vis2d.containsRect(x1, y1, x2, y2)) {
            graphics2d.drawString(text, x1, y1);
        }
    }

}
