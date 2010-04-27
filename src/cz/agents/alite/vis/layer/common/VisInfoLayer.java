package cz.agents.alite.vis.layer.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.layer.AbstractLayer;
import cz.agents.alite.vis.layer.VisLayer;
import cz.agents.alite.vis.layer.toggle.KeyToggleLayer;

public class VisInfoLayer extends AbstractLayer {

    protected VisInfoLayer() {
    }

    @Override
    public void paint(Graphics2D canvas) {
        Dimension dim = Vis.getDrawingDimension();

        canvas.setColor(Color.BLUE);
        canvas.drawString(String.format("Zoom: %.2f", Vis.getZoomFactor()), 15, dim.height - 75);
        canvas.drawString(String.format("Offset: (%.2f, %.2f)", Vis.getOffset().x / Vis.getZoomFactor(), Vis.getOffset().y / Vis.getZoomFactor()), 15, dim.height - 55);
        canvas.drawString(String.format("Screen: (%.2f, %.2f)", Vis.getCursorPosition().x, Vis.getCursorPosition().y), 15, dim.height - 35);
        canvas.drawString(String.format("World: (%.2f, %.2f)", Vis.transInvX((int) Vis.getCursorPosition().x), Vis.transInvY((int) Vis.getCursorPosition().y)), 15, dim.height - 15);
    }

    public static VisLayer create() {
        KeyToggleLayer toggle = KeyToggleLayer.create("i");
        toggle.addSubLayer(new VisInfoLayer());

        return toggle;
    }

    @Override
    public String getLayerDescription() {
        String description = "[Vis Info] The layer shows visualisation info in lower left corner (zoom factor, panning offser, mouse position).";
        return buildLayersDescription(description);
    }

}
