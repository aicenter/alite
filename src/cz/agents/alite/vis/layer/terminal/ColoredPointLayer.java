package cz.agents.alite.vis.layer.terminal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.ColoredPoint;
import cz.agents.alite.vis.element.aggregation.ColoredPointElements;

/**
 * same as point layer but different colors and widths for points
 * 
 * count of widths and colors should be smaller than points, the remaining
 * points stay same style like the last one
 * 
 * @author Ondrej Milenovsky
 * */
public class ColoredPointLayer extends TerminalLayer
{

	private final ColoredPointElements pointElements;

	protected ColoredPointLayer(ColoredPointElements pointElements)
	{
		this.pointElements = pointElements;
	}

	@Override
	public void paint(Graphics2D canvas)
	{

		canvas.setStroke(new BasicStroke(1));
		int radius = 10;

		for(ColoredPoint p: pointElements.getPoints())
		{
			canvas.setColor(p.getColor());
			radius = (int) (p.getWidth() / 2.0);

			int x = Vis.transX(p.getPosition().x);
			int y = Vis.transY(p.getPosition().y);
			// if (x > 0 && x < Vis.getDrawingDimension().width && y > 0 && y <
			// Vis.getDrawingDimension().height) {
			canvas.drawOval(x - radius, y - radius, radius * 2, radius * 2);
			canvas.fillOval(x - radius, y - radius, radius * 2, radius * 2);

		}

	}

	@Override
	public String getLayerDescription()
	{
		String description = "Layer shows points with different colors and widths.";
		return buildLayersDescription(description);
	}

	public static ColoredPointLayer create(ColoredPointElements pointElements)
	{
		return new ColoredPointLayer(pointElements);
	}

}
