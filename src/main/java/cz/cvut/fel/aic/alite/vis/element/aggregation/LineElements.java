/**
 *
 */
package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.Line;

public interface LineElements extends StyledElements {

    Iterable<? extends Line> getLines();

}
