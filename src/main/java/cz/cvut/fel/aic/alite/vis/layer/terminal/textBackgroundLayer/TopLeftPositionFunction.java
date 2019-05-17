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
package cz.cvut.fel.aic.alite.vis.layer.terminal.textBackgroundLayer;

import java.awt.Dimension;
import java.awt.Point;

/**
* This implementation will keep drawn rectangle's top left corner in given point.
* @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
*/
public class TopLeftPositionFunction implements PositionFunction {

	private Point point;

	/**
	 *
	 * @param x x-coordinate of top left corner
	 * @param y y-coordinate of top left corner
	 */
	public TopLeftPositionFunction(int x, int y) {
		point = new Point(x, y);
	}

	@Override
	public Point getTopLeftPoint(int rectangeWidth, int rectangleHeight, Dimension drawingDimension) {
		return point;
	}

	@Override
	public void moveLocation(int deltaX, int deltaY) {
		point = new Point(point.x + deltaX, point.y + deltaY);
	}
}
