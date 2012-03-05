package cz.agents.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.StyledLine;
import cz.agents.alite.vis.element.aggregation.StyledLineElements;

public class StyledLineLayer extends TerminalLayer {

    private final StyledLineElements lineElements;

    protected StyledLineLayer(StyledLineElements lineElements) {
        this.lineElements = lineElements;
    }

    @Override
    public void paint(Graphics2D canvas) {
        canvas.setStroke(new BasicStroke(1));
        for (StyledLine line : lineElements.getLines()) {
            drawLine(line, canvas);
        }
    }

    private void drawLine(StyledLine line, Graphics2D canvas) {
        canvas.setColor(line.getColor());
        canvas.setStroke(new BasicStroke(line.getStrokeWidth()));

        int x = Vis.transX(line.getFrom().x);
        int y = Vis.transY(line.getFrom().y);
        int xTo = Vis.transX(line.getTo().x);
        int yTo = Vis.transY(line.getTo().y);

        // TODO: both points lies in out of the rectangle, but intersects it
        if ((x > 0 && x < Vis.getDrawingDimension().width  && y > 0 && y < Vis.getDrawingDimension().height)
                || (xTo > 0 && xTo < Vis.getDrawingDimension().width && yTo > 0 && yTo < Vis.getDrawingDimension().height)) {
            canvas.drawLine(x, y, xTo, yTo);
        }
    }

    @Override
    public String getLayerDescription() {
        String description = "Layer shows lines with different colors and widths.";
        return buildLayersDescription(description);
    }

    public static StyledLineLayer create(StyledLineElements lineElements) {
        return new StyledLineLayer(lineElements);
    }

}
