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
package cz.cvut.fel.aic.alite.vis.layer.terminal;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.StyledPoint;
import cz.cvut.fel.aic.alite.vis.element.aggregation.StyledPointElements;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * same as point layer but different colors and widths for points
 * 
 * count of widths and colors should be smaller than points, the remaining points stay same style
 * like the last one
 * 
 * @author Ondrej Milenovsky
 */
public class StyledPointLayer extends TerminalLayer {

	private final StyledPointElements pointElements;

	protected StyledPointLayer(StyledPointElements pointElements) {
		this.pointElements = pointElements;
	}

	@Override
	public void paint(Graphics2D canvas) {
		canvas.setStroke(new BasicStroke(1));
		Dimension dim = Vis.getDrawingDimension();
		for (StyledPoint point : pointElements.getPoints()) {
			drawPoint(point, canvas, dim);
		}
	}

	private void drawPoint(StyledPoint point, Graphics2D canvas, Dimension dim) {
		canvas.setColor(point.getColor());
		int radius = point.getWidth() / 2;

		int x1 = Vis.transX(point.getPosition().x) - radius;
		int y1 = Vis.transY(point.getPosition().y) - radius;
		int x2 = Vis.transX(point.getPosition().x) + radius;
		int y2 = Vis.transY(point.getPosition().y) + radius;
		if (x2 > 0 && x1 < dim.width && y2 > 0 && y1 < dim.height) {
			canvas.fillOval(x1, y1, point.getWidth(), point.getWidth());
		}

	}

	@Override
	public String getLayerDescription() {
		String description = "Layer shows points with different colors and widths.";
		return buildLayersDescription(description);
	}

	public static StyledPointLayer create(StyledPointElements pointElements) {
		return new StyledPointLayer(pointElements);
	}

}
