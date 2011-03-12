package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.StructProcessor;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.layer.proxy.ProxyLayer;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point2d;

public class BrainzProxyLayer implements ProxyLayer {

    private final ArrayList<Point2d> points;

    public BrainzProxyLayer(int n, int size) {
        points = new ArrayList<Point2d>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Point2d(Math.random() * size, Math.random() * size));
        }
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        Structure struct = new Structure();
        Folder f = struct.getRoot("Undead land").getFolder("Brainz");
        for (int i = 0; i < points.size(); i++) {
            Element e = f.getElement("p" + i, PointKeys.TYPE);
            e.setParameter(PointKeys.POS, points.get(i));
            if (i == 0) {
                e.setParameter(PointKeys.COLOR, Color.RED);
                e.setParameter(PointKeys.WIDTH, 4.0);
                e.setParameter(PointKeys.CONSTANT_SIZE, true);
            }
        }
        processor.push(struct);
    }

}
