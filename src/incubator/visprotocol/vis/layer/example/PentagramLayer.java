package incubator.visprotocol.vis.layer.example;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point2d;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.structure.key.OvalKeys;
import incubator.visprotocol.vis.layer.TypeParamIdFilter;
import incubator.visprotocol.vis.layer.TypedLayer;

public class PentagramLayer extends TypedLayer {

    private final ExampleEnvironment env;
    private double lineWidth = 110;

    public PentagramLayer(ExampleEnvironment env, TypeParamIdFilter filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {

        Structure struct = new Structure();
        Folder f = struct.getRoot("Undead land").getFolder("Pentagram");

        int step = (int) (env.getTime() * 1.5);
        for (int i = 0; i < 30; i++) {
            fillPentagram(f, step + i, new Color(30 + i * 6, 0, 0), i);
        }

        return struct;
    }

    private void fillPentagram(Folder f, int step, Color c, int number) {
        if (step < 0) {
            step += 360;
        }
        if (step >= 360) {
            step -= 360;
        }
        double size = 10000;

        double cx = size / 2.0;
        double cy = size / 2.0;
        double sizeX = size * Math.cos(step * Math.PI / 180.0);
        double sizeA = Math.abs(sizeX);

        Element e = f.getElement("circle" + number, OvalKeys.TYPE);
        if (number == 0) {
            e.setParameter(OvalKeys.CONSTANT_SIZE, false);
            e.setParameter(OvalKeys.WIDTH, lineWidth);
        }
        e.setParameter(OvalKeys.COLOR, c);
        e.setParameter(OvalKeys.POS, new Point2d(cx, cy));
        e.setParameter(OvalKeys.SIZE_X, sizeA);
        e.setParameter(OvalKeys.SIZE_Y, size);

        e = f.getElement("star" + number, LineKeys.TYPE);
        if (number == 0) {
            e.setParameter(LineKeys.WIDTH, lineWidth);
            e.setParameter(LineKeys.CONSTANT_SIZE, false);
        }
        e.setParameter(LineKeys.COLOR, c);

        ArrayList<Point2d> points = new ArrayList<Point2d>(6);
        for (int i = 0; i < 5; i++) {
            double a = Math.PI * 2 / 2.5 * i + Math.PI / 2.0 + 0.01;
            points.add(new Point2d(cx + sizeX / 2.0 * Math.cos(a), cy + size / 2.0 * Math.sin(a)));
        }
        points.add(points.get(0));

        e.setParameter(LineKeys.POINTS, points);
    }

}
