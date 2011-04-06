package incubator.communication.ext.protocols.tests;

import cz.agents.alite.communication.channel.CommunicationChannelException;
import cz.agents.alite.communication.protocol.subscribe.SubscribeProtocolReceiver;
import cz.agents.alite.communication.protocol.subscribe.SubscribeProtocolSender;
import cz.agents.alite.communication.DefaultCommunicator;
import incubator.communication.channel.DirectCommunicationChannelSniffed;
import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.communication.directory.DirectoryFacilitatorSingleton;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            c1.addChannel(new DirectCommunicationChannelSniffed(c1));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(SubscribeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        SubscribeProtocolReceiver agent1a = new SubscribeProtocolReceiver(c1, directory, subscribThing1) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A1 is receiving: " + inform);
            }
        };
        SubscribeProtocolReceiver agent1b = new SubscribeProtocolReceiver(c1, directory, subscribThing2) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A1 is receiving: " + inform);
            }
        };

        DefaultCommunicator c2 = new DefaultCommunicator("A2");
        try {
            c2.addChannel(new DirectCommunicationChannelSniffed(c2));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(SubscribeTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        SubscribeProtocolReceiver agent2 = new SubscribeProtocolReceiver(c2, directory, subscribThing2) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A2 is receiving: " + inform);
            }
        };


        DefaultCommunicator c3 = new DefaultCommunicator("A3");
        try {
            c3.addChannel(new DirectCommunicationChannelSniffed(c3));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(SubscribeTest.class.getName()).log(Level.SEVERE, null, ex);
        }


        SubscribeProtocolReceiver agent3 = new SubscribeProtocolReceiver(c3, directory, subscribThing1) {

            @Override
            protected void handleInform(Object inform) {
                System.out.println("A3 is receiving: " + inform);
            }
        };

        DefaultCommunicator c4 = new DefaultCommunicator("A4");
        try {
            c4.addChannel(new DirectCommunicationChannelSniffed(c4));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(SubscribeTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        SubscribeProtocolSender agent4 = new SubscribeProtocolSender(c4, directory, subscribThing1) {
        };

        System.out.println("--------------------------------------");
        System.out.println("A1 is sending info1a");
        agent1a.sendInform("info1a");
        System.out.println("--------------------------------------");
        System.out.println("A1 is sending info1b");
        agent1b.sendInform("info1b");
        System.out.println("--------------------------------------");
        System.out.println("A2 is sending info2");
        agent2.sendInform("info2");
        System.out.println("--------------------------------------");
        System.out.println("A3 is sending info3");
        agent3.sendInform("info3");
        System.out.println("--------------------------------------");
        System.out.println("A4 is sending info4");
        agent4.sendInform("info4");


    }
}
