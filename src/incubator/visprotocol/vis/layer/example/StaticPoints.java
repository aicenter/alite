package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.element.PointElement;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point3d;

/**
 * Random static points.
 * 
 * @author Ondrej Milenovsky
 * */
public class StaticPoints extends AbstractLayer {

    private final ArrayList<Point3d> points;

    public StaticPoints(int n, int size, FilterStorage filter) {
        super(filter);
        points = new ArrayList<Point3d>(n);
        for (int i = 0; i < n; i++) {
            points.add(new Point3d(Math.random() * size, Math.random() * size, 0));
        }
    }

    @Override
    protected void generateFrame() {
        changeFolder(".World.Static");
        setStaticFolder();
        PointElement point = new PointElement(null, new Color(255, 160, 160, 30), 4, true);
        for (int i = 0; i < points.size(); i++) {
            point.center = points.get(i);
            addElement("p" + i, point);
        }
    }
    
    

    // @Override
    // public Structure pull() {
    // Structure struct = new Structure(CommonKeys.STRUCT_PART);
    // if (hasType(PointKeys.TYPE)) {
    // Folder f = struct.getRoot("World").getFolder("Static");
    // setParameter(f, CommonKeys.NOT_CHANGE, true);
    // for (int i = 0; i < points.size(); i++) {
    // Element e = f.getElement("p" + i, PointKeys.TYPE);
    // setParameter(e, PointKeys.CENTER, points.get(i));
    // if (i == 0) {
    // setParameter(e, PointKeys.COLOR, new Color(255, 160, 160, 30));
    // setParameter(e, PointKeys.SIZE, 4.0);
    // setParameter(e, PointKeys.CONSTANT_SIZE, true);
    // }
    // }
    // }
    // return struct;
    // }

}
