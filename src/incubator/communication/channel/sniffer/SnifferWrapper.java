package incubator.communication.channel.sniffer;

import java.util.regex.Pattern;

import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import incubator.communication.channel.sniffer.compatibility.Store;
import cz.agents.alite.communication.Message;

//  ATGSniffer:atgSniffer.sniffer.SnifferWrapper()
/**
 * <p>Title: A-Globe</p>
 * <p>Description: This agent provides watching flow of messages. </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak, Jiri Vokrinek, Jiri Biba
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 *
 *
 */
// TODO: complete the javadoc
public class SnifferWrapper //implements GISTopicServerListener
//public class SnifferWrapper extends ToolAgent //implements GISTopicServerListener
{

    private static final SnifferWrapper instance = new SnifferWrapper();

    public static SnifferWrapper getInstnace() {
        return instance;
    }
    /**
     * Agent's QUI.
     */
    public SnifferGUI gui;
    /**
     * Shell of the <code>GISServerService</code>
     */
//  private GISServerService.Shell gisServer = null;
    private Object gisServer = null;
    /**
     * Agent's <code>ComAnalyzerDataContainer</code>. It contains list of agents and
     * containers etc.
     */
    private SnifferDataContainer dataContainer;
    /**
     * Max message history length
     */
    int msgHistoryLength;
    /**
     * Default max message history length
     */
    private final static int MSG_HISTORY_LENGTH_DEFAULT = 4000;
    /**
     * Msg history length store key name
     */
    private final static String MSG_HISTORY_LENGTH_NAME = "MSG_HISTORY_LENGTH_NAME";
    /**
     * Current address filter
     */
    String addressFilter;
    /**
     * Current address filter pattern
     */
    Pattern addressFilterPattern;
    /**
     * Default address filter
     */
    private final static String ADDRESS_FILTER_DEFAULT = "others";
    /**
     * Address filter store key name
     */
    private final static String ADDRESS_FILTER_NAME = "ADDRESS_FILTER";
    public static final String ATG_SNIFFER = "ATGSniffer";
    /**
     * Agent's store
     */
    private Store store;
    public incubator.communication.channel.sniffer.ComAnalyzerDataContainer comAnalyzerDataContainer;
    public ComAnalyzerAgentGUI commAnalyzerGUI;

    /**
     * Empty constructor.
     */
    public SnifferWrapper() {
        //addBehaviour(new messageHandler(this));




        //    super.init(a, initState);

        // Load settings
        // TODO: hack store
        //    store = getContainer().getAgentStore(getName());
        store = new Store(this.ATG_SNIFFER, null);
        msgHistoryLength = store.getInt(MSG_HISTORY_LENGTH_NAME, MSG_HISTORY_LENGTH_DEFAULT);
        addressFilter = store.getString(ADDRESS_FILTER_NAME, ADDRESS_FILTER_DEFAULT);
        addressFilterPattern = Pattern.compile(addressFilter, Pattern.CASE_INSENSITIVE);

        // create gui
        dataContainer = new SnifferDataContainer(this);
        gui = new SnifferGUI(this, dataContainer);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                gui.maxHistory.setText("/" + msgHistoryLength);
            }
        });

        System.out.println("Sniffer initialized.");

