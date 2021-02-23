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

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.layer.GroupLayer;
import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;

public class VisualInteractionLayer extends CommonLayer {

	private VisualInteractionProvidingEntity entity;
	private MouseInputAdapter mouseListener;

	protected VisualInteractionLayer(VisualInteractionProvidingEntity entity) {
		this.entity = entity;
	}

	@Override
	public void init(Vis vis) {
		super.init(vis);

		mouseListener = new MouseInputAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				double x = Vis.transInvX(e.getX());
				double y = Vis.transInvY(e.getY());
				entity.interactVisually(x, y, e);
			}

		};
		vis.addMouseListener(mouseListener);
	}

	@Override
	public void deinit(Vis vis) {
		super.deinit(vis);

		vis.removeMouseListener(mouseListener);
	}

	public static VisLayer create(final VisualInteractionProvidingEntity entity) {
		GroupLayer group = GroupLayer.create();

		// interaction
		group.addSubLayer(new VisualInteractionLayer(entity));

		return group;
	}

	public static interface VisualInteractionProvidingEntity {

		public String getName();
		public void interactVisually(double x, double y, MouseEvent e);
	}

}
