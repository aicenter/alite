package cz.agents.alite.vis.visualizable;

/**
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public interface VisualizableProvider {

    public Iterable<? extends Visualizable> getData();

}
