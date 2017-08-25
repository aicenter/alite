package cz.cvut.fel.aic.alite.communication;

import cz.cvut.fel.aic.alite.communication.DefaultCommunicator;
import cz.cvut.fel.aic.alite.communication.Message;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cz.cvut.fel.aic.alite.communication.channel.CommunicationChannelException;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel;
import cz.cvut.fel.aic.alite.communication.channel.UnknownReceiversException;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel.DefaultReceiverTable;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel.ReceiverTable;
import cz.cvut.fel.aic.alite.communication.content.error.ErrorContent;
import cz.cvut.fel.aic.alite.communication.content.error.ErrorMessageHandler;
import cz.cvut.fel.aic.alite.communication.protocol.Performative;
import cz.cvut.fel.aic.alite.communication.protocol.Protocol;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolContent;
import cz.cvut.fel.aic.alite.communication.protocol.ProtocolMessageHandler;

public class TestMessaging {

    private final static ReceiverTable singletonTable = new DefaultReceiverTable();

    private static final String data = "DATA";
    private static final String addressReceiver = "addressReceiver";
    private static final Collection<String> addressReceiversUnknown = new LinkedList<String>(Arrays.asList(new String[] {"addressDummy1", "addressDummy2"}));
    private static final String addressSender = "addressSender";
    private static final Protocol protocol = new Protocol() {

        @Override
        public String getName() {
            return "TEST";
        }

        @Override
        public boolean equals(Protocol protocol) {
            return getName().equals(protocol.getName());
        }
    };

    private static final Performative performative = Performative.DONE;

    private static MessageWrapper received;
    private static MessageWrapper errorReceived;
    private static DefaultCommunicator communicatorSender;
    private static DefaultCommunicator communicatorReceiver;


    @BeforeClass
    public static void setUpBeforeClass() throws CommunicationChannelException {
        received = new MessageWrapper();
        errorReceived = new MessageWrapper();

        communicatorSender = new DefaultCommunicator(addressSender);
        communicatorReceiver = new DefaultCommunicator(addressReceiver);

        // create communication channels
        communicatorSender.addChannel(new DirectCommunicationChannel(communicatorSender, singletonTable));
        communicatorReceiver.addChannel(new DirectCommunicationChannel(communicatorReceiver, singletonTable));
    }

    @Before
    public void setUp() throws CommunicationChannelException {
        received.message = null;
        errorReceived.message = null;
    }

    @Test
    public void directSend() {

        // create message handlers
        ErrorMessageHandler errorMessageHandler = new ErrorMessageHandler() {

            @Override
            public void handleMessage(Message message, ErrorContent content) {
                Assert.assertTrue(false);
            }

        };
        communicatorSender.addMessageHandler(errorMessageHandler);

        ProtocolMessageHandler protocolMessageHandler = new ProtocolMessageHandler(protocol, performative, "") {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                Assert.assertEquals(data, content.getData());

                received.message = message;
            }

        };
        communicatorReceiver.addMessageHandler(protocolMessageHandler);

        // send message
        Message message = communicatorSender.createMessage(new ProtocolContent(protocol, performative, data,""));
        message.addReceiver(addressReceiver);
        communicatorSender.sendMessage(message);


        // test
        Assert.assertNotNull(received.message);
        Assert.assertEquals(data, received.message.getContent().getData());
        Assert.assertEquals(message.getId(), received.message.getId());
        Assert.assertEquals(message.getSender(), addressSender);
        Assert.assertTrue(message.getReceivers().contains(addressReceiver));

        // cleanup
        communicatorSender.removeMessageHandler(errorMessageHandler);
        communicatorReceiver.removeMessageHandler(protocolMessageHandler);
    }

    @Test
    public void directSendUnknownReceiver() {

        // create message handlers
        ErrorMessageHandler errorMessageHandler = new ErrorMessageHandler() {

            @Override
            public void handleMessage(Message message, ErrorContent content) {
                Assert.assertTrue((((UnknownReceiversException) content.getData()).getUnknownReceivers().containsAll(addressReceiversUnknown)));
                Assert.assertFalse((((UnknownReceiversException) content.getData()).getUnknownReceivers().contains(addressReceiver)));

                errorReceived.message = message;
            }

        };
        communicatorSender.addMessageHandler(errorMessageHandler);

        ProtocolMessageHandler protocolMessageHandler = new ProtocolMessageHandler(protocol, performative,"") {

            @Override
            public void handleMessage(Message message, ProtocolContent content) {
                Assert.assertEquals(data, content.getData());

                received.message = message;
            }

        };
        communicatorReceiver.addMessageHandler(protocolMessageHandler);

        // send message
        Message message = communicatorSender.createMessage(new ProtocolContent(protocol, performative, data,""));
        message.addReceivers(addressReceiversUnknown);
        message.addReceiver(addressReceiver);
        communicatorSender.sendMessage(message);


        // test - delivered
        Assert.assertNotNull(received.message);
        Assert.assertEquals(data, received.message.getContent().getData());
        Assert.assertEquals(message.getId(), received.message.getId());
        Assert.assertEquals(message.getSender(), addressSender);
        Assert.assertTrue(message.getReceivers().contains(addressReceiver));

        // test - undelivered
        Assert.assertNotNull(errorReceived.message);
        Assert.assertTrue(((UnknownReceiversException) errorReceived.message.getContent().getData()).getUnknownReceivers().containsAll(addressReceiversUnknown));
        Assert.assertFalse((((UnknownReceiversException) errorReceived.message.getContent().getData()).getUnknownReceivers().contains(addressReceiver)));
        Assert.assertEquals(errorReceived.message.getSender(), addressSender);
        Assert.assertTrue(errorReceived.message.getReceivers().contains(addressSender));

        // cleanup
        communicatorSender.removeMessageHandler(errorMessageHandler);
        communicatorReceiver.removeMessageHandler(protocolMessageHandler);
    }

    private static class MessageWrapper {

        public Message message = null;

    }

}
