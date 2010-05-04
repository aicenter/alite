package cz.agents.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.Line;
import cz.agents.alite.vis.element.aggregation.LineElements;

public class LineLayer extends TerminalLayer {

    private final LineElements lineElements;

    protected LineLayer(LineElements lineElements) {
        this.lineElements = lineElements;
    }

    @Override
    public void paint(Graphics2D canvas) {
        canvas.setColor(lineElements.getColor());
        canvas.setStroke(new BasicStroke(lineElements.getStrokeWidth()));

        for (Line line: lineElements.getLines()) {
            int x = Vis.transX(line.getFrom().x);
            int y = Vis.transY(line.getFrom().y);
            int xTo = Vis.transX(line.getTo().x);
            int yTo = Vis.transY(line.getTo().y);

            //if ((x > 0 && x < Vis.getDrawingDimension().width  && y > 0 && y < Vis.getDrawingDimension().height)
            //        || (xTo > 0 && xTo < Vis.getDrawingDimension().width  && yTo > 0 && yTo < Vis.getDrawingDimension().height)) {
                canvas.drawLine(x, y, xTo, yTo);

                onEachLine(canvas, line);
            //}
        }
    }

    protected void onEachLine(Graphics2D canvas, Line line) {
    }

    @Override
    public String getLayerDescription() {
        String description = "Layer shows lines.";
        return buildLayersDescription(description);
    }

    public static LineLayer create(LineElements lineElements) {
        return new LineLayer(lineElements);
    }

}
