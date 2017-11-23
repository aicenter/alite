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
package cz.cvut.fel.aic.alite.communication.protocol.simple;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.DefaultProtocol;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;
import java.util.Collection;

/**
 * This is basic protocol for message passing. It allows direct communication between agents.
 * It creates {@link ProtocolMessageHandler} bounded to the name provided and allows
 * to send messages to such handlers.
 *
 * @author Jiri Vokrinek
 */
public abstract class SimpleProtocol extends DefaultProtocol {

    static final String SIMPLE_PROTOCOL_NAME = "SIMPLE_PROTOCOL";
    private final MessageHandler messagehandler;

    /**
     *
     * @param communicator
     * @param name unique identification of protocol instance
     */
    public SimpleProtocol(Communicator communicator, String name) {
        super(communicator, SIMPLE_PROTOCOL_NAME + ": " + name);
        messagehandler = new ProtocolMessageHandler(this) {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                processMessage(content.getData());
            }
        };
        communicator.addMessageHandler(messagehandler);

    }

    /**
     * The method called when new message arrives.
     *
     * @param content message content received.
     */
    abstract protected void processMessage(Object content);

    /**
     * Sends a message to a receiver.
     *
     * @param content the content to be sent
     * @param receiver address of the receiver
     */
    public void sendMessage(Object content, String receiver) {
        ProtocolContent pC = new ProtocolContent(this, Performative.PROPAGATE, content, generateSession());
        Message message = communicator.createMessage(pC);
        message.addReceiver(receiver);
        communicator.sendMessage(message);
    }

    /**
     * Sends a message to multiple receivers.
     *
     * @param content the content to be sent
     * @param receivers addresses of the receivers
     */
    public void sendMessage(Object content, Collection<String> receivers) {
        ProtocolContent pC = new ProtocolContent(this, Performative.PROPAGATE, content, generateSession());
        Message message = communicator.createMessage(pC);
        message.addReceivers(receivers);
        communicator.sendMessage(message);
    }
}
