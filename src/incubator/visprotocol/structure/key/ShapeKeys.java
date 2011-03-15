package incubator.visprotocol.structure.key;

import incubator.visprotocol.structure.key.struct.Shape;

import java.awt.Color;

import javax.vecmath.Point2d;

/**
 * Use diameter or [size x and size y]
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class ShapeKeys {

    public static final String TYPE = "Shape";

    public static final Typer<Shape> SHAPE = new Typer<Shape>("shape");

    public static final Typer<Point2d> CENTER = new Typer<Point2d>("center");
    /** Do not use size x and y, they will be replaced with size */
    public static final Typer<Double> SIZE = new Typer<Double>("size");
    /** Do not use diameter */
    public static final Typer<Double> SIZE_X = new Typer<Double>("size_x");
    /** Do not use diameter */
    public static final Typer<Double> SIZE_Y = new Typer<Double>("size_y");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    /** value < 0 means fill */
    public static final Typer<Double> LINE_WIDTH = new Typer<Double>("line_width");
    public static final Typer<Boolean> CONSTANT_LINE_WIDTH = new Typer<Boolean>("const_line_width");
    public static final Typer<Boolean> CONSTANT_SIZE = new Typer<Boolean>("const_size");

}
