package incubator.visprotocol.vis.layer;

import incubator.visprotocol.creator.VisualPersonProvider;
import incubator.visprotocol.vis.layer.proxy.PointProxyLayer;
import incubator.visprotocol.vis.layer.proxy.ProxyLayer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cz.agents.alite.vis.element.Point;
import cz.agents.alite.vis.element.aggregation.PointElements;
import cz.agents.alite.vis.element.implemetation.PointImpl;

// TODO: create new Layer interface and implement it here
public class PersonLayer extends Object {

    public static ProxyLayer create(final VisualPersonProvider visualPersonProvider) {
        return new PointProxyLayer(new PointElements() {

            @Override
            public int getStrokeWidth() {
                return 10;
            }

            @Override
            public Color getColor() {
                return Color.BLUE;
            }

            @Override
            public Iterable<? extends Point> getPoints() {
                List<Point> points = new ArrayList<Point>();
                points.add(new PointImpl(visualPersonProvider.getPersonPosition()));
                return points;
            }
        });
    }

}
