package cz.cvut.fel.aic.alite.vis.element.implemetation;

import javax.vecmath.Point3d;

import cz.cvut.fel.aic.alite.vis.element.Point;

public class PointImpl implements Point {

    public final Point3d position;

    public PointImpl(Point3d position) {
        this.position = position;
    }

    @Override
    public Point3d getPosition() {
        return position;
    }

}
