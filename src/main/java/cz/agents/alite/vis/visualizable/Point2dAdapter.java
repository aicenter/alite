package cz.agents.alite.vis.visualizable;

import javax.vecmath.Point2d;

/**
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public interface Point2dAdapter<T> {

    public Point2d convert(T t);

}
