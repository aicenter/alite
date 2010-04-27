package spaceplan.zone;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import spaceplan.GeometryUtils;

public class PrismZone implements Zone {

    private static final long serialVersionUID = 7280812238303400113L;

    private Point2d[] points;
    private double[] xs;
    private double[] ys;

    public PrismZone(Point2d[] points, double height) {
        this.points = points;

        xs = new double[points.length];
        ys = new double[points.length];
        int i = 0;
        for (Point2d point : points) {
            xs[i] = point.x;
            ys[i] = point.y;
            i++;
        }
    }

    @Override
    public boolean testPoint(final Point3d point) {
        return GeometryUtils.isPointInsidePolygon(xs, ys, point.x, point.y);
    }

    @Override
    public boolean testLine(final Point3d point1, final Point3d point2, Point3d outPoint) {
        Point2d oldPoint = points[points.length - 1];
        for (Point2d point : points) {
            boolean intersects = GeometryUtils.isLineIntersectingLine(
                    point1.x, point1.y, point2.x, point2.y,
                    point.x, point.y, oldPoint.x, oldPoint.y);
            if (intersects) {
                // TODO: outPoint
                return true;
            }
            oldPoint = point;
        }

        return false;

    }

    @Override
    public void accept(ZoneVisitor visitor) {
        visitor.visit(this);
    }

    public Point2d[] getPoints() {
        return points;
    }

    public double[] getXs() {
        return xs;
    }

    public double[] getYs() {
        return ys;
    }

}
