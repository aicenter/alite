package cz.agents.alite.event;


public interface EventHandler {

    public EventProcessor getSimulation();

    public void handleEvent(Event event);

}
