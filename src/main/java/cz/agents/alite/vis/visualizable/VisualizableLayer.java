package cz.agents.alite.vis.visualizable;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.layer.AbstractLayer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public class VisualizableLayer extends AbstractLayer {

    private final VisualizableProvider provider;

    public VisualizableLayer(VisualizableProvider provider) {
        this.provider = provider;
    }

    @Override
    public void paint(Graphics2D canvas) {
        Dimension dim = Vis.getDrawingDimension();
        Rectangle2D drawingRectangle = new Rectangle(dim);
        for (Visualizable visualizable : provider.getData()) {
            visualizable.paint(canvas, drawingRectangle);
        }
    }
}
