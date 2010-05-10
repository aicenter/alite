package cz.agents.alite.vis.element;

import java.awt.Color;
import java.awt.Point;


/**
 * @author Ondrej Milenovsky
 * */
public interface ColoredPoint extends cz.agents.alite.vis.element.Point
{
	public Color getColor();
	public int getWidth();
}
