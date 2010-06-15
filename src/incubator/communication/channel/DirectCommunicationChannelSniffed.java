package incubator.communication.channel;

import cz.agents.alite.communication.CommunicationReceiver;
import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.channel.DirectCommunicationChannel;
import incubator.communication.channel.sniffer.SnifferWrapper;

/**
 * Sniffed variant of {@link DirectCommunicationChannel}. 
 * Uses legacy A-globe sniffer.
 *
 * @author vokrinek
 */
public class DirectCommunicationChannelSniffed extends DirectCommunicationChannel {

    /**
     *
     * @param communicator
     */
    public DirectCommunicationChannelSniffed(CommunicationReceiver communicator) {
        super(communicator);
    }

    /**
     *
     * @param receiver
     * @param message
     */
    @Override
    protected void callDirectReceive(CommunicationReceiver receiver, Message message) {
        SnifferWrapper.getInstnace().handleMessage(message);
        super.callDirectReceive(receiver, message);
    }
}
