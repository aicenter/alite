package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.StyledPoint;

/**
 * @author Ondrej Milenovsky
 */
public interface StyledPointElements extends Elements {

    Iterable<? extends StyledPoint> getPoints();

}
