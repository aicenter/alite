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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cz.cvut.fel.aic.alite.vis.Vis;

/**
 * A default implementation of the {@link GroupVisLayer} interface.
 *
 * @author Antonin Komenda
 */
public class GroupLayer extends AbstractLayer implements GroupVisLayer {

    protected final LinkedList<VisLayer> subLayers = new LinkedList<VisLayer>();

    protected GroupLayer() {
    }

    public LinkedList<VisLayer> getSubLayers() {
        return subLayers;
    }

    @Override
    public void init(Vis vis) {
        for (VisLayer layer : getSubLayers()) {
            layer.init(vis);
        }
    }

    @Override
    public void deinit(Vis vis) {
        for (VisLayer layer : getSubLayers()) {
            layer.deinit(vis);
        }
    }

    @Override
    public void addSubLayer(VisLayer layer) {
        subLayers.add(layer);
    }

    @Override
    public void removeSubLayer(VisLayer layer) {
        subLayers.remove(layer);
    }

    @Override
    public void paint(Graphics2D canvas) {
        // TODO slow, do it better way
        List<VisLayer> toIterateThrough = new ArrayList<VisLayer>(subLayers);
        for (VisLayer layer : toIterateThrough) {
            layer.paint(canvas);
        }
    }

    @Override
    public String getLayerDescription() {
        String description = "All sub-layers are always shown:";
        return buildLayersDescription(description);
    }

    public static GroupLayer create() {
        return new GroupLayer();
    }

    protected String buildLayersDescription(String description) {
        if (getHelpOverrideString() != null) {
            return getHelpOverrideString();
        }

        for (VisLayer layer : subLayers) {
            if (!layer.getLayerDescription().isEmpty()) {
                description += "<br/>   "
                        + layer.getLayerDescription().replace("   ", "      ").replace("\n",
                                "\n   ");
            }
        }
        return description;
    }

    public void clear() {
        subLayers.clear();
    }

}
