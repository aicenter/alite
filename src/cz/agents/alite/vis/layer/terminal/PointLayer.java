package cz.agents.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.Point;
import cz.agents.alite.vis.element.aggregation.PointElements;

public class PointLayer extends TerminalLayer {

    private final PointElements pointElements;

    protected PointLayer(PointElements pointElements) {
        this.pointElements = pointElements;
    }

    @Override
    public void paint(Graphics2D canvas) {
        int radius = (int) (pointElements.getStrokeWidth() / 2.0);
        canvas.setColor(pointElements.getColor());
        canvas.setStroke(new BasicStroke(1));
        for (Point point: pointElements.getPoints()) {
            int x = Vis.transX(point.getPosition().x);
            int y = Vis.transY(point.getPosition().y);
            //if (x > 0 && x < Vis.getDrawingDimension().width  && y > 0 && y < Vis.getDrawingDimension().height) {
                canvas.drawOval(x - radius, y - radius, radius * 2, radius * 2);
                canvas.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            //}
        }
    }

    @Override
    public String getLayerDescription() {
        String description = "Layer shows points.";
        return buildLayersDescription(description);
    }

    public static PointLayer create(PointElements pointElements) {
        return new PointLayer(pointElements);
    }

}
