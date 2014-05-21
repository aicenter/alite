package cz.agents.alite.vis.visualizable;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public interface Visualizable {

    public void paint(Graphics2D canvas, Rectangle2D drawingRectangle);

}
