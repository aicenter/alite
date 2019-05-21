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
package cz.cvut.fel.aic.alite.vis.layer.common;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.Image;
import cz.cvut.fel.aic.alite.vis.element.aggregation.ImageElements;
import cz.cvut.fel.aic.alite.vis.element.implemetation.ImageImpl;
import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import cz.cvut.fel.aic.alite.vis.layer.terminal.ImageLayer;

public class LogoLayer extends ImageLayer {

	public LogoLayer(final BufferedImage image) {
		super(new ImageElements() {

			@Override
			public Iterable<? extends Image> getImages() {
				return Arrays.asList(new ImageImpl(image));
			}

		});

	}

	@Override
	public void paint(Graphics2D canvas) {
		Dimension dim = Vis.getDrawingDimension();
		for (Image image : getImageElements().getImages()) {
			canvas.drawImage(image.getImage(), dim.width - image.getImage().getWidth() - 25, -10, null);
		}
	}

	@Override
	public String getLayerDescription() {
		String description = "[Logo] The layer shows logo.";
		return buildLayersDescription(description);
	}

	public static VisLayer create(BufferedImage image) {
		return new LogoLayer(image);
	}

	public static VisLayer create(File file) {
		return create(ImageLayer.loadImage(file));
	}

	public static VisLayer create(URL file) {
		return create(ImageLayer.loadImage(file));
	}

}
