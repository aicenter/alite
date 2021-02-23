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

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.Line;
import cz.cvut.fel.aic.alite.vis.element.aggregation.LineElements;
import java.awt.Graphics2D;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

public class ArrowLayer extends LineLayer {
// TODO: should be an extender of the LineLayer (inheritance is bad here!)

	protected ArrowLayer(LineElements lineElements) {
		super(lineElements);
	}

	@Override
	protected void onEachLine(Graphics2D canvas, Line line) {
		int x1 = Vis.transX(line.getFrom().x);
		int y1 = Vis.transY(line.getFrom().y);
		int x2 = Vis.transX(line.getTo().x);
		int y2 = Vis.transY(line.getTo().y);

		Vector3d arrowPart1 = new Vector3d(x2 - x1, y2 - y1, 0);
		Vector3d arrowPart2 = new Vector3d(arrowPart1);
		Matrix4d transf1 = new Matrix4d();
		transf1.rotZ(5 * Math.PI / 6);
		transf1.transform(arrowPart1);
		transf1.rotZ(7 * Math.PI / 6);
		transf1.transform(arrowPart2);
		arrowPart1.normalize();
		arrowPart1.scale(10);

		arrowPart2.normalize();
		arrowPart2.scale(10);

		canvas.drawLine(x2, y2, x2 + (int) arrowPart1.x, y2 + (int) arrowPart1.y);
		canvas.drawLine(x2, y2, x2 + (int) arrowPart2.x, y2 + (int) arrowPart2.y);
	}

	public static ArrowLayer create(LineElements lineElements) {
		return new ArrowLayer(lineElements);
	}

}
