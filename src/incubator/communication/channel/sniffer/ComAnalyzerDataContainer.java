package incubator.communication.channel.sniffer;


import incubator.communication.channel.sniffer.compatibility.Store;
import cz.agents.alite.communication.Message;
import java.awt.*;
import javax.swing.*;
import java.util.*;

import java.util.regex.*;




/**
 * Visualization data container for ComAnalyzerAgent.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Dusan Pavlicek
 * @version 1.0
 */
public class ComAnalyzerDataContainer {

    /**
     * Store constants.
     */
    private static final String STORE_FILTER = "param/filter";
    private static final String STORE_REFRESH_TIME = "param/refresh_time";
    private static final String STORE_FADING_TIME = "param/fading_time";
    private static final String STORE_TIME_FRAME = "param/time_frame";
    private static final String STORE_MSGCNT_SENS = "param/msgcnt_sens";
    private static final String STORE_LINE_MINWIDTH = "param/line_minwidth";
    private static final String STORE_LINE_MAXWIDTH = "param/line_maxwidth";

    /**
     * Default width of agent's box in the GUI.
     */
    private final static int AGENT_BOX_WIDTH = 30;//120;

    /**
     * Default height of agent's box in the GUI.
     */
    private final static int AGENT_BOX_HEIGHT = 30;//50;

    /**
     * Default width of empty border around the text area of agent boxes in GUI.
     */
    private final static int AGENT_BOX_BORDER = 5;

    /**
     * Width of empty border around the drawing area in the GUI.
     */
    private final static int PANEL_BORDER = 25;

    /**
     * Default font size.
     */
    private final static int FONT_SIZE = 11;

    /**
     * Color constants.
     */
    private final static Color COLOR_AGENT_FILL = new Color(255, 255, 128);
    private final static Color COLOR_SERVICE_FILL = new Color(128, 192, 255);

    /**
     * Pen stroke constants.
     */
    private final static Stroke STROKE_WIDTH_1 = new BasicStroke(1);

    /**
     * Alpha composite constants.
     */
    private final static AlphaComposite ALPHA_COMPOSITE_OPAQUE = AlphaComposite.Src;

    /**
     * Variable parameters.
     */
    int paramRefreshTime; // GUI refresh time [ms]
    int paramFadingTime; // messages' fading time [ms]
    int paramTimeFrame; // length of the message list time frame [ms]
    float paramMsgCountSensitivity; // GUI sensitivity to message counts [%/100]
    float paramMinLineWidth; // min width of message lines [pixels]
    float paramMaxLineWidth; // max width of message lines [pixels]

    /**
     * The owner of this ComAnalyzerDataContainer.
     */
    SnifferWrapper owner;

    /**
     * Store for ComAnalyzerDataContainer.
     */
    Store store;

    /**
     * The JPanel to which all graphics is rendered.
     */
    private JPanel guiCanvas;

    /**
     *
     */
    private int guiOffsetX;
    private int guiOffsetY;

    /**
     * Scale of all graphics being rendered (1.0 by default).
     */
    private float guiScale;

    /**
     * X radius of the ellipse being rendered.
     */
    private float guiRadiusX;

    /**
     * Y radius of the ellipse being rendered.
     */
    private float guiRadiusY;

    /**
     * Current width of agent's box in the GUI.
     */
    private int agentBoxWidth;

    /**
     * Current height of agent's box in the GUI.
     */
    private int agentBoxHeight;

    /**
     * Current width of empty border around the text area of agent boxes in GUI.
     */
    private int agentBoxBorder;

    /**
     * Agent/service being currently zoomed (or null if none).
     */
    private AgentInfo zoomedAgent;

    /**
     * Default (unscaled) font.
     */
    private Font fontDefault;

    /**
     * Current scaled font.
     */
    private Font fontScaled;

    /**
     * Default (unscaled) font metrics.
     */
    private FontMetrics fontMetricsDefault;

    /**
     * Current scaled font metrics.
     */
    private FontMetrics fontMetricsScaled;

    /**
     * Current internal time stamp (tick).
     */
    private long timeStamp;

    /**
     * Current regular expression string used for filtering
     * displayed agents/services.
     */
    private String regexString;

    /**
     * Current regular expression pattern used for filtering
     * displayed agents/services.
     */
    private Pattern regexPattern;

    /**
     * Maps (key) containerName to (value) containerAddress.
     */
    private Map containerAddresses;

    /**
     * Maps (key) containerAddress to (value) containerName.
     */
    private Map containerNames;

