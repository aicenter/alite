package cz.cvut.fel.aic.alite.vis.layer.terminal;

import cz.cvut.fel.aic.alite.vis.element.Element;
import cz.cvut.fel.aic.alite.vis.element.Point;
import cz.cvut.fel.aic.alite.vis.element.aggregation.PointElements;
import cz.cvut.fel.aic.alite.vis.layer.AbstractLayer;

/**
 * The TerminalLayers draw various visual elements on the canvas of Vis singleton.
 *
 * The particular TerminalLayers copies the structure of the visual {@link Element}s.
 *
 * A terminal layer (if ever) has only one factory method create(). This method,
 * by convention, has only one parameter, which is the aggregated version of an
 * element interface, which the layer can draw. For instance, The create() method
 * of the {@link PointLayer}, has one parameter of type {@link PointElements},
 * which is a aggregated version of the {@link Point} element definition interface.
 *
 *
 * @author Antonin Komenda
 */
public abstract class TerminalLayer extends AbstractLayer {
}
