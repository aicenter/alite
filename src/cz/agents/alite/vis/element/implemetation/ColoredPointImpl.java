package cz.agents.alite.vis.element.implemetation;

import javax.vecmath.Point3d;

import java.awt.Color;

import cz.agents.alite.vis.element.ColoredPoint;

public class ColoredPointImpl implements ColoredPoint
{
	private final Point3d point;
	private final Color color;
	private final int width;
	
	public ColoredPointImpl(Point3d point, Color color, int width)
	{
		//this.point = new Point3d(point.x, point.y, 0);
		this.point = point;
		this.color = color;
		this.width = width;
	}
	
	@Override
	public Point3d getPosition()
	{
		return point;
	}
	@Override
	public Color getColor()
	{
		return color;
	}
	
	@Override
	public int getWidth()
	{
		return width;
	}
}