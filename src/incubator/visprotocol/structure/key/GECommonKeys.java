package incubator.visprotocol.structure.key;

/**
 * @author Ondrej Milenovsky
 * */
public abstract class GECommonKeys {
    /** Elements/folders won't be shown if ge.zoom > max */
    public static final Typer<Double> LOD_MAX = new Typer<Double>("ge_lod_max");
    /** Elements/folders won't be shown if ge.zoom < min */
    public static final Typer<Double> LOD_MIN = new Typer<Double>("ge_lod_min");

}
