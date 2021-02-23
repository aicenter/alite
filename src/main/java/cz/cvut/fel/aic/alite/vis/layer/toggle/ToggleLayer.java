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
package cz.cvut.fel.aic.alite.vis.layer.toggle;

import cz.cvut.fel.aic.alite.vis.layer.GroupLayer;
import java.awt.Graphics2D;

/**
 * A ToggleLayer can simply switch, if the sub-layers are drawn or not.
 *
 * The switching can be done by calling of the setEnabled() method.
 *
 *
 * @author Antonin Komenda
 */
public class ToggleLayer extends GroupLayer {

	private boolean enabled = true;

	public ToggleLayer() {
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void paint(Graphics2D canvas) {
		if (enabled) {
			super.paint(canvas);
		}
	}

}
