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
package cz.cvut.fel.aic.alite.vis.element.implemetation;

import java.awt.Color;

import javax.vecmath.Point3d;

import cz.cvut.fel.aic.alite.vis.element.StyledLine;

public class StyledLineImpl implements StyledLine {

    public final Point3d from;
    public final Point3d to;
    public final Color color;
    public final int width;

    public StyledLineImpl(Point3d from, Point3d to, Color color, int width) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.width = width;
    }

    @Override
    public Point3d getFrom() {
        return from;
    }

    @Override
    public Point3d getTo() {
        return to;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getStrokeWidth() {
        return width;
    }

}
