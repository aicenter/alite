package incubator.visprotocol.vis.output.painter.vis2d;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.vecmath.Point2d;

public class PointPainter implements Painter {

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;
    private double width = 1;
    private Point2d pos = new Point2d();

    public PointPainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        if (e.containsParameter(PointKeys.COLOR)) {
            color = e.getParameter(PointKeys.COLOR);
        }
        if (e.containsParameter(PointKeys.WIDTH)) {
            width = e.getParameter(PointKeys.WIDTH);
        }
        if (e.containsParameter(PointKeys.POS)) {
            pos = e.getParameter(PointKeys.POS);
        }

        Graphics2D graphics2d = vis2dOutput.getGraphics2D();

        int radius = (int) (width / 2);
        graphics2d.setColor(color);
        graphics2d.setStroke(new BasicStroke(1));

        int x1 = vis2dOutput.transX(pos.x) - radius;
        int y1 = vis2dOutput.transY(pos.y) - radius;
        int x2 = vis2dOutput.transX(pos.x) + radius;
        int y2 = vis2dOutput.transY(pos.y) + radius;
        if ((x2 > 0) && (x1 < vis2dOutput.getWidth()) && (y2 > 0) && (y1 < vis2dOutput.getHeight())) {
            graphics2d.fillOval(x1, y1, radius * 2, radius * 2);
        }
    }

}
