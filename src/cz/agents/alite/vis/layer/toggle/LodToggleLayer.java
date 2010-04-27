package cz.agents.alite.vis.layer.toggle;

import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;


public class LodToggleLayer extends ToggleLayer {

    private final double maxZoom;
    private final double minZoom;

    protected LodToggleLayer(double maxZoom, double minZoom) {
        this.maxZoom = maxZoom;
        this.minZoom = minZoom;
    }

    @Override
    public void paint(Graphics2D canvas) {
        double zoomFactor = Vis.transW(100) / 100.0;
        setEnabled((zoomFactor >= minZoom) && (zoomFactor <= maxZoom));

        super.paint(canvas);
    }

    @Override
    public String getLayerDescription() {
        String description = "All sub-layers are shown only for zoom factor greater than " + String.format("%.2f", minZoom) + " and lower than " + String.format("%.2f", maxZoom) + ":";
        return buildLayersDescription(description);
    }

    public static LodToggleLayer create(double maxZoom, double minZoom) {
        return new LodToggleLayer(maxZoom, minZoom);
    }

}