//      DFAgentDescription dfd = new DFAgentDescription();
//      dfd.setName(getAID());
//      ServiceDescription sd = new ServiceDescription();
//      sd.setName(this.ATG_SNIFFER);
//      sd.setType(this.ATG_SNIFFER);
//      dfd.addServices(sd);
//      try {
//          DFService.register(this,dfd);
//      } catch (FIPAException e) {
//          System.out.println("SEVERE: Cannot register ATGSniffer by DFService!");
//      }

        initCommAnalyzer();

    }

    public Store getAgentStore() {
        return this.store;
    }

    /**
     * Method stops the agent.
     */
    public void finish() {
        gui.setVisible(false);
        if (gisServer != null) {
//      gisServer.unsubscribeAllTopics();
        }
    }

    /**
     * This method registers a container to be watched.
     *
     * @param containerName String
     * @param containerAddress Address
     */
    private void loginContainer(final String containerName, final String containerAddress) {
//    getLogger().severe("Login container: "+vp.getName());

        dataContainer.loginContainer(containerName, containerAddress);
        comAnalyzerDataContainer.loginContainer(containerName, containerAddress);
    }

    /**
     * This method deregisters a watched container.
     *
     * @param containerName String
     */
    private void logoutContainer(final String containerName) {
//    getLogger().severe("Logout container: "+vp.getName());

        dataContainer.logoutContainer(containerName);
        comAnalyzerDataContainer.logoutContainer(containerName);
    }

    public void handleMessage(Message m) {
        incomingMessageCopy(m, false);
        // TODO: log message
    }

    private void incomingMessageCopy(final Message m, final boolean undeliverable) {
        if (m.getReceivers().isEmpty()) {
            System.out.println("Empty message: ");//+m.toString());
            return;
        }
        dataContainer.incomingMessageCopy(m, undeliverable);
        comAnalyzerDataContainer.incomingMessageCopy(m, undeliverable);
        JScrollBar vert = gui.detailedScrollPanel.getVerticalScrollBar();
        vert.setValue(vert.getMaximum() - vert.getVisibleAmount());

    }

    /**
     * The method kills the agent.
     */
    void close_gui() {
//TODO: agent kill
//    kill();
//    if (getState() != MIGRATING) {
//
//    }
    }

    /**
     * The method shows GUI of the container where the agent runs.
     */
    void showContainerGUI() {
        // Destination command service address
        /** TODO: hack - not supported now
        final Address a= getAddress().deriveServiceAddress(AgentContainer.COMMANDSERVICE);
        final Command c= new Command();
        c.setName(AgentContainer.CommandService.GUI);
        c.getParam().add(XMLtools.makeParam(AgentContainer.CommandService.VISIBLE,Boolean.toString(true)));

        addEvent(new Runnable() {
        public void run() {

        //        if (gisServer != null)
        //          gisServer.sendRequest(a, c);
        }
        });
         **/
    }

    /**
     * Handler of login topic. The topic for login is <code>
     * Simulator.TOPIC_VISIBILITY_UPDATES</code>
     *
     * @param topic String
     * @param remoteContainerName String
     * @param remoteContainerAddress Address
     */
    public void handleLoginTopic(String topic, String remoteContainerName,
            String remoteContainerAddress) {
        /** TODO: hack - topics not supported

        if (topic.equalsIgnoreCase(MessageTransport.TOPIC_VISIBILITY_UPDATES)) {
        Address rAdr = remoteContainerAddress;
        if (rAdr == null) {  // local container
        rAdr = getAddress().deriveContainerAddress();
        }
        loginContainer(remoteContainerName,rAdr);
        return;
        }
        //    logger.warning("Unexpected topic login: "+topic);
         **/
    }

    /**
     * Handler of logout topic. The topic for logout is <code>
     * Simulator.TOPIC_VISIBILITY_UPDATES</code>
     *
     * @param topic String
     * @param remoteContainerName String
     */
    public void handleLogoutTopic(String topic, String remoteContainerName) {
        /** TODO: hack - topics not supported
        if (topic.equalsIgnoreCase(MessageTransport.TOPIC_VISIBILITY_UPDATES)) {
        logoutContainer(remoteContainerName);
        return;
        }
        //    logger.severe("Unexpected topic logout: "+topic);
         **/
    }

    /**
     * This method handles the topic <code>MessageTransport.TOPIC_MESSAGE_COPY
     * </code>
     *
     * @param topic String
     * @param content Object
     * @param reason String
     * @param remoteContainerName String
     * @param remoteClientAddress Address
     */
    public void handleTopic(String topic, Object content, String reason,
            String remoteContainerName, String remoteClientAddress) {
        /** TODO: hack - topics not supported
        if (topic.equalsIgnoreCase(MessageTransport.TOPIC_OUTGOING_MESSAGE_COPY)) {
        try {
        Message m = Message.getMessageFormBase64encodedSerializedData( (String)
        content, this);
        incomingMessageCopy(m, Boolean.valueOf(reason).booleanValue());
        }
        catch (Exception ex) {
        logger.warning("Problem with getting message copy: "+ex+"\nOrginal content: "+(String)content);
        }
        return;
        }
         */
    }

    /**
     * updateMsgHistoryLength
     *
     * @param newMsgHistoryLength int
     */
    void updateMsgHistoryLength(int newMsgHistoryLength) {
        // save changes
        msgHistoryLength = newMsgHistoryLength;
        store.putInt(MSG_HISTORY_LENGTH_NAME, msgHistoryLength);

        //update gui
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                gui.maxHistory.setText("/" + msgHistoryLength);
            }
        });

        //update history queue
        dataContainer.updateMsgHistoryLength();
    }

    /**
     * updateAddressFilter
     *
     * @param newAddressFilter String
     */
    void updateAddressFilter(String newAddressFilter) {
        // save changes
        addressFilter = newAddressFilter;
        store.putString(ADDRESS_FILTER_NAME, addressFilter);
        addressFilterPattern = Pattern.compile(addressFilter, Pattern.CASE_INSENSITIVE);
    }

//
//private class messageHandler extends CyclicBehaviour {
//
//private SnifferWrapper myAgent=null;
//
//
//    public messageHandler(SnifferWrapper owner) {
//        myAgent = owner;
//    }
//
//    /**
//     * action
//     *
//     * @todo Implement this jade.core.behaviours.Behaviour method
//     */
//    public void action() {
//        ACLMessage message = myAgent.receive();
//        if (message!=null) {
//
//            myAgent.handleMessage(message);
//
//        } else {
//            block();
//        }
//    }
//
//
//}
    // Comm analyzer
    private void initCommAnalyzer() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {

                public void run() {
                    commAnalyzerGUI = new incubator.communication.channel.sniffer.ComAnalyzerAgentGUI(SnifferWrapper.this);
                }
            });
        } catch (Exception ex) {
            System.out.println("Cannot create gui due to: " + ex
                    + "\nComAnalyzer will not be operational.");
//            logSevere("Cannot create gui due to: " + ex +
//                      "\nComAnalyzer will be stopped.");
//            stop();
            return;
        }
        comAnalyzerDataContainer = new incubator.communication.channel.sniffer.ComAnalyzerDataContainer(this);
        commAnalyzerGUI.setDataContainer(comAnalyzerDataContainer);
        rotateShowCommAnalyzer();
        rotateShowSniffer();
    }

    public void rotateShowCommAnalyzer() {
        commAnalyzerGUI.setVisible(!commAnalyzerGUI.isVisible());
    }

    public void rotateShowSniffer() {
        gui.setVisible(!gui.isVisible());
    }

    public ComAnalyzerAgentGUI getGUI() {
        return commAnalyzerGUI;
    }
}
