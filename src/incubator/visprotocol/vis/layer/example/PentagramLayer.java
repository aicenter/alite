package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.structure.key.ShapeKeys;
import incubator.visprotocol.structure.key.struct.Shape;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point2d;

/**
 * The unholy layer >:-)
 * 
 * @author Ondrej Milenovsky
 * */
public class PentagramLayer extends TypedLayer {

    private final ExampleEnvironment env;
    private double size = 10000;

    public PentagramLayer(ExampleEnvironment env, FilterStorage filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {

        Structure struct = new Structure(CommonKeys.STRUCT_PART);
        Folder f = struct.getRoot("World").getFolder("Pentagram");

        int step = (int) (env.getTime() * 0.075);
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
        double cx = size / 2.0;
        double cy = size / 2.0;
        double sizeX = size * Math.cos(step * Math.PI / 180.0);
        double sizeA = Math.abs(sizeX);

        if (hasType(ShapeKeys.TYPE)) {
            Element e = f.getElement("circle" + number, ShapeKeys.TYPE);
            if (number == 0) {
                setParameter(e, ShapeKeys.CONSTANT_LINE_WIDTH, false);
                setParameter(e, ShapeKeys.LINE_WIDTH, size / 90.0);
                setParameter(e, ShapeKeys.SHAPE, Shape.OVAL);
            }
            setParameter(e, ShapeKeys.COLOR, c);
            setParameter(e, ShapeKeys.CENTER, new Point2d(cx, cy));
            setParameter(e, ShapeKeys.SIZE_X, sizeA);
            setParameter(e, ShapeKeys.SIZE_Y, size);
        }

        if (hasType(LineKeys.TYPE)) {
            Element e = f.getElement("star" + number, LineKeys.TYPE);
            if (number == 0) {
                setParameter(e, LineKeys.LINE_WIDTH, size / 90.0);
                setParameter(e, LineKeys.CONSTANT_LINE_WIDTH, false);
            }
            e.setParameter(LineKeys.COLOR, c);

            ArrayList<Point2d> points = new ArrayList<Point2d>(6);
            for (int i = 0; i < 5; i++) {
                double a = Math.PI * 2 / 2.5 * i + Math.PI / 2.0 + 0.01;
                points.add(new Point2d(cx + sizeX / 2.01 * Math.cos(a), cy + size / 2.01
                        * Math.sin(a)));
            }
            points.add(points.get(0));

            setParameter(e, LineKeys.POINTS, points);
        }
    }

}
