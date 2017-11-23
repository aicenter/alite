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

import cz.cvut.fel.aic.alite.vis.element.FilledStyledCircle;

public class FilledStyledCircleImpl implements FilledStyledCircle {

    public final Point3d position;
    public final double radius;
    public final Color color;
    public final Color fillColor;

    public FilledStyledCircleImpl(Point3d position, double radius, Color color, Color fillColor) {
        this.position = position;
        this.radius = radius;
        this.color = color;
        this.fillColor = fillColor;
    }

    @Override
    public Point3d getPosition() {
        return position;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public Color getFillColor() {
        return null;
    }

}
