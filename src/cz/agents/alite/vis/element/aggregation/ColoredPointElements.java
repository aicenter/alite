/**
 *
 */
package cz.agents.alite.vis.element.aggregation;

import cz.agents.alite.vis.element.ColoredPoint;

/**
 * @author Ondrej Milenovsky
 * */
public interface ColoredPointElements extends Elements
{

	Iterable<? extends ColoredPoint> getPoints();

}
