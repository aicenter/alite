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
package cz.cvut.fel.aic.alite.vis.layer.toggle;

import java.awt.Graphics2D;

import cz.cvut.fel.aic.alite.vis.Vis;


/**
 * The LodToggleLayer turns on and off its sub-layers according to a zoom of the
 * view in the Vis singleton (nearness of the "camera" to the scene).
 *
 *
 * @author Antonin Komenda
 *
 */
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
