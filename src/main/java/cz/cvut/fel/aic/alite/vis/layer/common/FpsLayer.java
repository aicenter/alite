/* 
 * Copyright (C) 2017 Czech Technical University in Prague.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.alite.vis.layer.common;

import java.awt.Color;
import java.awt.Graphics2D;

import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import cz.cvut.fel.aic.alite.vis.layer.toggle.KeyToggleLayer;

public class FpsLayer extends CommonLayer {

    private final Color color;

    private int fps;
    private int fpsCount = 0;
    private long time = System.currentTimeMillis();

    protected FpsLayer(Color color) {
        this.color = color;
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

        canvas.setColor(color);
        canvas.drawString("FPS: " + fps, 15, 40);
    }

    public static VisLayer create(final Color color) {
        KeyToggleLayer toggle = KeyToggleLayer.create("f");
        toggle.addSubLayer(new FpsLayer(color));

        return toggle;
    }

    public static VisLayer create() {
        return create(Color.BLUE);
    }

    @Override
    public String getLayerDescription() {
        String description = "[FPS] The layer shows current visualisation FPS (Frames per Second).";
        return buildLayersDescription(description);
    }

}
