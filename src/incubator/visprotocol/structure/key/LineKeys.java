package incubator.visprotocol.structure.key;

import java.awt.Color;
import java.util.Collection;

import javax.vecmath.Point2d;

public abstract class LineKeys {

    public static final String TYPE = "Line";

    public static final Typer<Collection<Point2d>> POINTS = new Typer<Collection<Point2d>>("points");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Double> WIDTH = new Typer<Double>("width");
    public static final Typer<Boolean> CONSTANT_SIZE = new Typer<Boolean>("const_size");

}
