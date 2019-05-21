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
package cz.cvut.fel.aic.alite.vis.layer.terminal.textBackgroundLayer;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.element.ColoredTextLineElement;
import cz.cvut.fel.aic.alite.vis.element.aggregation.ColoredTextLineElements;
import cz.cvut.fel.aic.alite.vis.layer.DraggableLayer;
import cz.cvut.fel.aic.alite.vis.layer.terminal.TerminalLayer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>This layer provides suppor for drawing text inside colored box. The box size is streched in order to contain
 * whole text dynamically. The text is represented by {@link ColoredTextLineElements} class and therefore the text
 * can have different color for each line. </p>
 *
 * <p>The box can be parametrized by color and padding. The position of the box can is specified by implementation
 * of {@link PositionFunction}, which allows custom positiong. The TextBackgroundLayer implements {@link
 * DraggableLayer.Draggable} interface.</p>
 *
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 * @see TextBackgroundLayerBuilder
 * @see PositionFunction
 * @see ColoredTextLineElements
 * @see DraggableLayer.Draggable
 */
public class TextBackgroundLayer extends TerminalLayer implements DraggableLayer.Draggable {

	private final int leftPadding;
	private final int rightPadding;
	private final int bottomPadding;
	private final int topPadding;

	private final ColoredTextLineElements coloredTextLineElements;
	private final PositionFunction positionFunction;
	private final Color backgroundColor;

	private Rectangle rectangle;
	private boolean active = false;

	/**
	 * Default implementation of factory method. It is recommended to use {@link TextBackgroundLayerBuilder} instead.
	 * @param coloredTextLineElements elements that will be displayed
	 * @param positionFunction position function determing position of the box
	 * @param backgroundColor background color of the box
	 * @param leftPadding distance between left edge of the box and text
	 * @param rightPadding distance between right edge of the box and text
	 * @param bottomPadding distance between bottom edge of the box and text
	 * @param topPadding distance between top edge of the box and text
	 * @return an instance of the class
	 */
	public static TextBackgroundLayer create(ColoredTextLineElements coloredTextLineElements,
											 PositionFunction positionFunction, Color backgroundColor, int leftPadding,
											 int rightPadding, int bottomPadding, int topPadding) {
		return new TextBackgroundLayer(coloredTextLineElements, positionFunction, backgroundColor, leftPadding,
				rightPadding, bottomPadding, topPadding);
	}

	private TextBackgroundLayer(ColoredTextLineElements coloredTextLineElements,
								PositionFunction positionFunction, Color backgroundColor, int leftPadding,
								int rightPadding, int bottomPadding, int topPadding) {
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		this.bottomPadding = bottomPadding;
		this.topPadding = topPadding;
		this.coloredTextLineElements = coloredTextLineElements;
		this.positionFunction = positionFunction;
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void paint(Graphics2D canvas) {

		final Iterator<ColoredTextLineElement> textLinesIterator =
				coloredTextLineElements.getTextLines().iterator();

		if (textLinesIterator.hasNext() == false) {
			active = false;
			return ;
		}

		active = true;


		FontMetrics fontMetrics = canvas.getFontMetrics();
		List<ColoredTextLineElement> textLines = new ArrayList<ColoredTextLineElement>();
		int maxLineSize = 0;

		while (textLinesIterator.hasNext()) {
			ColoredTextLineElement element = textLinesIterator.next();

			textLines.add(element);

			maxLineSize = checkMaxLineSize(maxLineSize, fontMetrics, element.getTextLine());
		}

		int yStep = fontMetrics.getHeight();

		int width = leftPadding + maxLineSize + rightPadding;
		int height = textLines.size() * yStep + topPadding + bottomPadding;

		Dimension drawingDimension = Vis.getDrawingDimension();
		Point topLeft = positionFunction.getTopLeftPoint(width, height, drawingDimension);

		if (rectangle == null) {
			rectangle = new Rectangle(topLeft);
		}

		rectangle.setLocation(topLeft);
		rectangle.setSize(width, height);
		int x = topLeft.x;
		int y = topLeft.y;

		canvas.setColor(backgroundColor);
		canvas.fill(rectangle);

		x += leftPadding;
		y += topPadding + fontMetrics.getAscent();

		for (ColoredTextLineElement textLine : textLines) {
			canvas.setColor(textLine.getColor());
			canvas.drawString(textLine.getTextLine(), x, y);
			y += yStep;

		}
	}

	private int checkMaxLineSize(int maxLineSize, FontMetrics fontMetrics, String line) {
		return Math.max(maxLineSize, fontMetrics.stringWidth(line));
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public boolean isInArea(int x, int y) {
		return rectangle != null && rectangle.contains(x, y);
	}

	@Override
	public void moveLocation(int deltaX, int deltaY) {
		positionFunction.moveLocation(deltaX, deltaY);
	}


}
