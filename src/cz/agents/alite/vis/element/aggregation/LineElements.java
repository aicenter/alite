/**
 *
 */
package cz.agents.alite.vis.element.aggregation;

import cz.agents.alite.vis.element.Line;

public interface LineElements extends Elements, StyledElements {

    Iterable<? extends Line> getLines();

}
