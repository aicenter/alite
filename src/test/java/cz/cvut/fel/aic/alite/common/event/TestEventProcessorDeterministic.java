package cz.cvut.fel.aic.alite.common.event;

import static org.junit.Assert.* ;

import org.junit.Test;

import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessor;

public class TestEventProcessorDeterministic {

    @Test
    public void sameTimeEventOrdering() {
        final Counter counter = new Counter(10);
        final EventProcessor eventProcessor = new EventProcessor();

        for (int i = 10; i <= 1000; i++) {
            eventProcessor.addEvent(null, new EventHandler() {

                @Override
                public void handleEvent(Event event) {
                    if ((Integer) event.getContent() / 10 != counter.value / 10) {
                        assertTrue(false);
                        return;
                    }
                    counter.value++;
                }

                @Override
                public EventProcessor getEventProcessor() {
                    return eventProcessor;
                }

            }, null, i, i/10);
        }

        eventProcessor.run();

        assertTrue(true);
    }

    private static class Counter {

        int value = 10;

        public Counter(int value) {
            this.value = value;
        }

    }

}
