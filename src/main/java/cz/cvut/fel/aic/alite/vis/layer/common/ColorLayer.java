/* 
 * Copyright (C) 2019 Czech Technical University in Prague.
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

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class ColorLayer extends CommonLayer {
// TODO: the color should be requested

	private Color color;

	protected ColorLayer(final Color color) {
		this.color = color;
	}

	@Override
	public void paint(Graphics2D canvas) {
		canvas.setColor(color);
		Dimension dim = Vis.getDrawingDimension();
		canvas.fillRect(0, 0, dim.width, dim.height);
	}

	@Override
	public String getLayerDescription() {
		String description = "Layer fills the view with color.";
		return buildLayersDescription(description);
	}

	public static VisLayer create(Color color) {
		return new ColorLayer(color);
	}

}
