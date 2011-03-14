package incubator.visprotocol.vis.output.vis2d;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Basic transformators for Vis2D
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class Vis2DBasicTransformators {
    /** move and zoom transformator */
    public static Collection<Transformator> createBasicTransformators() {
        ArrayList<Transformator> ret = new ArrayList<Transformator>();
        ret.add(new MoveTransformator());
        ret.add(new ZoomTransformator());
        return ret;
    }
}
