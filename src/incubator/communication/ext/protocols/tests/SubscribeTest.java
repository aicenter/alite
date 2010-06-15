package incubator.communication.ext.protocols.tests;

import cz.agents.alite.communication.protocol.subscribe.SubscribeProtocol;
import cz.agents.alite.communication.protocol.subscribe.SubscribeProtocolSender;
import cz.agents.alite.communication.DefaultCommunicator;
import incubator.communication.channel.DirectCommunicationChannelSniffed;
import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.communication.directory.DirectoryFacilitatorSingleton;

/**
 *
 * @author Jiri Vokrinek
 */
public class SubscribeTest {

    public static void main(String[] args) {

        CapabilityRegister directory = DirectoryFacilitatorSingleton.getInstance();

        String subscribThing1 = "mySubscription1";
        String subscribThing2 = "mySubscription2";

        DefaultCommunicator c1 = new DefaultCommunicator("A1");
        c1.addChannel(new DirectCommunicationChannelSniffed(c1));
        SubscribeProtocol agent1a = new SubscribeProtocol(c1, directory, subscribThing1) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A1 is receiving: " + inform);
            }
        };
        SubscribeProtocol agent1b = new SubscribeProtocol(c1, directory, subscribThing2) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A1 is receiving: " + inform);
            }
        };

        DefaultCommunicator c2 = new DefaultCommunicator("A2");
        c2.addChannel(new DirectCommunicationChannelSniffed(c2));

        SubscribeProtocol agent2 = new SubscribeProtocol(c2, directory, subscribThing2) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A2 is receiving: " + inform);
            }
        };


        DefaultCommunicator c3 = new DefaultCommunicator("A3");
        c3.addChannel(new DirectCommunicationChannelSniffed(c3));


        SubscribeProtocol agent3 = new SubscribeProtocol(c3, directory, subscribThing1) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A3 is receiving: " + inform);
            }
        };

        DefaultCommunicator c4 = new DefaultCommunicator("A4");
        c4.addChannel(new DirectCommunicationChannelSniffed(c4));

        SubscribeProtocolSender agent4 = new SubscribeProtocolSender(c4, directory, subscribThing1) {
        };

        System.out.println("--------------------------------------");
        System.out.println("A1 is sending info1a");
        agent1a.snedInform("info1a");
        System.out.println("--------------------------------------");
        System.out.println("A1 is sending info1b");
        agent1b.snedInform("info1b");
        System.out.println("--------------------------------------");
        System.out.println("A2 is sending info2");
        agent2.snedInform("info2");
        System.out.println("--------------------------------------");
        System.out.println("A3 is sending info3");
        agent3.snedInform("info3");
        System.out.println("--------------------------------------");
        System.out.println("A4 is sending info4");
        agent4.snedInform("info4");


    }
}
