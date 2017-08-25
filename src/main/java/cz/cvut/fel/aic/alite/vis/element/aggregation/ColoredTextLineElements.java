package cz.cvut.fel.aic.alite.vis.element.aggregation;

import cz.cvut.fel.aic.alite.vis.element.ColoredTextLineElement;

/**
 * Aggregation interface for {@link ColoredTextLineElement}
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 *
 * @see ColoredTextLineElement
 * @see cz.cvut.fel.aic.alite.vis.layer.terminal.textBackgroundLayer.TextBackgroundLayer
 */
public interface ColoredTextLineElements extends Elements {

    /**
     * Returns {@link Iterable} instance. The instance should be immutable.
     * @return {@link Iterable} instance. The instance should be immutable.
     */
    public Iterable<ColoredTextLineElement> getTextLines();

}
