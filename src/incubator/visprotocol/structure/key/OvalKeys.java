package incubator.visprotocol.structure.key;

import java.awt.Color;

import javax.vecmath.Point2d;

/**
 * Use pos and (diameter or (size x and size y))
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class OvalKeys {

    public static final String TYPE = "Oval";

    public static final Typer<Point2d> CENTER = new Typer<Point2d>("center");
    public static final Typer<Double> DIAMETER = new Typer<Double>("diameter");
    public static final Typer<Double> SIZE_X = new Typer<Double>("size_x");
    public static final Typer<Double> SIZE_Y = new Typer<Double>("size_y");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Double> LINE_WIDTH = new Typer<Double>("line_width");
    public static final Typer<Boolean> CONSTANT_LINE_WIDTH = new Typer<Boolean>("const_line_width");

}
