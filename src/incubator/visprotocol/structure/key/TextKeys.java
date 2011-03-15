package incubator.visprotocol.structure.key;

import incubator.visprotocol.structure.key.struct.Align;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;

/**
 * Use center or pos. Use font or [font name, font size and font flags].
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class TextKeys {

    public static final String TYPE = "Text";

    /** Center, do not use pos. If used align, center is same as pos. */
    public static final Typer<Point2d> CENTER = new Typer<Point2d>("center");
    /** Upper left corner, do not use center. */
    public static final Typer<Point2d> POS = new Typer<Point2d>("pos");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Boolean> CONSTANT_SIZE = new Typer<Boolean>("const_size");
    /**
     * Pos or center means translation from aligned position, so (0,0) means exact place. If pos is
     * not specified, is reseted to (0,0), so you don't need to specify it everywhere. If ratio,
     * align_ratio is used.
     */
    public static final Typer<Align> ALIGN_ON_SCREEN = new Typer<Align>("align");
    /** used only if align = ratio, (0, 0) means upper left corner, (0.5, 0.5) means center */
    public static final Typer<Point2d> ALIGN_RATIO = new Typer<Point2d>("align_ratio");

    public static final Typer<String> TEXT = new Typer<String>("text");
    /** Do not use font. */
    public static final Typer<Double> FONT_SIZE = new Typer<Double>("font_size");
    /** Do not use font. */
    public static final Typer<String> FONT_NAME = new Typer<String>("font_name");
    /** Do not use font. */
    public static final Typer<Integer> FONT_STYLE = new Typer<Integer>("font_style");
    /** Do not use font size, name and flags. */
    public static final Typer<Font> FONT = new Typer<Font>("font");

}
