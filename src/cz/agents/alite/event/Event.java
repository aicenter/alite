package cz.agents.alite.event;


public final class Event implements Comparable<Event> {

    private long time;
    private EventType type;
    private String owner;
    private Object content;
    private EventHandler recipient;

    Event(long time, EventType type, EventHandler recipient, String owner, Object content) {
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
        return (time < event.time ? -1 : (time == event.time ? 0 : 1));
    }

    @Override
    public String toString() {
        return time + ": " + type + "(" + owner + "): " + content;
    }

}
