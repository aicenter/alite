package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point2d;

/**
 * Random static points.
 * 
 * @author Ondrej Milenovsky
 * */
public class BrainzLayer extends TypedLayer {

    private final ArrayList<Point2d> points;

    public BrainzLayer(int n, int size, FilterStorage filter) {
        super(filter);
        points = new ArrayList<Point2d>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Point2d(Math.random() * size, Math.random() * size));
        }
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure(CommonKeys.STRUCT_PART);
        if (hasType(PointKeys.TYPE)) {
            Folder f = struct.getRoot("Undead land").getFolder("Brainz");
            setParameter(f, CommonKeys.NOT_CHANGE, true);
            for (int i = 0; i < points.size(); i++) {
                Element e = f.getElement("p" + i, PointKeys.TYPE);
                setParameter(e, PointKeys.CENTER, points.get(i));
                if (i == 0) {
                    setParameter(e, PointKeys.COLOR, new Color(255, 160, 160, 30));
                    setParameter(e, PointKeys.SIZE, 4.0);
                    setParameter(e, PointKeys.CONSTANT_SIZE, true);
                }
            }
        }
        return struct;
    }

}