    /**
     * Maps (key) agentAddress to (value) AgentInfo.
     */
    private Map agentInfos;

    /**
     * Vector of currently visible AgentInfos (displayed in GUI).
     */
    private Vector visibleAgents;

    /**
     * Set of currently invisible AgentInfos (not displayed in GUI).
     */
    private Set invisibleAgents;

    /**
     * Maps (key) containerAddress to (value) Set of agentAddresses.
     */
    private Map agentsOnContainer;

    /**
     * Internal data matrices.
     */
    private DataMatrices dataMatrices;

    /**
     * List of messages that came in within the current time frame.
     */
    private LinkedList messages;

    public ComAnalyzerDataContainer(SnifferWrapper owner) {
        this.owner = owner;
        store = owner.getAgentStore();
        guiCanvas = owner.getGUI().getCanvasPanel();
        guiOffsetX = 0;
        guiOffsetY = 0;
        guiScale = 1.0f;
        agentBoxWidth = Math.round(guiScale * AGENT_BOX_WIDTH);
        agentBoxHeight = Math.round(guiScale * AGENT_BOX_HEIGHT);
        agentBoxBorder = Math.round(guiScale * AGENT_BOX_BORDER);
        fontDefault = new Font("Default", Font.PLAIN, FONT_SIZE);
        fontScaled = new Font("Default", Font.PLAIN, Math.round(guiScale * FONT_SIZE));
        fontMetricsDefault = guiCanvas.getFontMetrics(fontDefault);
        fontMetricsScaled = guiCanvas.getFontMetrics(fontScaled);
        timeStamp = java.lang.System.currentTimeMillis();
        init();
        loadStore();
//        AglobeThreadPool.getThread(new TimeStamp(), "Time stamp thread").start();
//        AglobeThreadPool.getThread(new Refresh(), "Refresh thread").start();
        new Thread(new TimeStamp(), "Time stamp thread").start();
        new Thread(new Refresh(), "Refresh thread").start();
    
    }

    /**
     * Data initialization/reset.
     */
    synchronized private void init() {
        containerAddresses = new HashMap();
        containerNames = new HashMap();

        agentInfos = new HashMap();
        visibleAgents = new Vector();
        invisibleAgents = new HashSet();

        agentsOnContainer = new HashMap();

        dataMatrices = new DataMatrices();

        messages = new LinkedList();

        zoomedAgent = null;

//        loginContainer(owner.getContainer().getContainerName(),
//                       owner.getAddress().deriveContainerAddress());
    }

    /**
     * Loads parameters from store (or uses default values).
     */
    synchronized void loadStore() {
        regexString = store.getString(STORE_FILTER, ".*");
        regexPattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
        paramRefreshTime = store.getInt(STORE_REFRESH_TIME, 500);
        paramFadingTime = store.getInt(STORE_FADING_TIME, 4000);
        paramTimeFrame = store.getInt(STORE_TIME_FRAME, 10000);
        paramMsgCountSensitivity = (float) store.getDouble(STORE_MSGCNT_SENS, 0.1);
        paramMinLineWidth = (float) store.getDouble(STORE_LINE_MINWIDTH, 1.0);
        paramMaxLineWidth = (float) store.getDouble(STORE_LINE_MAXWIDTH, 5.0);
    }

    /**
     * Saves parameters to store.
     */
    synchronized void saveStore() {
        store.putString(STORE_FILTER, regexString);
        store.putInt(STORE_REFRESH_TIME, paramRefreshTime);
        store.putInt(STORE_FADING_TIME, paramFadingTime);
        store.putInt(STORE_TIME_FRAME, paramTimeFrame);
        store.putDouble(STORE_MSGCNT_SENS, paramMsgCountSensitivity);
        store.putDouble(STORE_LINE_MINWIDTH, paramMinLineWidth);
        store.putDouble(STORE_LINE_MAXWIDTH, paramMaxLineWidth);
    }

