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
package cz.cvut.fel.aic.alite.communication.channel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cz.cvut.fel.aic.alite.communication.CommunicationReceiver;
import cz.cvut.fel.aic.alite.communication.Message;

/**
 * Asynchronous version of {@link DirectCommunicationChannel}.
 * Uses direct calling of the receiveMessage method using asynchronous {@link Executors}.
 * The number of threads in the executos pool correspond to the number of processor cores
 * {@see Runtime.getRuntime().availableProcessors()}.
 *
 * @author Jiri Vokrinek
 */
public class DirectCommunicationChannelAsync extends DirectCommunicationChannel {

    private final ExecutorService executorService;
    @Deprecated
    private static ExecutorService obsoleteExecutorService = null;

    /**
     *
     * @param communicator
     */
    @Deprecated
    public DirectCommunicationChannelAsync(CommunicationReceiver communicator) throws CommunicationChannelException {
        super(communicator);
        if (obsoleteExecutorService == null) {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            obsoleteExecutorService = Executors.newFixedThreadPool(availableProcessors);
        }
        executorService = obsoleteExecutorService;
    }

    @Deprecated
    public DirectCommunicationChannelAsync(CommunicationReceiver communicator, ReceiverTable channelReceiverTable) throws CommunicationChannelException {
        super(communicator, channelReceiverTable);
        if (obsoleteExecutorService == null) {
            int availableProcessors = Runtime.getRuntime().availableProcessors();
            obsoleteExecutorService = Executors.newFixedThreadPool(availableProcessors);
        }
        executorService = obsoleteExecutorService;
    }

    public DirectCommunicationChannelAsync(CommunicationReceiver communicator, ReceiverTable channelReceiverTable, ExecutorService executorService) throws CommunicationChannelException {
        super(communicator, channelReceiverTable);
        this.executorService = executorService;
    }

    /**
     * Asynchronous direct call using {@link Executors}.
     *
     * @param receiver
     * @param message
     */
    @Override
    protected void callDirectReceive(final CommunicationReceiver receiver, final Message message) {
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                receiver.receiveMessage(message);
            }
        });
    }
}
