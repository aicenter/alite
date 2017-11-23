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

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.vecmath.Vector3d;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.Sprite;
import cz.cvut.fel.aic.alite.vis.element.aggregation.SpriteElements;

public class SpriteLayer extends TerminalLayer {

    private final SpriteElements spriteElements;

    SpriteLayer(SpriteElements spriteElements) {
        this.spriteElements = spriteElements;
    }

    @Override
    public void paint(Graphics2D canvas) {
        Dimension dim = Vis.getDrawingDimension();
        for (Sprite sprite : spriteElements.getSprites()) {
            int x1 = Vis.transX(sprite.getPosition().x) - sprite.getImage().getWidth() / 2;
            int y1 = Vis.transY(sprite.getPosition().y) - sprite.getImage().getHeight() / 2;
            int x2 = Vis.transX(sprite.getPosition().x) + sprite.getImage().getWidth() / 2;
            int y2 = Vis.transY(sprite.getPosition().y) + sprite.getImage().getHeight() / 2;
            if (x2 > 0 && x1 < dim.width  && y2 > 0 && y1 < dim.height) {
                Vector3d vec = sprite.getDirection();

                AffineTransform transform = AffineTransform.getTranslateInstance(x1, y1);
                transform.concatenate(AffineTransform.getRotateInstance(vec.x, vec.y,
                        sprite.getImage().getWidth() / 2, sprite.getImage().getHeight() / 2));
                canvas.drawImage(sprite.getImage(), transform, null);
            }
        }
    }

    @Override
    public String getLayerDescription() {
        String description = "Layer shows image spritesO.";
        return buildLayersDescription(description);
    }

    public static SpriteLayer create(SpriteElements spriteElements) {
        return new SpriteLayer(spriteElements);
    }

}