    /**
     * Register a new container that logged in the A-globe system.
     * @param containerName String
     * @param containerAddress Address
     */
    synchronized void loginContainer(final String containerName, final String containerAddress) {
        containerAddresses.put(containerName, containerAddress);
        containerNames.put(containerAddress, containerName);
        Set set = (Set) agentsOnContainer.get(containerAddress);
        if (set == null) {
            agentsOnContainer.put(containerAddress, new HashSet());
        } else {
            int maxStringWidthScaled = Math.round(guiScale *
                                                  (AGENT_BOX_WIDTH -
                    AGENT_BOX_BORDER * 2));
            Vector containerNameWrappedScaled = wrapString(containerName,
                    fontMetricsScaled, maxStringWidthScaled);
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                AgentInfo info = (AgentInfo) agentInfos.get(iter.next());
                info.containerNameWrappedScaled = containerNameWrappedScaled;
            }
            guiCanvas.repaint();
        }
    }

    /**
     * Deregister a container that logged out of the A-globe system.
     * @param containerName String
     */
    synchronized void logoutContainer(final String containerName) {
        String containerAddress = (String) containerAddresses.remove(containerName);
        if (containerAddress == null) { // we don't know such container
            return;
        }

        containerNames.remove(containerAddress);
        Set set = (Set) agentsOnContainer.get(containerAddress); // agents to be removed

        if (zoomedAgent != null) {
            if (set.contains(zoomedAgent)) {
                zoomedAgent = null;
            }
        }

        // selectively remove messages from list
        Iterator iter = messages.iterator();
        while (iter.hasNext()) {
            MessageInfo info = (MessageInfo) iter.next();
            if (set.contains(info.senderAddress) ||
                set.contains(info.receiverAddress)) {
                // sender or receiver is to be removed => remove the message
                iter.remove();
            }
        }

        // selectively remove agents
        iter = set.iterator();
        while (iter.hasNext()) {
            AgentInfo info = (AgentInfo) agentInfos.remove((String) iter.next());
            if (info != null) {
                visibleAgents.remove(info);
            }
        }

        agentsOnContainer.remove(containerAddress);
        dataMatrices.rebuildMatrices(visibleAgents);
        recalcAgentPositions();
        guiCanvas.repaint();
    }

    /**
     * Handles a message that was sent.
     * Updates visibleAgents and invisibleAgents lists,
     * opacityMatrix and msgCountMatrix.
     * @param m Message
     * @param undeliverable boolean
     */
    synchronized void incomingMessageCopy(final Message m, final boolean undeliverable) {
        
        
        String senderAddress = m.getSender();
        String senderContainerAddress = m.getSender();
        String senderContainerName = (String) containerNames.get(senderContainerAddress);
        boolean isSenderVisible = false;
        int senderMatrixIndex = -1;

        /* determine sender visibility */
        if (invisibleAgents.contains(senderAddress)) { // invisible
            isSenderVisible = false;
        } else { // maybe visible
            AgentInfo info = (AgentInfo) agentInfos.get(senderAddress);
            if (info == null) { // we don't know this agent yet
                Matcher regexMatcher = regexPattern.matcher(senderAddress.toString());
                if (regexMatcher.find()) { // visible
                    senderMatrixIndex = dataMatrices.getSize();
                    info = new AgentInfo(senderAddress,
                                         senderContainerAddress,
                                         senderContainerName,
                                         senderMatrixIndex);
                    dataMatrices.extendMatrices();
                    dataMatrices.agentInfoMatrix[senderMatrixIndex] = info;
                    agentInfos.put(senderAddress, info);
                    addAgentToGUI(info);

                    Set set = (Set) agentsOnContainer.get(senderContainerAddress);
                    if (set == null) {
                        set = new HashSet();
                        agentsOnContainer.put(senderContainerAddress, set);
                    }
                    set.add(senderAddress);

                    isSenderVisible = true;
                } else { // invisible
                    invisibleAgents.add(senderAddress);
                    isSenderVisible = false;
                }
            } else { // visible
                senderMatrixIndex = info.matrixIndex;
                isSenderVisible = true;
            }
        }

        for (String receiver : m.getReceivers()) {


        
        String receiverAddress = receiver;
        String receiverContainerAddress = receiverAddress; //receiverAddress.deriveContainerAddress();
        String receiverContainerName = (String) containerNames.get(receiverContainerAddress);
        boolean isReceiverVisible = false;
        int receiverMatrixIndex = -1;

        /* determine receiver visibility */
        if (invisibleAgents.contains(receiverAddress)) { // invisible
            isReceiverVisible = false;
        } else { // maybe visible
            AgentInfo info = (AgentInfo) agentInfos.get(receiverAddress);
            if (info == null) { // we don't know this agent yet
                Matcher regexMatcher = regexPattern.matcher(receiverAddress.toString());
                if (regexMatcher.find()) { // visible
                    receiverMatrixIndex = dataMatrices.getSize();
                    info = new AgentInfo(receiverAddress,
                                         receiverContainerAddress,
                                         receiverContainerName,
                                         receiverMatrixIndex);
                    dataMatrices.extendMatrices();
                    dataMatrices.agentInfoMatrix[receiverMatrixIndex] = info;
                    agentInfos.put(receiverAddress, info);
                    addAgentToGUI(info);

                    Set set = (Set) agentsOnContainer.get(receiverContainerAddress);
                    if (set == null) {
                        set = new HashSet();
                        agentsOnContainer.put(receiverContainerAddress, set);
                    }
                    set.add(receiverAddress);

                    isReceiverVisible = true;
                } else { // invisible
                    invisibleAgents.add(receiverAddress);
                    isReceiverVisible = false;
                }
            } else { // visible
                receiverMatrixIndex = info.matrixIndex;
                isReceiverVisible = true;
            }
        }

        if (!isSenderVisible || !isReceiverVisible) {
            return;
        }

        /* add the message to statistics */
        if (senderMatrixIndex > receiverMatrixIndex) {
            dataMatrices.opacityMatrix[senderMatrixIndex][receiverMatrixIndex] = 1.0f;
            dataMatrices.msgCountMatrix[senderMatrixIndex][receiverMatrixIndex]++;
        } else {
            dataMatrices.opacityMatrix[receiverMatrixIndex][senderMatrixIndex] = 1.0f;
            dataMatrices.msgCountMatrix[receiverMatrixIndex][senderMatrixIndex]++;
        }
        MessageInfo info = new MessageInfo(senderAddress, senderMatrixIndex,
                                           receiverAddress, receiverMatrixIndex,
                                           timeStamp);
        messages.addLast(info);

        }
        
        guiCanvas.repaint();
    }

    synchronized public void setGUIOffset(int x, int y, boolean doRepaint) {
        guiOffsetX = x;
        guiOffsetY = y;

        recalcAgentPositions();
        if (doRepaint) {
            guiCanvas.repaint();
        }
    }

    synchronized int getGUIOffsetX() {
        return guiOffsetX;
    }

    synchronized int getGUIOffsetY() {
        return guiOffsetY;
    }

    /**
     * Returns GUI rendering scale.
     *
     * @return float
     */
    synchronized float getGUIScale() {
        return guiScale;
    }

    /**
     * Sets GUI rendering scale (1.0 by default).
     *
     * @param guiScale float
     * @param doRepaint boolean
     */
    synchronized void setGUIScale(float guiScale, boolean doRepaint) {
        if (guiScale < 0.05) {
            guiScale = 0.05f;
        }
        if (guiScale > 10.0) {
            guiScale = 10.0f;
        }
        this.guiScale = guiScale;
        int fontSize;
        if (guiScale < 1) {
            fontSize = Math.round(guiScale * FONT_SIZE);
            agentBoxWidth = Math.round(guiScale * AGENT_BOX_WIDTH);
            agentBoxHeight = Math.round(guiScale * AGENT_BOX_HEIGHT);
            agentBoxBorder = Math.round(guiScale * AGENT_BOX_BORDER);
        } else {
            fontSize = FONT_SIZE;
            agentBoxWidth = AGENT_BOX_WIDTH;
            agentBoxHeight = AGENT_BOX_HEIGHT;
            agentBoxBorder = AGENT_BOX_BORDER;
        }
        if (fontSize < 3) {
            fontScaled = null;
            fontMetricsScaled = null;
            for (int i = 0; i < visibleAgents.size(); i++) {
                AgentInfo info = (AgentInfo) visibleAgents.get(i);
                info.agentNameWrappedScaled = null;
                info.containerNameWrappedScaled = null;
            }
        } else {
            fontScaled = new Font("Default", Font.PLAIN, fontSize);
            fontMetricsScaled = guiCanvas.getFontMetrics(fontScaled);
            int maxStringWidth = Math.round(guiScale * (AGENT_BOX_WIDTH -
                    AGENT_BOX_BORDER * 2));
            for (int i = 0; i < visibleAgents.size(); i++) {
                AgentInfo info = (AgentInfo) visibleAgents.get(i);
                info.agentNameWrappedScaled = wrapString(info.agentName, fontMetricsScaled,
                        maxStringWidth);
                info.containerNameWrappedScaled = wrapString(info.containerName, fontMetricsScaled,
                        maxStringWidth);
            }
        }

        recalcAgentPositions();
        if (doRepaint) {
            guiCanvas.repaint();
        }
    }

    /**
     * Returns X radius of the rendered ellipse.
     *
     * @return float
     */
    synchronized float getGUIRadiusX() {
        return guiRadiusX;
    }

    /**
     * Returns Y radius of the rendered ellipse.
     *
     * @return float
     */
    synchronized float getGUIRadiusY() {
        return guiRadiusY;
    }

    /**
     * Returns the current regular expression used for filtering displayed
     * agents/services.
     *
     * @return String
     */
    synchronized String getRegularExpression() {
        return regexString;
    }

    /**
     * Sets the current regular expression used for filtering displayed
     * agents/services.
     *
     * @param regularExpression String
     * @throws IllegalArgumentException
     */
    synchronized void setRegularExpression(String regularExpression) throws IllegalArgumentException {
        this.regexString = regularExpression;
        this.regexPattern = Pattern.compile(regularExpression,
                                            Pattern.CASE_INSENSITIVE);
        init();
        guiCanvas.repaint();
    }

    synchronized void zoomAgent(int mouseX, int mouseY) {
        AgentInfo[] agentInfoMatrix = dataMatrices.agentInfoMatrix;
        int count = dataMatrices.getSize();
        for (int i = 0; i < count; i++) {
            AgentInfo info = agentInfoMatrix[i];
            if (mouseX >= info.boxTopLeftX &&
                mouseX <= info.boxTopLeftX + agentBoxWidth &&
                mouseY >= info.boxTopLeftY &&
                mouseY <= info.boxTopLeftY + agentBoxHeight) {
                if (info != zoomedAgent) {
                    zoomedAgent = info;
                    guiCanvas.repaint();
                }
                return;
            }
        }
        if (zoomedAgent != null) {
            zoomedAgent = null;
            guiCanvas.repaint();
        }
    }

    /**
     * Draws all agents/services and messages.
     * @param g Graphics
     */
    synchronized public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        drawMessages(g2d);
        int count = visibleAgents.size();
        for (int i = 0; i < count; i++) {
            drawAgent(g2d, (AgentInfo) visibleAgents.get(i));
        }
        if (zoomedAgent != null) {
            drawAgent(g2d, zoomedAgent);
        }
    }

    /**
     * Recalculates all agent positions in GUI.
     */
    synchronized void recalcAgentPositions() {
        int panelWidthHalf = guiCanvas.getWidth() / 2;
        int panelHeightHalf = guiCanvas.getHeight() / 2;
        guiRadiusX = panelWidthHalf - AGENT_BOX_WIDTH / 2 - PANEL_BORDER;
        guiRadiusY = panelHeightHalf - AGENT_BOX_HEIGHT / 2 - PANEL_BORDER;
        if (guiScale > 1) {
            guiRadiusX *= guiScale;
            guiRadiusY *= guiScale;
        }
        int centerX = Math.round(guiScale * guiOffsetX) + panelWidthHalf;
        int centerY = Math.round(guiScale * guiOffsetY) + panelHeightHalf;
        int agentBoxWidthHalf = agentBoxWidth / 2;
        int agentBoxHeightHalf = agentBoxHeight / 2;
        int count = visibleAgents.size();
        double angleStep = Math.PI * 2 / count;
        double angle = 0;
        for (int i = 0; i < count; i++) {
            AgentInfo info = (AgentInfo) visibleAgents.get(i);
            info.boxCenterX = (int) Math.round(centerX + guiRadiusX * Math.sin(angle));
            info.boxCenterY = (int) Math.round(centerY + guiRadiusY * Math.cos(angle));
            info.boxTopLeftX = info.boxCenterX - agentBoxWidthHalf;
            info.boxTopLeftY = info.boxCenterY - agentBoxHeightHalf;
            angle += angleStep;
        }
    }

    /**
     * Adds an agent to Vector of agents displayed in GUI
     * @param info AgentInfo
     */
    synchronized private void addAgentToGUI(AgentInfo info) {
        for (int i = 0; i < visibleAgents.size(); i++) {
            if (((AgentInfo) visibleAgents.get(i)).containerAddress.equals(info)) {
                visibleAgents.add(i, info);
                recalcAgentPositions();
                return;
            }
        }
        visibleAgents.add(info);
        recalcAgentPositions();
    }

    /**
     * Decrease opacity value of all messages (used for visualisation)
     * and update agents' isActive status.
     */
    synchronized private void decreaseOpacity() {
        AgentInfo[] agentInfoMatrix = dataMatrices.agentInfoMatrix;
        float opacityMatrix[][] = dataMatrices.opacityMatrix;
        int count = dataMatrices.getSize();
        for (int i = 0; i < count; i++) {
            agentInfoMatrix[i].isActive = false;
        }
        float delta = (float) paramRefreshTime / paramFadingTime;
        for (int i = 0; i < count; i++) {
            for (int j = 0; j <= i; j++) {
                if ((opacityMatrix[i][j] -= delta) <= 0) {
                    opacityMatrix[i][j] = 0;
                } else {
                    agentInfoMatrix[i].isActive = true;
                    agentInfoMatrix[j].isActive = true;
                }
            }
        }
    }

    /**
     * Removes all "old" messages,
     * i.e. those that are no longer inside the current time frame.
     * @param timeFrame long
     */
    synchronized private void removeOldMessages(long timeFrame) {
        while (!messages.isEmpty()) {
            MessageInfo msg = (MessageInfo) messages.getFirst();
            if (msg.timeStamp >= timeStamp - timeFrame) {
                break;
            }
            if (msg.senderMatrixIndex > msg.receiverMatrixIndex) {
                dataMatrices.msgCountMatrix[msg.senderMatrixIndex][msg.receiverMatrixIndex]--;
            } else {
                dataMatrices.msgCountMatrix[msg.receiverMatrixIndex][msg.senderMatrixIndex]--;
            }
            messages.removeFirst();
        }
    }

    /**
     * Wraps the string to fit into maxWidth.
     * @param string String
     * @param fontMetrics FontMetrics
     * @param maxWidth int
     * @return Vector
     */
    synchronized private Vector wrapString(String string, FontMetrics fontMetrics,
                                           int maxWidth) {
        Vector v = new Vector();
        if (string == null || fontMetrics == null) {
            return v;
        }
        String remains = string;
        while (true) {
            if (fontMetrics.stringWidth(remains) <= maxWidth) { // nothing to split
                v.add(remains);
                return v;
            }
            /* find the first char in string "remains" that exceeds maxWidth */
            int width = 0;
            int i;
            for (i = 0; ; i++) {
                width += fontMetrics.charWidth(remains.charAt(i));
                if (width > maxWidth) {
                    break;
                }
            }
            /* split the string */
            v.add(remains.substring(0, i));
            remains = "  " + remains.substring(i);
        }
    }

    /**
     * Draws a single agent/service.
     * @param g Graphics
     * @param info AgentInfo
     */
    synchronized private void drawAgent(Graphics2D g, AgentInfo info) {
        int x, y;
        int boxWidth, boxHeight, boxBorder;
        Font font;
        FontMetrics fontMetrics;
        Vector containerNameWrapped, agentNameWrapped;
        if (zoomedAgent == info) {
            // agent zoomed => draw unscaled (100%)
            x = info.boxCenterX - AGENT_BOX_WIDTH / 2;
            y = info.boxCenterY - AGENT_BOX_HEIGHT / 2;
            boxWidth = AGENT_BOX_WIDTH;
            boxHeight = AGENT_BOX_HEIGHT;
            boxBorder = AGENT_BOX_BORDER;
            font = fontDefault;
            fontMetrics = fontMetricsDefault;
            containerNameWrapped = info.containerNameWrappedDefault;
            agentNameWrapped = info.agentNameWrappedDefault;
        } else {
            // draw scaled
            x = info.boxTopLeftX;
            y = info.boxTopLeftY;
            boxWidth = agentBoxWidth;
            boxHeight = agentBoxHeight;
            boxBorder = agentBoxBorder;
            font = fontScaled;
            fontMetrics = fontMetricsScaled;
            containerNameWrapped = info.containerNameWrappedScaled;
            agentNameWrapped = info.agentNameWrappedScaled;
        }
        g.setComposite(ALPHA_COMPOSITE_OPAQUE);
        if (info.isService) {
            g.setPaint(COLOR_SERVICE_FILL);
        } else {
            g.setPaint(COLOR_AGENT_FILL);
        }
        g.setStroke(STROKE_WIDTH_1);
        g.fillRect(x, y, boxWidth, boxHeight);
        g.setPaint(info.isActive ? Color.RED : Color.BLACK);
        g.drawRect(x, y, boxWidth, boxHeight);

        if (font != null) {
            g.setFont(font);
            g.setClip(x, y, boxWidth, boxHeight);
            x += boxBorder;
            y += boxBorder;
            int lineHeight = fontMetrics.getHeight();
            for (int i = 0; i < containerNameWrapped.size(); i++) {
                y += lineHeight;
                g.drawString((String) containerNameWrapped.get(i), x, y);
            }
            for (int i = 0; i < agentNameWrapped.size(); i++) {
                y += lineHeight;
                g.drawString((String) agentNameWrapped.get(i), x, y);
            }
            g.setClip(null);
        }

    }

    synchronized private void drawMessages(Graphics2D g) {
        int count = visibleAgents.size();
        for (int row = 0; row < count; row++) {
            for (int column = 0; column < row; column++) {
                float alpha = dataMatrices.opacityMatrix[row][column];
                if (alpha > 0) {
                    int msgCount = dataMatrices.msgCountMatrix[row][column];
                    if (msgCount > 0) {
                        AgentInfo infoRow = dataMatrices.agentInfoMatrix[row];
                        AgentInfo infoColumn = dataMatrices.agentInfoMatrix[column];

                        float color = msgCount * paramMsgCountSensitivity;
                        if (color > 1.0) {
                            color = 1.0f;
                        }
                        float lineWidth = color * paramMaxLineWidth;
                        if (lineWidth < paramMinLineWidth) {
                            lineWidth = paramMinLineWidth;
                        }
                        g.setPaint(new Color(color, 0, 0));
                        g.setStroke(new BasicStroke(lineWidth));
                        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                        g.drawLine(infoRow.boxCenterX, infoRow.boxCenterY,
                                   infoColumn.boxCenterX, infoColumn.boxCenterY);
                    }
                }
            }
        }
    }


    /**
     * Internal structure keeping info about each visible agent/service.
     */
    private class AgentInfo {
        boolean isService; // agent or service?
        String agentAddress;
        String agentName;
        Vector agentNameWrappedDefault;
        Vector agentNameWrappedScaled;
        String containerAddress;
        String containerName;
        Vector containerNameWrappedDefault;
        Vector containerNameWrappedScaled;
        int matrixIndex;
        boolean isActive; // is agent/service currently sending/receiving any messages?

        // top left corner of agent box in GUI, X coordinate
        int boxTopLeftX;
        // top left corner of agent box in GUI, Y coordinate
        int boxTopLeftY;

        // center of agent box in GUI, X coordinate
        int boxCenterX;
        // center of agent box in GUI, Y coordinate
        int boxCenterY;

        AgentInfo(String agentAddress,
                  String containerAddress, String containerName,
                  int matrixIndex) {
            int maxStringWidthDefault = AGENT_BOX_WIDTH - AGENT_BOX_BORDER * 2;
            int maxStringWidthScaled = Math.round(guiScale * (AGENT_BOX_WIDTH - AGENT_BOX_BORDER * 2));
            this.isService = false;//agentAddress.isService();
            this.agentAddress = agentAddress;
            this.agentName = agentAddress;
            this.agentNameWrappedDefault = wrapString(agentName, fontMetricsDefault, maxStringWidthDefault);
            this.agentNameWrappedScaled = wrapString(agentName, fontMetricsScaled, maxStringWidthScaled);
            this.containerAddress = containerAddress;
            this.containerName = containerName;
            this.containerNameWrappedDefault = wrapString(containerName, fontMetricsDefault, maxStringWidthDefault);
            this.containerNameWrappedScaled = wrapString(containerName, fontMetricsScaled, maxStringWidthScaled);
            this.matrixIndex = matrixIndex;
            this.isActive = false;
        }
    }


    /**
     * Internal structure keeping info about each sent message.
     */
    private class MessageInfo {
        String senderAddress;
        int senderMatrixIndex;
        String receiverAddress;
        int receiverMatrixIndex;
        long timeStamp;

        MessageInfo(String senderAddress, int senderMatrixIndex,
                    String receiverAddress, int receiverMatrixIndex,
                    long timeStamp) {
            this.senderAddress = senderAddress;
            this.senderMatrixIndex = senderMatrixIndex;
            this.receiverAddress = receiverAddress;
            this.receiverMatrixIndex = receiverMatrixIndex;
            this.timeStamp = timeStamp;
        }
    }


    /**
     * Internal data matrices.
     */
    private class DataMatrices {
        /**
         * Current total capacity of matrices.
         */
        private int matricesCapacity;

        /**
         * Current size of matrices.
         */
        private int matricesSize;

        /**
         * 1D matrix containing AgentInfos of visible agents.
         * Indices match those used in opacityMatrix and msgCountMatrix.
         */
        private AgentInfo[] agentInfoMatrix;

        /**
         * 2D matrix (agentMatrixIndex * agentMatrixIndex) containing
         * opacity values (used for visualisation).
         */
        private float[][] opacityMatrix;

        /**
         * 2D matrix (agentMatrixIndex * agentMatrixIndex) containing
         * number of messages that came in within the current time frame.
         */
        private int[][] msgCountMatrix;

        /**
         * Matrices initialization.
         */
        private DataMatrices() {
            matricesCapacity = 0;
            matricesSize = 0;
            agentInfoMatrix = new AgentInfo[0];
            opacityMatrix = new float[0][0];
            msgCountMatrix = new int[0][0];
        }

        /**
         * Returns size of data matrices (i.e. number of currently visible agents).
         * @return int
         */
        private int getSize() {
            return matricesSize;
        }

        /**
         * Increases size of matrices by one.
         */
        private void extendMatrices() {
            if (matricesSize + 1 > matricesCapacity) { // we need to increase capacity first
                matricesCapacity += 50;

                AgentInfo[] tmp1 = new AgentInfo[matricesCapacity];
                for (int i = 0; i < matricesSize; i++) {
                    tmp1[i] = agentInfoMatrix[i];
                }
                agentInfoMatrix = tmp1;

                float[][] tmp2 = new float[matricesCapacity][];
                for (int i = 0; i < matricesSize; i++) {
                    tmp2[i] = opacityMatrix[i];
                }
                for (int i = matricesSize; i < matricesCapacity; i++) {
                    tmp2[i] = new float[i + 1];
                }
                opacityMatrix = tmp2;

                int[][] tmp3 = new int[matricesCapacity][];
                for (int i = 0; i < matricesSize; i++) {
                    tmp3[i] = msgCountMatrix[i];
                }
                for (int i = matricesSize; i < matricesCapacity; i++) {
                    tmp3[i] = new int[i + 1];
                }
                msgCountMatrix = tmp3;
            }
            matricesSize++; // increase size
        }

        /**
         * Rebuilds matrices, keeping only currently visibleAgents.
         *
         * @param visibleAgents Vector
         */
        private void rebuildMatrices(Vector visibleAgents) {
            int size = visibleAgents.size();
            int capacity = size + 50;

            // allocate new matrices
            AgentInfo[] tmp1 = new AgentInfo[capacity];
            float[][] tmp2 = new float[capacity][];
            int[][] tmp3 = new int[capacity][];
            for (int i = 0; i < capacity; i++) {
                tmp2[i] = new float[i + 1];
                tmp3[i] = new int[i + 1];
            }

            // fill them with data
            for (int column = 0; column < size; column++) {
                AgentInfo infoColumn = (AgentInfo) visibleAgents.get(column);
                tmp1[column] = infoColumn;
                for (int row = column; row < size; row++) {
                    AgentInfo infoRow = (AgentInfo) visibleAgents.get(row);
                    if (infoRow.matrixIndex > infoColumn.matrixIndex) {
                        tmp2[row][column] = opacityMatrix[infoRow.matrixIndex][infoColumn.matrixIndex];
                        tmp3[row][column] = msgCountMatrix[infoRow.matrixIndex][infoColumn.matrixIndex];
                    } else {
                        tmp2[row][column] = opacityMatrix[infoColumn.matrixIndex][infoRow.matrixIndex];
                        tmp3[row][column] = msgCountMatrix[infoColumn.matrixIndex][infoRow.matrixIndex];
                    }
                }
                infoColumn.matrixIndex = column;
            }

            matricesSize = size;
            matricesCapacity = capacity;
            agentInfoMatrix = tmp1;
            opacityMatrix = tmp2;
            msgCountMatrix = tmp3;
        }

    }


    /**
     * Thread for periodical updates of the current timeStamp.
     */
    private class TimeStamp implements Runnable {
        public void run() {
            while (true) {
                timeStamp = java.lang.System.currentTimeMillis();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                }
            }
        }
    }


    /**
     * Thread for periodical refreshing of data values that must change over time.
     */
    private class Refresh implements Runnable {
        public void run() {
            while (true) {
                decreaseOpacity();
                removeOldMessages(paramTimeFrame);
                guiCanvas.repaint();
                try {
                    Thread.sleep(paramRefreshTime);
                } catch (InterruptedException ex) {
                }
            }
        }
    }
}
