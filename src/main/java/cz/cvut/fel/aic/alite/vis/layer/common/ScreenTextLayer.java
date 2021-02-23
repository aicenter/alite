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

import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The layer prints one line of text to the given screen coordinates.
 */
public class ScreenTextLayer extends CommonLayer {

	public static interface TextProvider {
		String getText();
	}

	private final Color color;
	private final int x;
	private final int y;
	private final TextProvider textProvider;

	protected ScreenTextLayer(int x, int y, Color color, TextProvider textProvider) {
		this.textProvider = textProvider;
		this.color = color;
		this.x = x;
		this.y = y;
	}

	@Override
	public void paint(Graphics2D canvas) {
		canvas.setColor(color);
		canvas.drawString(textProvider.getText(),x,y);
	}

	public static VisLayer create(int x, int y, Color color, TextProvider textProvider) {
		return new ScreenTextLayer(x, y, color, textProvider);
	}
}
