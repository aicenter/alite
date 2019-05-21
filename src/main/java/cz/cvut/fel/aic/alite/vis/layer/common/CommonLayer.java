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

import cz.cvut.fel.aic.alite.vis.layer.AbstractLayer;
import cz.cvut.fel.aic.alite.vis.layer.terminal.TerminalLayer;
import cz.cvut.fel.aic.alite.vis.layer.toggle.KeyToggleLayer;

/**
 * A CommonLayer is typically a wrapper or composition of several
 * {@link TerminalLayer}s.
 *
 * Each non-terminal layer, should be a CommonLayer. The common layers typically
 * have various create() factory methods, to cover the needs of the layer users.
 * The create() method(s) of a common layer typically do not return a object of
 * the type the particular layer, but rather a toggle layer, which turns on and off the layer
 * e.g., by a key. For example, the create() method of the {@link FpsLayer}
 * do not return <code>new {@link FpsLayer}(...)</code>, but a new instance of
 * the {@link KeyToggleLayer}, that shows the layer by pressing the 'f' key.
 *
 *
 * @author Antonin Komenda
 */
public abstract class CommonLayer extends AbstractLayer {
}
