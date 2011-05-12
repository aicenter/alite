package incubator.visprotocol.structure.key;

import java.awt.Color;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Keys for PointPainter.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class PointKeys {

    public static final String TYPE = "Point";

    public static final Typer<Vector3D> CENTER = new Typer<Vector3D>("center");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Double> SIZE = new Typer<Double>("size");
    public static final Typer<Boolean> CONSTANT_SIZE = new Typer<Boolean>("const_size");

}
