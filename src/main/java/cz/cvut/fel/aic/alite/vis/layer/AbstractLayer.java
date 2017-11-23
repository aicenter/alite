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
package cz.cvut.fel.aic.alite.vis.layer;

import java.awt.Graphics2D;

import cz.cvut.fel.aic.alite.vis.Vis;

/**
 * A default implementation of the {@link VisLayer} interface.
 *
 * @author Antonin Komenda
 */
public abstract class AbstractLayer implements VisLayer {

    private String helpOverrideString = null;

    @Override
    public void init(Vis vis) {
    }

    @Override
    public void deinit(Vis vis) {
    }

    @Override
    public void paint(Graphics2D canvas) {
    }

    @Override
    public String getLayerDescription() {
    return buildLayersDescription(getClass().toString() + " layer\n");
    }

    @Override
    public void setHelpOverrideString(String helpOverrideString) {
    this.helpOverrideString = helpOverrideString;
    }

    protected String getHelpOverrideString() {
    return helpOverrideString;
    }

    protected String buildLayersDescription(String description) {
    if (helpOverrideString != null) {
        return helpOverrideString;
    }
    return description;
    }

}
