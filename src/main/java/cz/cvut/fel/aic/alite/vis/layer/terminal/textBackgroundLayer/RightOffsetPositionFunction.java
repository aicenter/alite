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
package cz.cvut.fel.aic.alite.vis.layer.terminal.textBackgroundLayer;

import java.awt.Dimension;
import java.awt.Point;

/**
 * This implementation will keep drawn rectangle by given distance from right border of canvas. The distance is
 * between right border of drawn rectangle and right border of canvas.
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public class RightOffsetPositionFunction implements PositionFunction {

	private int rightOffset;
	private int y;

	/**
	 * @param rightOffset distance between right edge of the drawn rectangle and right border of canvas
	 * @param y y-coordinate of the top edge of the drawn rectangle
	 */
	public RightOffsetPositionFunction(int rightOffset, int y) {
		this.rightOffset = rightOffset;
		this.y = y;
	}

	@Override
	public Point getTopLeftPoint(int rectangeWidth, int rectangleHeight, Dimension drawingDimension) {
		return new Point((int) (drawingDimension.getWidth() - rightOffset - rectangeWidth), y);
	}

	@Override
	public void moveLocation(int deltaX, int deltaY) {
		rightOffset -= deltaX;
		y += deltaY;
	}
}
