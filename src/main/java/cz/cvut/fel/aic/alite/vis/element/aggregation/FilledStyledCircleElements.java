package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.FilledStyledCircle;

public interface FilledStyledCircleElements extends Elements, FilledStyledElements {

    public Iterable<? extends FilledStyledCircle> getCircles();
}
