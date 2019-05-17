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

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.StyledLine;
import cz.cvut.fel.aic.alite.vis.element.aggregation.StyledLineElements;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class StyledLineLayer extends TerminalLayer {

	private final StyledLineElements lineElements;

	protected StyledLineLayer(StyledLineElements lineElements) {
		this.lineElements = lineElements;
	}

	@Override
	public void paint(Graphics2D canvas) {
		canvas.setStroke(new BasicStroke(1));

		Dimension dim = Vis.getDrawingDimension();
		Rectangle2D drawingRectangle = new Rectangle(dim);

		for (StyledLine line : lineElements.getLines()) {
			drawLine(line, canvas, drawingRectangle);
		}
	}

	private void drawLine(StyledLine line, Graphics2D canvas, Rectangle2D drawingRectangle) {
		canvas.setColor(line.getColor());
		canvas.setStroke(new BasicStroke(line.getStrokeWidth()));

		int x = Vis.transX(line.getFrom().x);
		int y = Vis.transY(line.getFrom().y);
		int xTo = Vis.transX(line.getTo().x);
		int yTo = Vis.transY(line.getTo().y);

		Line2D line2d = new Line2D.Double(x, y, xTo, yTo);

		if (line2d.intersects(drawingRectangle)) {
			canvas.draw(line2d);
		}
	}

	@Override
	public String getLayerDescription() {
		String description = "Layer shows lines with different colors and widths.";
		return buildLayersDescription(description);
	}

	public static StyledLineLayer create(StyledLineElements lineElements) {
		return new StyledLineLayer(lineElements);
	}

}
