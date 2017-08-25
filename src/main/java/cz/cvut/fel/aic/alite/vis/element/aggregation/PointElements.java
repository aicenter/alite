/**
 *
 */
package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.Point;

public interface PointElements extends StyledElements {

    Iterable<? extends Point> getPoints();

}
