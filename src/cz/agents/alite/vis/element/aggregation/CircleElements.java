/**
 *
 */
package cz.agents.alite.vis.element.aggregation;

import cz.agents.alite.vis.element.Circle;

public interface CircleElements extends Elements, StyledElements {

    public Iterable<? extends Circle> getCircles();

}
