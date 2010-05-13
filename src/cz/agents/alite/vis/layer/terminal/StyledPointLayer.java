package cz.agents.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.StyledPoint;
import cz.agents.alite.vis.element.aggregation.StyledPointElements;

/**
 * same as point layer but different colors and widths for points
 *
 * count of widths and colors should be smaller than points, the remaining
 * points stay same style like the last one
 *
 * @author Ondrej Milenovsky
 */
public class StyledPointLayer extends TerminalLayer {

    private final StyledPointElements pointElements;

    protected StyledPointLayer(StyledPointElements pointElements) {
        this.pointElements = pointElements;
    }

    @Override
    public void paint(Graphics2D canvas) {

        canvas.setStroke(new BasicStroke(1));
        int radius = 10;

        for(StyledPoint point: pointElements.getPoints()) {
            canvas.setColor(point.getColor());
            radius = (int) (point.getWidth() / 2.0);

            int x1 = Vis.transX(point.getPosition().x) - radius;
            int y1 = Vis.transY(point.getPosition().y) - radius;
            int x2 = Vis.transX(point.getPosition().x) + radius;
            int y2 = Vis.transY(point.getPosition().y) + radius;
            if (x2 > 0 && x1 < Vis.getDrawingDimension().width  && y2 > 0 && y1 < Vis.getDrawingDimension().height) {
                canvas.fillOval(x1, y1, radius * 2, radius * 2);
            }
        }

    }

    @Override
    public String getLayerDescription() {
        String description = "Layer shows points with different colors and widths.";
        return buildLayersDescription(description);
    }

    public static StyledPointLayer create(StyledPointElements pointElements) {
        return new StyledPointLayer(pointElements);
    }

}
