package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.layer.TypeParamIdFilter;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point2d;

public class BrainzProxyLayer extends TypedLayer {

    private final ArrayList<Point2d> points;

    public BrainzProxyLayer(int n, int size, TypeParamIdFilter filter) {
        super(filter);
        points = new ArrayList<Point2d>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Point2d(Math.random() * size, Math.random() * size));
        }
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure();
        if (hasType(PointKeys.TYPE)) {
            Folder f = struct.getRoot("Undead land").getFolder("Brainz");
            for (int i = 0; i < points.size(); i++) {
                Element e = f.getElement("p" + i, PointKeys.TYPE);
                setParameter(e, PointKeys.POS, points.get(i));
                if (i == 0) {
                    setParameter(e, PointKeys.COLOR, Color.PINK);
                    setParameter(e, PointKeys.WIDTH, 4.0);
                    setParameter(e, PointKeys.CONSTANT_SIZE, true);
                }
            }
        }
        return struct;
    }

}
