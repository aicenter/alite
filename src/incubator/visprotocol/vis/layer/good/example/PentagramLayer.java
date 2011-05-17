package incubator.visprotocol.vis.layer.good.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.key.struct.Shape;
import incubator.visprotocol.vis.layer.good.AbstractLayer;
import incubator.visprotocol.vis.layer.good.element.LineElement;
import incubator.visprotocol.vis.layer.good.element.ShapeElement;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.math.geometry.Vector3D;

/**
 * The unholy layer >:-)
 * 
 * @author Ondrej Milenovsky
 * */
public class PentagramLayer extends AbstractLayer {

    private final ExampleEnvironment env;
    private double size = 10000;

    public PentagramLayer(ExampleEnvironment env) {
        super("Pentagram");
        this.env = env;
    }

    @Override
    protected void generateFrame() {
        int step = (int) (env.getCurrentTimeMillis() * 0.075);
        for (int i = 0; i < 30; i++) {
            fillPentagram(step + i, new Color(30 + i * 6, 0, 0), i);
        }
    }

    private void fillPentagram(int step, Color c, int number) {
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

        addElement("circle" + number, new ShapeElement(Shape.OVAL, new Vector3D(cx, cy, 0), c,
                sizeA, size, size / 90.0, false, false));
        ArrayList<Vector3D> points = new ArrayList<Vector3D>(6);
        for (int i = 0; i < 5; i++) {
            double a = Math.PI * 2 / 2.5 * i + Math.PI / 2.0 + 0.01;
            points.add(new Vector3D(cx + sizeX / 2.01 * Math.cos(a),
                    cy + size / 2.01 * Math.sin(a), 0));
        }
        points.add(points.get(0));
        addElement("star" + number, new LineElement(points, c, size / 90.0, false));
    }

}
