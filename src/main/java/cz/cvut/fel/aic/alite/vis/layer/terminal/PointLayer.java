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
package cz.cvut.fel.aic.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.Point;
import cz.cvut.fel.aic.alite.vis.element.aggregation.PointElements;

public class PointLayer extends TerminalLayer {

	private final PointElements pointElements;

	protected PointLayer(PointElements pointElements) {
		this.pointElements = pointElements;
	}

	@Override
	public void paint(Graphics2D canvas) {
		int radius = (int) (pointElements.getStrokeWidth() / 2.0);
		canvas.setColor(pointElements.getColor());
		canvas.setStroke(new BasicStroke(1));
		Dimension dim = Vis.getDrawingDimension();

		for (Point point : pointElements.getPoints()) {

			int x1 = Vis.transX(point.getPosition().x) - radius;
			int y1 = Vis.transY(point.getPosition().y) - radius;
			int x2 = Vis.transX(point.getPosition().x) + radius;
			int y2 = Vis.transY(point.getPosition().y) + radius;
			if (x2 > 0 && x1 < dim.width && y2 > 0 && y1 < dim.height) {
				canvas.fillOval(x1, y1, radius * 2, radius * 2);
			}
		}
	}

	@Override
	public String getLayerDescription() {
		String description = "Layer shows points.";
		return buildLayersDescription(description);
	}

	public static PointLayer create(PointElements pointElements) {
		return new PointLayer(pointElements);
	}

}
