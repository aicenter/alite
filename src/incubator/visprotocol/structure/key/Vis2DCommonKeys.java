package incubator.visprotocol.structure.key;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ondrej Milenovsky
 * */
public abstract class Vis2DCommonKeys {

    public static final Set<String> COMMON_PARAMS = new HashSet<String>();

    /** Elements/folders won't be shown if vis.zoom > max */
    public static final Typer<Double> LOD_MAX = new Typer<Double>("vis2d_lod_max");
    /** Elements/folders won't be shown if vis.zoom < min */
    public static final Typer<Double> LOD_MIN = new Typer<Double>("vis2d_lod_min");

    /** Precision when printing double */
    public static final Typer<Integer> PRECISION = new Typer<Integer>("vis2d_prec");
    
    static {
        COMMON_PARAMS.add(LOD_MAX.id);
        COMMON_PARAMS.add(LOD_MIN.id);
    }

}
