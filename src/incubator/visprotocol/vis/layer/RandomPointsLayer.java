package incubator.visprotocol.vis.layer;

import incubator.visprotocol.vis.layer.proxy.PointProxyLayer;
import incubator.visprotocol.vis.layer.proxy.ProxyLayer;

import java.awt.Color;
import java.util.ArrayList;

import javax.vecmath.Point3d;

import cz.agents.alite.vis.element.Point;
import cz.agents.alite.vis.element.aggregation.PointElements;
import cz.agents.alite.vis.element.implemetation.PointImpl;

public class RandomPointsLayer implements Layer {

    public static ProxyLayer create(int n, int size, final String id) {
        final ArrayList<Point> points = new ArrayList<Point>(n);
        for (int i = 0; i < n; i++) {
            points.add(new PointImpl(new Point3d(Math.random() * size, Math.random() * size, 0)));
        }

        return new PointProxyLayer(new PointElements() {

            @Override
            public int getStrokeWidth() {
                return 5;
            }

            @Override
            public Color getColor() {
                return Color.RED;
            }

            @Override
            public Iterable<? extends Point> getPoints() {
                return points;
            }
        }, id);
    }

    @Override
    public String getName() {
        return "Random layer";
    }
}
