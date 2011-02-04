/**
 *
 */
package cz.agents.alite.vis.element.aggregation;

import cz.agents.alite.vis.element.Point;

public interface PointElements extends Elements, StyledElements {

    Iterable<? extends Point> getPoints();

}
