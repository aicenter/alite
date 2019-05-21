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
package cz.cvut.fel.aic.alite.vis.layer.terminal;

import cz.cvut.fel.aic.alite.vis.element.Element;
import cz.cvut.fel.aic.alite.vis.element.Point;
import cz.cvut.fel.aic.alite.vis.element.aggregation.PointElements;
import cz.cvut.fel.aic.alite.vis.layer.AbstractLayer;

/**
 * The TerminalLayers draw various visual elements on the canvas of Vis singleton.
 *
 * The particular TerminalLayers copies the structure of the visual {@link Element}s.
 *
 * A terminal layer (if ever) has only one factory method create(). This method,
 * by convention, has only one parameter, which is the aggregated version of an
 * element interface, which the layer can draw. For instance, The create() method
 * of the {@link PointLayer}, has one parameter of type {@link PointElements},
 * which is a aggregated version of the {@link Point} element definition interface.
 *
 *
 * @author Antonin Komenda
 */
public abstract class TerminalLayer extends AbstractLayer {
}
