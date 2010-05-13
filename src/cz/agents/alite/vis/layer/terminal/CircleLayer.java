package cz.agents.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.Circle;
import cz.agents.alite.vis.element.aggregation.CircleElements;

public class CircleLayer extends TerminalLayer {

    private final CircleElements circleElements;

    protected CircleLayer(CircleElements circleElements) {
        this.circleElements = circleElements;
    }

    @Override
    public void paint(Graphics2D canvas) {
        canvas.setColor(circleElements.getColor());
        canvas.setStroke(new BasicStroke(circleElements.getStrokeWidth()));

        for (Circle circle: circleElements.getCircles()) {
            int x1 = Vis.transX(circle.getPosition().x - circle.getRadius());
            int y1 = Vis.transY(circle.getPosition().y - circle.getRadius());
            int x2 = Vis.transX(circle.getPosition().x + circle.getRadius());
            int y2 = Vis.transY(circle.getPosition().y + circle.getRadius());
            int diameterW = Vis.transW(circle.getRadius() * 2.0);
            int diameterH = Vis.transH(circle.getRadius() * 2.0);
            if (x2 > 0 && x1 < Vis.getDrawingDimension().width  && y2 > 0 && y1 < Vis.getDrawingDimension().height) {
                canvas.drawOval(x1, y1, diameterW, diameterH);
            }
        }
    }

    public static CircleLayer create(CircleElements circleElements) {
        return new CircleLayer(circleElements);
    }

}
