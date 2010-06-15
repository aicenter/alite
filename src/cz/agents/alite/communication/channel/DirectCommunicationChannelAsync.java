package cz.agents.alite.communication.channel;

import cz.agents.alite.communication.CommunicationReceiver;
import cz.agents.alite.communication.Message;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Asynchronous version of {@link DirectCommunicationChannel}.
 * Uses direct calling of the receiveMessage method using asynchronous {@link Executors}.
 * The number of threads in the executos pool correspond to the number of processor cores
 * {@see Runtime.getRuntime().availableProcessors()}.
 *
 * @author vokrinek
 */
public class DirectCommunicationChannelAsync extends DirectCommunicationChannel {

    static final int availableProcessors = Runtime.getRuntime().availableProcessors();
    static final ExecutorService executor = Executors.newFixedThreadPool(availableProcessors);
    //static final ExecutorService executor = Executors.newFixedThreadPool(1);

    /**
     *
     * @param communicator
     */
    public DirectCommunicationChannelAsync(CommunicationReceiver communicator) {
        super(communicator);
    }

    /**
     * Asynchronous direct call using {@link Executors}.
     *
     * @param receiver
     * @param message
     */
    @Override
    protected void callDirectReceive(final CommunicationReceiver receiver, final Message message) {
        executor.submit(new Runnable() {

            public void run() {
                receiver.receiveMessage(message);
            }
        });
    }
}
