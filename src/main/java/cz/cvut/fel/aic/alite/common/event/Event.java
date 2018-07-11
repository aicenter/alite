/* 
 * Copyright (C) 2017 Czech Technical University in Prague.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.alite.common.event;


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

    private final long id;
    private final long time;
    private final Enum type;
    private final String owner;
    private final Object content;
    private final EventHandler recipient;


    Event(long id, long time, Enum type, EventHandler recipient, String owner, Object content) {
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

    public Enum getType() {
        return type;
    }

    public long getTime() {
        return time;
    }

    public EventHandler getRecipient() {
        return recipient;
    }

    public boolean isType(Enum eventType) {
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
