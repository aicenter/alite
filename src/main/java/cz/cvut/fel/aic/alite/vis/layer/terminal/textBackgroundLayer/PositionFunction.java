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
 * Implementation of this interface is responsible for getting position of the top left corner of
 * {@link TextBackgroundLayer} rectangle. Various implementations allows anchoring the rectangle to different points
 * in drawing area.
 *
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 * @see TextBackgroundLayer
 */
public interface PositionFunction {

    /**
     * Returns position of the top left corner of {@link TextBackgroundLayer} rectangle
     *
     * @param rectangeWidth    width of the rectangle
     * @param rectangleHeight  height of the rectangle
     * @param drawingDimension dimension of the area in which drawing occures
     * @return position of the top left corner
     */
    public Point getTopLeftPoint(int rectangeWidth, int rectangleHeight, Dimension drawingDimension);

    /**
     * moveLocation method should change internal field of implementation that way,
     * that the drawn rectangle will be drawn on location that differs by {@code deltaX} and {@code deltaY} in
     * horizontal and vertical direction respectively
     *
     * @param deltaX delta in x-coordinates
     * @param deltaY delta in y-coordinates
     */
    public void moveLocation(int deltaX, int deltaY);

}
