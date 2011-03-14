package incubator.visprotocol.structure.key;

import java.awt.Color;

import javax.vecmath.Point2d;

public abstract class PointKeys {

    public static final String TYPE = "Point";

    public static final Typer<Point2d> CENTER = new Typer<Point2d>("center");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Double> SIZE = new Typer<Double>("width");
    public static final Typer<Boolean> CONSTANT_SIZE = new Typer<Boolean>("const_size");

}
