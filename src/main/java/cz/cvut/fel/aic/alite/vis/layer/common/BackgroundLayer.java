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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.Image;
import cz.cvut.fel.aic.alite.vis.element.aggregation.ImageElements;
import cz.cvut.fel.aic.alite.vis.element.implemetation.ImageImpl;
import cz.cvut.fel.aic.alite.vis.layer.GroupLayer;
import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import cz.cvut.fel.aic.alite.vis.layer.terminal.ImageLayer;
import cz.cvut.fel.aic.alite.vis.layer.toggle.KeyToggleLayer;
import cz.cvut.fel.aic.alite.vis.layer.toggle.LodToggleLayer;

public class BackgroundLayer extends ImageLayer {

	protected BackgroundLayer(final BufferedImage image) {
		super(new ImageElements() {

			@Override
			public Iterable<? extends Image> getImages() {
				return Arrays.asList(new ImageImpl(image));
			}

		});
	}

	@Override
	public void paint(Graphics2D canvas) {
		Rectangle worldBounds = Vis.getWorldBounds();
		for (Image image : getImageElements().getImages()) {
			canvas.drawImage(image.getImage(), Vis.transX(worldBounds.getX()), Vis.transY(worldBounds.getY()), Vis.transW(worldBounds.getWidth()), Vis.transH(worldBounds.getHeight()), null);
		}
	}

	@Override
	public String getLayerDescription() {
		String description = "Layer contains background image.";
		return buildLayersDescription(description);
	}

	public static VisLayer create(BufferedImage image, String toggleKey) {
		VisLayer background = new BackgroundLayer(image);
		background.setHelpOverrideString("[Blockades] The layer shows ground blockades in form of a bitmap (black = blockade).");

		KeyToggleLayer toggle = KeyToggleLayer.create(toggleKey);
		toggle.addSubLayer(background);

		return toggle;
	}

	/**
	 * Simple thresholded double LOD background.
	 *
	 * The layer shows the <code>detailedImage</code>, if the zoom of the Vis view
	 * is greater then the <code>zoomThreshold</code>. Otherwise, the image is shown.
	 */
	public static VisLayer create(File detailedImage, File image, double zoomThreshold) {
		LodToggleLayer toggleDetailed = LodToggleLayer.create(Double.POSITIVE_INFINITY, zoomThreshold);
		toggleDetailed.addSubLayer(new BackgroundLayer(loadImage(detailedImage)));

		LodToggleLayer toggle = LodToggleLayer.create(zoomThreshold, 0);
		toggle.addSubLayer(new BackgroundLayer(loadImage(image)));

		GroupLayer group = GroupLayer.create();
		group.setHelpOverrideString("[Background] The layer shows informational image of the environment.");
		group.addSubLayer(toggleDetailed);
		group.addSubLayer(toggle);

		return group;
	}

	public static VisLayer create(BufferedImage image) {
		return new BackgroundLayer(image);
	}

	public static VisLayer create(File file) {
		return create(loadImage(file));
	}

}
