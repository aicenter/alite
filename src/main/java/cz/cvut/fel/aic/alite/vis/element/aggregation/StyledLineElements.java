/**
 *
 */
package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.StyledLine;

public interface StyledLineElements extends Elements {

    Iterable<? extends StyledLine> getLines();

}
