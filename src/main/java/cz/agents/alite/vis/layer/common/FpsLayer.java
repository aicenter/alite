package cz.agents.alite.vis.layer.common;

import java.awt.Color;
import java.awt.Graphics2D;

import cz.agents.alite.vis.layer.VisLayer;
import cz.agents.alite.vis.layer.toggle.KeyToggleLayer;

public class FpsLayer extends CommonLayer {

    private int fps;
    private int fpsCount = 0;
    private long time = System.currentTimeMillis();

    protected FpsLayer() {
    }

    @Override
    public void paint(Graphics2D canvas) {
        fpsCount++;
        if (fpsCount == 10) {
            long now = System.currentTimeMillis();
            fps = (int) (10 * 1000 / (now - time + 1));
            time = now;
            fpsCount = 0;
        }

        canvas.setColor(Color.BLUE);
        canvas.drawString("FPS: " + fps, 15, 40);
    }

    public static VisLayer create() {
        KeyToggleLayer toggle = KeyToggleLayer.create("f");
        toggle.addSubLayer(new FpsLayer());

        return toggle;
    }

    @Override
    public String getLayerDescription() {
        String description = "[FPS] The layer shows current visualisation FPS (Frames per Second).";
        return buildLayersDescription(description);
    }

}
