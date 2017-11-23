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
package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.ColoredTextLineElement;

/**
 * Aggregation interface for {@link ColoredTextLineElement}
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 *
 * @see ColoredTextLineElement
 * @see cz.cvut.fel.aic.alite.vis.layer.terminal.textBackgroundLayer.TextBackgroundLayer
 */
public interface ColoredTextLineElements extends Elements {

    /**
     * Returns {@link Iterable} instance. The instance should be immutable.
     * @return {@link Iterable} instance. The instance should be immutable.
     */
    public Iterable<ColoredTextLineElement> getTextLines();

}
