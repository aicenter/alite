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
package cz.cvut.fel.aic.alite.vis.element;

import cz.cvut.fel.aic.alite.vis.element.aggregation.Elements;

/**
 * The visual elements are abstract descriptions of various objects,
 * which can be drawn.
 *
 * Each element is described only by its interface, and the parameters of the
 * elements defined by the methods in the interface are the only thing needed,
 * so the element can be drawn.
 *
 * Any object implementing the particular element interface, can be consequently
 * used for the visualization. Which means, the data structures (which can be
 * possibly visual elements) of the application do not need to re-instantiated,
 * but can only implement the interface and also be used in the visualization.
 *
 * Each element has its own default implementation in the <code>implementation</code>
 * sub-package. The default implementations can be used, if there is no explicit
 * data structure representing the visual element in the application.
 *
 * Groups of the elements are described by their aggregations
 * (see the {@link Elements} interface).
 *
 *
 * @author Antonin Komenda
 */
public interface Element {

}
