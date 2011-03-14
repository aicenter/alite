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
import java.util.HashSet;
import java.util.Set;

import javax.vecmath.Point2d;

/**
 * Painter to paint single text from element
 * 
 * @author Ondrej Milenovsky
 * */
public class TextPainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(TextKeys.COLOR
            .toString(), TextKeys.ALIGN_ON_SCREEN.toString(), TextKeys.CENTER.toString(),
            TextKeys.CONSTANT_SIZE.toString(), TextKeys.FONT.toString(), TextKeys.FONT_STYLE
                    .toString(), TextKeys.FONT_NAME.toString(), TextKeys.FONT_SIZE.toString(),
            TextKeys.TEXT.toString(), TextKeys.POS.toString()));

    private final Vis2DOutput vis2d;

    private Color color = Color.BLACK;
    private Point2d pos = new Point2d();
    private boolean constantSize = false;
    private Font font = new Font("arial", Font.PLAIN, 10);
    private Align align = Align.NONE;
    private String text = "Welcome to hell!";
    private double fontSize = 10;

    public TextPainter(Vis2DOutput vis2dOutput) {
        this.vis2d = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        Graphics2D graphics2d = vis2d.getGraphics2D();

        color = StructUtils.updateValue(e, TextKeys.COLOR, color);
        align = e.getParameter(TextKeys.ALIGN_ON_SCREEN);
        constantSize = StructUtils.updateValue(e, TextKeys.CONSTANT_SIZE, constantSize);
        text = StructUtils.updateValue(e, TextKeys.TEXT, text);

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

        // TODO size
        double sizeX = 100;
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
                pos = new Point2d(pos.x -= sizeX / 2.0, pos.y -= sizeY / 2.0);
            }
        }
        if (!posChanged && (align != Align.NONE)) {
            pos = new Point2d(0, 0);
        }

        int x1;
        int y1;

        if (align == Align.UPPER_LEFT) {
            x1 = (int) pos.x;
            y1 = (int) (pos.y + sizeY);
        } else if (align == Align.UPPER_CENTER) {
            x1 = (int) (pos.x + (vis2d.getWidth() - sizeX) / 2.0);
            y1 = (int) (pos.y + sizeY);
        } else if (align == Align.UPPER_RIGHT) {
            x1 = (int) (pos.x + vis2d.getWidth() - sizeX);
            y1 = (int) (pos.y + sizeY);
        } else if (align == Align.LEFT_CENTER) {
            x1 = (int) pos.x;
            y1 = (int) (pos.y + (vis2d.getHeight() - sizeY) / 2.0 + sizeY);
        } else if (align == Align.CENTER) {
            x1 = (int) (pos.x + (vis2d.getWidth() - sizeX) / 2.0);
            y1 = (int) (pos.y + (vis2d.getHeight() - sizeY) / 2.0 + sizeY);
        } else if (align == Align.RIGHT_CENTER) {
            x1 = (int) (pos.x + vis2d.getWidth() - sizeX);
            y1 = (int) (pos.y + (vis2d.getHeight() - sizeY) / 2.0 + sizeY);
        } else if (align == Align.LOWER_LEFT) {
            x1 = (int) pos.x;
            y1 = (int) (pos.y + vis2d.getHeight() - sizeY + sizeY);
        } else if (align == Align.LOWER_CENTER) {
            x1 = (int) (pos.x + (vis2d.getWidth() - sizeX) / 2.0);
            y1 = (int) (pos.y + vis2d.getHeight() - sizeY + sizeY);
        } else if (align == Align.LOWER_RIGHT) {
            x1 = (int) (pos.x + vis2d.getWidth() - sizeX);
            y1 = (int) (pos.y + vis2d.getHeight() - sizeY + sizeY);
        } else {
            x1 = vis2d.transX(pos.x);
            y1 = (int) (vis2d.transY(pos.y) + sizeY / 2.0);
        }
        int x2 = x1 + (int) sizeX;
        int y2 = y1 + (int) sizeY;

        if ((align != Align.NONE) || vis2d.containsRect(x1, y1, x2, y2)) {
            graphics2d.drawString(text, x1, y1);
        }
    }

}
