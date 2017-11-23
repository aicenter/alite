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
package cz.cvut.fel.aic.alite.communication.protocol.query;

import cz.cvut.fel.aic.alite.communication.Communicator;
import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;

/**
 * The general Query protocol. For each receiver instance the method {@link handleQuery(Object query)}
 * is called when initiator requests a query.
 *
 * @author Jiri Vokrinek
 * @author Antonin Komenda
 */
public abstract class QueryResponder extends Query {

    private final MessageHandler messagehandler;
    final String entityAddress;

    /**
     *
     * @param communicator
     * @param directory
     * @param name
     */
    public QueryResponder(final Communicator communicator, CapabilityRegister directory, String name) {
        super(communicator, name);
        this.entityAddress = communicator.getAddress();
        directory.register(entityAddress, getName());
        messagehandler = new ProtocolMessageHandler(this) {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                processMessage(message, content);
            }
        };
        communicator.addMessageHandler(messagehandler);
    }

    private void processMessage(Message message, ProtocolContent content) {
        String session = content.getSession();
        Object body = content.getData();
        switch (content.getPerformative()) {
            case QUERY:
                Object answer = handleQuery(body);
                Message msg = createReply(message, Performative.INFORM, answer, session);
                communicator.sendMessage(msg);
                break;
            default:
        }
    }

    private Message createReply(Message message, Performative performative, Object body, String session) {
        return communicator.createReply(message, new ProtocolContent(this, performative, body, session));
    }

    /**
     * This methods is called if some other agent sends Query.
     *
     * @param query the data of the query send by the initiator
     * @return the queried object to be returned to the initiator
     */
    abstract protected Object handleQuery(Object query);
}
