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
package cz.cvut.fel.aic.alite.creator;

import cz.cvut.fel.aic.alite.simulation.Simulation;

/**
 * A Creator encapsulates code that creates the application universe and runs
 * the play in it.
 *
 * The universe is a data composition of various elements describing the
 * existing stuff in the application.
 * The particular elements in the universe and their interactions are not
 * strictly pre-defined, so that they can be arbitrarily designed
 * for each application and its needs.
 *
 * The typical elements of the universe are: {@link Environment},
 * {@link Simulation}, agents, communication infrastructure, and similar.
 *
 *
 * @author Antonin Komenda
 */
public interface Creator {

    public void init(String[] args);

    public void create();

}
