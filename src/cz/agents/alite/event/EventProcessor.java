package cz.agents.alite.event;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The event processor is a wrapper for an event-queue cycle.
 *
 * On the fly, the processor lines up events added into it, and "sends" them
 * to their particular recipients in a particular order. By sending,
 * it is meant to call a {@link EventHandler}.handleEvent() method, with the
 * event as an argument. An event handler needs to be registered into the
 * processor to receive the events.
 *
 * The event is sent to all event handlers registered with the EventProcessor,
 * if its recipient is <code>null</code> and it is sent to the particular event
 * handler otherwise.
 *
 * The event-queue cycle can be paused/unpaused by the setRunning() method.
 * By the default, the cycle is started running.
 *
 * The time of currently processed events can be obtained by the getCurrentTime()
 * method.
 *
 *
 * @author Antonin Komenda
 */
public class EventProcessor {

    private boolean running = true;
    private boolean finished = false;
    private final Queue<Event> eventQueue = new PriorityQueue<Event>();
    private final List<EventHandler> entityList = new LinkedList<EventHandler>();
    private Thread thread = Thread.currentThread();
    private long currentTime = 0;

    public void run() {
        Event event = eventQueue.poll();
        while (event != null) {
            breforeRunningTest(event);

            currentTime = event.getTime();

            while (!running) {
                synchronized (thread) {
                    try {
                        thread.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(EventProcessor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            fireEvent(event);

            event = eventQueue.poll();
        }
        finished = true;
        running = false;
    }

    public void addEvent(EventType type, EventHandler recipient, String owner, Object content) {
        addEvent(type, recipient, owner, content, 1);
    }

    // TODO: refactorize the recipients/owners/senders/groups and similar
    public void addEvent(EventType type, EventHandler recipient, String owner, Object content, long deltaTime) {
        if (deltaTime < 1) {
            throw new IllegalArgumentException("deltaTime must be greater then zero!");
        }
        Event event = new Event(currentTime + deltaTime, type, recipient, owner, content);
        eventQueue.add(event);
    }

    public void addEventHandler(EventHandler entity) {
        entityList.add(entity);
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (running) {
            synchronized (thread) {
                thread.notify();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isFinished() {
        return finished;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public int getCurrentQueueLength() {
        return eventQueue.size();
    }

    protected void breforeRunningTest(Event event) {
    }

    protected void fireEvent(EventType type, EventHandler recipient, String owner, Object content) {
        fireEvent(new Event(currentTime, type, recipient, owner, content));
    }

    private void fireEvent(Event event) {
        if (event.getRecipient() != null) {
            event.getRecipient().handleEvent(event);
        } else {
            for (EventHandler entity : entityList) {
                entity.handleEvent(event);
            }
        }
    }

}

