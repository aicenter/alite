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
package cz.cvut.fel.aic.alite.communication.eventbased;

import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessor;
import cz.cvut.fel.aic.alite.common.event.EventType;
import cz.cvut.fel.aic.alite.communication.CommunicationReceiver;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.channel.CommunicationChannelException;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel;

/**
 * Event-based version of {@link DirectCommunicationChannel}.
 * Uses event processor for message handling. Messages are delivered in the time+1
 * by calling of the receiveMessage method.
 * This method is asynchronous.
 *
 * @author Jiri Vokrinek
 */
public class EventBasedCommunicationChannel extends DirectCommunicationChannel implements EventHandler {

    private final EventProcessor eventProcessor;

    /**
     *
     * @param communicator
     */
    @Deprecated
    public EventBasedCommunicationChannel(CommunicationReceiver communicator, EventProcessor eventProcessor) throws CommunicationChannelException {
        super(communicator);
        this.eventProcessor = eventProcessor;
    }

    public EventBasedCommunicationChannel(CommunicationReceiver communicator, EventProcessor eventProcessor, ReceiverTable channelReceiverTable) throws CommunicationChannelException {
        super(communicator, channelReceiverTable);
        this.eventProcessor = eventProcessor;
    }

    /**
     * Asynchronous event-based call using {@link EventProcessor}.
     *
     * @param receiver
     * @param message
     */
    @Override
    protected void callDirectReceive(final CommunicationReceiver receiver, final Message message) {
        eventProcessor.addEvent(EventMessageType.EVENT_MESSAGE, this, null, new EventBasedMessage(receiver, message));
    }

    @Override
    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    @Override
    public void handleEvent(Event event) {

        if (event.isType(EventMessageType.EVENT_MESSAGE)) {
            ((EventBasedMessage) event.getContent()).receiver.receiveMessage(((EventBasedMessage) event.getContent()).message);
        }

    }

    class EventBasedMessage {

        final CommunicationReceiver receiver;
        final Message message;

        public EventBasedMessage(CommunicationReceiver receiver, Message message) {
            this.receiver = receiver;
            this.message = message;
        }
    }
}

enum EventMessageType implements EventType {

    EVENT_MESSAGE
}
