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

import java.awt.image.BufferedImage;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import cz.cvut.fel.aic.alite.vis.element.Sprite;

public class SpriteImpl extends ImageImpl implements Sprite {

    public final Point3d position;
    public final Vector3d direction;

    public SpriteImpl(Point3d position, Vector3d direction, BufferedImage image) {
        super(image);

        this.position = position;
        this.direction = direction;
    }

    @Override
    public Point3d getPosition() {
        return position;
    }

    @Override
    public Vector3d getDirection() {
        return direction;
    }

}
