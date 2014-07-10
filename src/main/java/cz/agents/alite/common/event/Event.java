package cz.agents.alite.common.event;


/**
 * An event is an abstract description of anything which can happen.
 *
 * The event is described by a time in which the event will happen, a type
 * which can be arbitrarily redefined by the needs of the application, a content
 * carrying the payload of what have happened (i.e. parametrization of the event),
 * and recipient, who will be affected by the event (the owner is an
 * deprecated version of the recipient).
 *
 * The events are {@link Comparable} by its time.
 *
 * @author Antonin Komenda
 */
public final class Event implements Comparable<Event> {

	/**
	 * Maximum event time value (in milliseconds of simulation time)
	 * that is considered "sane"; currently approx. 100 years.
	 */
	public final long MAX_SANE_DELTA_TIME = 1000 * 60 * 60 * 24 * 360 * 100;
	
    private final long id;
    private final long time;
    private final EventType type;
    private final String owner;
    private final Object content;
    private final EventHandler recipient;



    Event(long id, long time, EventType type, EventHandler recipient, String owner, Object content) {
    	if (id < 0) {
    		throw new IllegalArgumentException("Event ID cannot be negative");
    	}
    	if (time < 0 || time > MAX_SANE_DELTA_TIME) {
    		throw new IllegalArgumentException("Event time delta (" + time + ") is out of allowed range");
    	}
        this.id = id;
        this.time = time;
        this.recipient = recipient;
        this.type = type;
        this.owner = owner;
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public String getOwner() {
        return owner;
    }

    public EventType getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public EventHandler getRecipient() {
        return recipient;
    }

    public boolean isType(EventType eventType) {
        return type.equals(eventType);
    }

    @Override
    public int compareTo(Event event) {
        if (time == event.time) {
            if (id == event.id) {
                throw new RuntimeException("Found same event ids in comparation!");
            }
            return (id < event.id ? -1 : 1);
        } else {
            return (time < event.time ? -1 : (time == event.time ? 0 : 1));
        }
    }

    @Override
    public String toString() {
        return time + ": " + type + "(" + owner + "): " + content;
    }

}
