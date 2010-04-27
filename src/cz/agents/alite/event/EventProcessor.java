package cz.agents.alite.event;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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

