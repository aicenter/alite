package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.element.PointElement;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point3d;

/**
 * Random dynamic points changing color and appearing/disappearing.
 * 
 * @author Ondrej Milenovsky
 * */
public class DynamicPointsLayer extends AbstractLayer {

    private final ArrayList<Point3d> points;
    private double prGenerate = 0.9;

    public DynamicPointsLayer(int n, int size) {
        super("Dynamic points");
        points = new ArrayList<Point3d>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Point3d(Math.random() * size, Math.random() * size, 0));
        }
    }

    @Override
    protected void generateFrame() {
        for (int i = 0; i < points.size(); i++) {
            if (Math.random() > prGenerate) {
                continue;
            }
            addElement("l" + i, new PointElement(points.get(i), new Color(0, 0, (int) (Math
                    .random() * 256)), 30, false));
        }
    }
}
