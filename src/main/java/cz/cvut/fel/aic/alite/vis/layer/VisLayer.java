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
package cz.cvut.fel.aic.alite.vis.layer;

import cz.cvut.fel.aic.alite.vis.Vis;
import java.awt.Graphics2D;


/**
 * A VisLayer describes one layer of data visualized by the VisManager.
 *
 * Each layer has a paint() method, which is called by the VisManager, if it wants
 * the layer to be drawn. The layer should use the Vis.getCanvas() method and
 * the transformation methods of the Vis singleton to draw its content.
 *
 * Additionally, each logical terminal layer should set a help string by the
 * setHelpOverrideString() method, to be correctly listed in the help layer
 * (if it is present in the VisManager).
 *
 *
 * @author Antonin Komenda
 */
public interface VisLayer {

	public void init(Vis vis);

	public void deinit(Vis vis);

	public void paint(Graphics2D canvas);

	public String getLayerDescription();

	public void setHelpOverrideString(String string);

}
