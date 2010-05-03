package cz.agents.alite.vis.element;

/**
 * The visual elements are abstract descriptions of various objects,
 * which can be drawn.
 *
 * Each element is described only by its interface, and the parameters of the
 * elements defined by the methods in the interface are the only thing needed,
 * so the element can be drawn.
 *
 * Any object implementing the particular element interface, can be consequently
 * used for the visualization. Which means, the data structures (which can be
 * possibly visual elements) of the application do not need to re-instantiated,
 * but can only implement the interface and also be used in the visualization.
 *
 *
 * @author Antonin Komenda
 */
public interface Element {

}
