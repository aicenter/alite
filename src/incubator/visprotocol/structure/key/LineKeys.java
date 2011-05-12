package incubator.visprotocol.structure.key;

import java.awt.Color;
import java.util.Collection;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Keys for LinePainter.
 * 
 * @author Ondrej Milenovsky
 */
public abstract class LineKeys {

    public static final String TYPE = "Line";

    public static final Typer<Collection<Vector3D>> POINTS = new Typer<Collection<Vector3D>>("points");
    public static final Typer<Color> COLOR = new Typer<Color>("color");
    public static final Typer<Double> LINE_WIDTH = new Typer<Double>("line_width");
    public static final Typer<Boolean> CONSTANT_LINE_WIDTH = new Typer<Boolean>("const_line_width");

}
