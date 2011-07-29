package cz.agents.alite.common.event;

import cz.agents.alite.common.event.Event;
import cz.agents.alite.common.event.EventHandler;
import cz.agents.alite.common.event.EventProcessor;

// TODO: use JUnit
public class TestEventProcessorDeterministic {

    public static void main(String[] args) {
        final EventProcessor eventProcessor = new EventProcessor();

        for (int i = 10; i < 1000; i++) {
            eventProcessor.addEvent(null, new EventHandler() {

                @Override
                public void handleEvent(Event event) {
                    System.out.println(event);
                }

                @Override
                public EventProcessor getEventProcessor() {
                    return eventProcessor;
                }

            }, null, i, i/10);
        }

        eventProcessor.run();

    }

}
