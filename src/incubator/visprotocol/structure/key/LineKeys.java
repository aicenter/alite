package incubator.visprotocol.structure.key;

import java.awt.Color;
import java.util.Collection;

import javax.vecmath.Point2d;

/**
 * @author Ondrej Milenovsky
 */
public abstract class LineKeys {

    public static final String TYPE = "Line";

    public static final Typer<Collection<Point2d>> POINTS = new Typer<Collection<Point2d>>("points");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Double> LINE_WIDTH = new Typer<Double>("line_width");
    public static final Typer<Boolean> CONSTANT_LINE_WIDTH = new Typer<Boolean>("const_line_width");

}