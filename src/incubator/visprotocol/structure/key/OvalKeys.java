package incubator.visprotocol.structure.key;

import java.awt.Color;

import javax.vecmath.Point2d;

/**
 * Use (point1 and (point2 or (diameter or (sizex and sizey)))) or (pos and (diameter or (sizex and
 * sizey)))
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class OvalKeys {

    public static final String TYPE = "Oval";

    /** one corner */
    @Deprecated
    public static final Typer<Point2d> POINT1 = new Typer<Point2d>("point1");
    /** second corner */
    @Deprecated
    public static final Typer<Point2d> POINT2 = new Typer<Point2d>("point2");
    /** center of oval */
    public static final Typer<Point2d> POS = new Typer<Point2d>("pos");
    /** diameter of circle */
    public static final Typer<Double> DIAMETER = new Typer<Double>("d");
    /** width of oval */
    public static final Typer<Double> SIZE_X = new Typer<Double>("sx");
    /** height of oval */
    public static final Typer<Double> SIZE_Y = new Typer<Double>("sy");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    /** line width */
    public static final Typer<Double> WIDTH = new Typer<Double>("width");
    public static final Typer<Boolean> CONSTANT_SIZE = new Typer<Boolean>("const_size");

}
