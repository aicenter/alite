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

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.Circle;
import cz.cvut.fel.aic.alite.vis.element.aggregation.CircleElements;

public class CircleLayer extends TerminalLayer {

	private final CircleElements circleElements;

	protected CircleLayer(CircleElements circleElements) {
		this.circleElements = circleElements;
	}

	@Override
	public void paint(Graphics2D canvas) {
		canvas.setColor(circleElements.getColor());
		canvas.setStroke(new BasicStroke(circleElements.getStrokeWidth()));
		Dimension dim = Vis.getDrawingDimension();

		for (Circle circle: circleElements.getCircles()) {
			int x1 = Vis.transX(circle.getPosition().x - circle.getRadius());
			int y1 = Vis.transY(circle.getPosition().y - circle.getRadius());
			int x2 = Vis.transX(circle.getPosition().x + circle.getRadius());
			int y2 = Vis.transY(circle.getPosition().y + circle.getRadius());

			if (x1 > x2) {
				int tmp = x2;
				x2 = x1;
				x1 = tmp;
			}
			if (y1 > y2) {
				int tmp = y2;
				y2 = y1;
				y1 = tmp;
			}

			int diameterW = Vis.transW(circle.getRadius() * 2.0);
			int diameterH = Vis.transH(circle.getRadius() * 2.0);
			if (x2 > 0 && x1 < dim.width  && y2 > 0 && y1 < dim.height) {
				canvas.drawOval(x1, y1, diameterW, diameterH);
			}
		}
	}

	public static CircleLayer create(CircleElements circleElements) {
		return new CircleLayer(circleElements);
	}

}
