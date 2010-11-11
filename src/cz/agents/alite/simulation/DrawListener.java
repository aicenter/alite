package cz.agents.alite.simulation;

/**
 * This interface is used for Simulation. Add this listener, then call
 * 
 * @author Ondrej Milenovsky
 * */
public interface DrawListener {
    /**
     * method called in new thread, limited by timeout, then the simulation
     * starts and any reading of shared object can cause errors
     */
    public void drawFrame(long timeOut);
}
