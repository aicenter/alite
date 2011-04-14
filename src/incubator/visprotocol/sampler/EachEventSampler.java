package incubator.visprotocol.sampler;

import cz.agents.alite.simulation.EventListener;
import cz.agents.alite.simulation.Simulation;

/**
 * Samples each change in environment
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class EachEventSampler implements Sampler, EventListener {

    private final Simulation simulation;
    
    public EachEventSampler(Simulation simulation) {
        this.simulation = simulation;
    }
    
    @Override
    public void eventFired() {
        sample();
    }

    @Override
    public void start() {
        simulation.addEventListener(this);
    }

    @Override
    public void stop() {
        simulation.removeEventListener(this);
    }
    
    protected abstract void sample();
}
