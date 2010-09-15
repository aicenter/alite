package incubator.communication.ext.protocols.tests;

import cz.agents.alite.communication.channel.CommunicationChannelException;
import cz.agents.alite.communication.protocol.tokenring.TokenRing;
import cz.agents.alite.communication.protocol.tokenring.MasteredTokenRing;
import cz.agents.alite.communication.DefaultCommunicator;
import cz.agents.alite.communication.channel.DirectCommunicationChannelAsync;
import cz.agents.alite.common.capability.CapabilityRegister;
import cz.agents.alite.communication.directory.DirectoryFacilitatorSingleton;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jiri Vokrinek
 */
public class TokenRingTest {

    static CapabilityRegister directory = DirectoryFacilitatorSingleton.getInstance();

    public static void main(String[] args) {
        tokenRing();
        try {
            Thread.sleep(500);
            System.out.println("*****************************************");
        } catch (InterruptedException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        masteredTokenRing();

    }

    private static void tokenRing() {
        String RING = "myRing";
        DefaultCommunicator c1 = new DefaultCommunicator("A1");
        try {
            c1.addChannel(new DirectCommunicationChannelAsync(c1));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TokenRing agent1 = new TokenRing(c1, directory, RING) {

            @Override
            protected void handleToken(Object token, TokenProcessCallback callback) {
                System.out.println("A1 is handling token: " + token);
                callback.processingDone();
            }
        };
        DefaultCommunicator c2 = new DefaultCommunicator("A2");
        try {
            c2.addChannel(new DirectCommunicationChannelAsync(c2));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TokenRing agent2 = new TokenRing(c2, directory, RING) {

            @Override
            protected void handleToken(Object token, TokenProcessCallback callback) {
                System.out.println("A2 is handling token: " + token);

                if (!token.equals("A2a")) {
                    System.out.println("A2 is invoking token A2a");


                    invokeToken("A2a", new TokenRingInform() {

                        public void tokenBack() {
                            System.out.println("tokenA2a is back!");
                        }
                    });
                }
                callback.processingDone();
            }
        };
        DefaultCommunicator c3 = new DefaultCommunicator("A3");
        try {
            c3.addChannel(new DirectCommunicationChannelAsync(c3));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TokenRing agent3 = new TokenRing(c3, directory, RING) {

            @Override
            protected void handleToken(Object token, TokenProcessCallback callback) {
                System.out.println("A3 is handling token: " + token);
                callback.processingDone();
            }
        };
        System.out.println("--------------------------------------");
        System.out.println("A1 is master " + agent1.isMaster());
        System.out.println("A2 is master " + agent2.isMaster());
        System.out.println("A3 is master " + agent3.isMaster());
        System.out.println("--------------------------------------");
        System.out.println("A1 is invoking token");
        agent1.invokeToken("tokenA1", new TokenRing.TokenRingInform() {

            public void tokenBack() {
                System.out.println("tokenA1 is back!");
            }
        });
        System.out.println("--------------------------------------");
        System.out.println("A2 is invoking token");
        agent2.invokeToken("tokenA2", new TokenRing.TokenRingInform() {

            public void tokenBack() {
                System.out.println("tokenA2 is back!");
            }
        });
        System.out.println("--------------------------------------");
        System.out.println("A3 is invoking token");
        agent3.invokeToken("tokenA3", new TokenRing.TokenRingInform() {

            public void tokenBack() {
                System.out.println("tokenA3 is back!");
            }
        });
        System.out.println("--------------------------------------");
    }

    private static void masteredTokenRing() {
        String RING = "myRing";
        DefaultCommunicator c1 = new DefaultCommunicator("A1");
        try {
            c1.addChannel(new DirectCommunicationChannelAsync(c1));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        MasteredTokenRing agent1 = new MasteredTokenRing(c1, directory, RING) {

            @Override
            protected void handleToken(Object token, TokenProcessCallback callback) {
                System.out.println("A1 is handling token: " + token);
                callback.processingDone();
            }
        };
        DefaultCommunicator c2 = new DefaultCommunicator("A2");
        try {
            c2.addChannel(new DirectCommunicationChannelAsync(c2));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TokenRing agent2 = new MasteredTokenRing(c2, directory, RING) {

            @Override
            protected void handleToken(Object token, TokenProcessCallback callback) {
                System.out.println("A2 is handling token: " + token);
                if (!token.equals("A2a")) {
                    System.out.println("A2 is invoking token A2a");
                    invokeToken("A2a", new TokenRingInform() {

                        public void tokenBack() {
                            System.out.println("tokenA2a is back!");
                        }
                    });
                }
                callback.processingDone();
            }
        };
        DefaultCommunicator c3 = new DefaultCommunicator("A3");
        try {
            c3.addChannel(new DirectCommunicationChannelAsync(c3));
        } catch (CommunicationChannelException ex) {
            Logger.getLogger(TokenRingTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        TokenRing agent3 = new MasteredTokenRing(c3, directory, RING) {

            @Override
            protected void handleToken(Object token, TokenProcessCallback callback) {
                System.out.println("A3 is handling token: " + token);
                callback.processingDone();
            }
        };
        System.out.println("--------------------------------------");
        System.out.println("A1 is master " + agent1.isMaster());
        System.out.println("A2 is master " + agent2.isMaster());
        System.out.println("A3 is master " + agent3.isMaster());
        System.out.println("--------------------------------------");
        System.out.println("A1 is invoking token");
        agent1.invokeToken("tokenA1", new TokenRing.TokenRingInform() {

            public void tokenBack() {
                System.out.println("tokenA1 is back!");
            }
        });
        System.out.println("--------------------------------------");
        System.out.println("A2 is invoking token");
        agent2.invokeToken("tokenA2", new TokenRing.TokenRingInform() {

            public void tokenBack() {
                System.out.println("tokenA2 is back!");
            }
        });
        System.out.println("--------------------------------------");
        System.out.println("A3 is invoking token");
        agent3.invokeToken("tokenA3", new TokenRing.TokenRingInform() {

            public void tokenBack() {
                System.out.println("tokenA3 is back!");
            }
        });
        System.out.println("--------------------------------------");
    }
}
