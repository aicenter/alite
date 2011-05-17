package incubator.visprotocol.vis.layer.complex.example;

import incubator.visprotocol.vis.layer.complex.AbstractLayer;
import incubator.visprotocol.vis.layer.complex.element.PointElement;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.math.geometry.Vector3D;


/**
 * Random static points.
 * 
 * @author Ondrej Milenovsky
 * */
public class StaticPoints extends AbstractLayer {

    private final ArrayList<Vector3D> points;

    public StaticPoints(int n, int size) {
        super("Static", true);
        points = new ArrayList<Vector3D>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Vector3D(Math.random() * size, Math.random() * size, 0));
        }
    }

    @Override
    protected void generateFrame() {
        for (int i = 0; i < points.size(); i++) {
            addElement("p" + i, new PointElement(points.get(i), new Color(255, 160, 160, 30), 4,
                    true));
        }
    }

}
