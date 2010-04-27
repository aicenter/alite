package cz.agents.alite.event;


public interface EventHandler {

    public EventProcessor getEventProcessor();

    public void handleEvent(Event event);

}
