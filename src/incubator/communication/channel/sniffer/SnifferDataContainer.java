
package incubator.communication.channel.sniffer;

import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.protocol.Performative;
import cz.agents.alite.communication.protocol.Protocol;
import cz.agents.alite.communication.protocol.ProtocolContent;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

//import aglobe.container.transport.*;

//import aglobe.ontology.*;

/**
 * <p>Title: A-Globe</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 *
 *
 */
// TODO: complete the javadoc

public class SnifferDataContainer {
  /**
     * Agent who owns the <code>SnifferDataContainer</code>
     */
  private final SnifferWrapper owner;

  private DetailHeaderPanel headerPanel = null;
  private DetailMainPanel mainPanel = null;
  private LeftHeaderPanel leftHeaderPanel = null;
  JTree containerTree = null;

  /**
   * Contains ContainerRecord, key is address.
   */

  final HashMap containers = new HashMap(); // contains ContainerRecord , key is address
  /**
   * Contains AgentRecord, key is address.
   */
  final HashMap agents = new HashMap();  // contains AgentRecord, key is address

  /**
   * Msg id of the next incoming message
   */
  private long nextMsgId = 1;

  /**
   * Contains all messages.
   */
  private ArrayList allMsg;  //contains all messages
  /**
   * If it is true, all containers are on the screen.
   */
  private boolean showAllContainers = true;
  /**
   * Contains messages which are shown.
   */
  ArrayList filteredMsg;  // contains messages which are shown

  /**
   * Current filter. Contains object that may be shown in the result.
   */
  ArrayList filteredObjects = new ArrayList(200);

  private int lastShowPosition = 0;
  /**
   * Colors of message types.
   */
  private final HashMap messageTypeColors = new HashMap();

  private final SnifferTreeModel treeModel;

  @SuppressWarnings(value={"unchecked"}) 
  public SnifferDataContainer(SnifferWrapper _owner) {
    owner = _owner;
    allMsg = new ArrayList(owner.msgHistoryLength);
    filteredMsg = new ArrayList(owner.msgHistoryLength);

    for(int i=0; i<DrawingConstants.MESSAGE_TYPES.length; i++) {
      messageTypeColors.put(DrawingConstants.MESSAGE_TYPES[i],DrawingConstants.MESSAGE_TYPE_COLORS[i]);
    }
    treeModel = new SnifferTreeModel(this);
//    ContainerRecord container = new ContainerRecord(_owner.getContainer().getContainerName(),
//        owner.getAddress().deriveContainerAddress());
//    ContainerRecord container = new ContainerRecord("JADE","JADE");
    ContainerRecord container = new ContainerRecord("PANDA","PANDA");
//            ""+_owner.getAID().getName().subSequence(_owner.getAID().getName().indexOf("@")+1, _owner.getAID().getName().length()),
//            ""+_owner.getAID().getName().subSequence(_owner.getAID().getName().indexOf("@")+1, _owner.getAID().getName().length())
//            );
    containers.put(container.address,container);

    if (showAllContainers) {
      if (owner.addressFilterPattern.matcher(SnifferTreeModel.OTHERS).find()) {
        filteredObjects.add(container);
        container.showPosition = lastShowPosition;
        container.countFromThisContainer = 1;
        container.isLast = true;
        lastShowPosition++;
      }
      treeModel.containersAdded(container);
    }
  }

  synchronized void setHeaderPanel(DetailHeaderPanel _new) {
    headerPanel = _new;
  }

  synchronized void setMainPanel(DetailMainPanel _new) {
    mainPanel = _new;
  }

  synchronized void setLeftHeaderPanel(LeftHeaderPanel _new) {
    leftHeaderPanel = _new;
  }

  synchronized void setContainerTree(JTree _new) {
    containerTree = _new;
  }

  TreeModel getTreeModel() {
    return treeModel;
  }

  TreeCellRenderer getTreeCellRenderer() {
    return treeModel.getCellRenderer();
  }

  MouseListener getMouseListener(JTree owner) {
    return treeModel.getMouseListener(owner);
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void loginContainer(String _name,String _address) {
    ContainerRecord container = (ContainerRecord)containers.get(_address);
    if (container == null) {  //add new container
      ContainerRecord sameContainer = getContainerWithSameName(_name);  //if exists container with same name, remove it first
      if (sameContainer != null) removeThisContainer(sameContainer);
      container = new ContainerRecord(_name,_address);
      containers.put(_address, container);
      if (showAllContainers) {
        if (owner.addressFilterPattern.matcher(SnifferTreeModel.OTHERS).find()) {
          container.showPosition = lastShowPosition;
          container.countFromThisContainer = 1;
          container.isLast = true;
          lastShowPosition++;
          filteredObjects.add(container);
          repaintAll();
        }
        treeModel.containersAdded(container);
      }
    } else {   // container already exist
      container.setLogged(true);
      repaintAll();
    }
  }

  synchronized void logoutContainer(String _name) {
    ContainerRecord container = null;
    for (Iterator iter = containers.values().iterator(); iter.hasNext(); ) {
      ContainerRecord item = (ContainerRecord)iter.next();
      if (item.getContainer().name.equalsIgnoreCase(_name)) {
        container = item;
        break;
      }
    }
    if (container != null) {
      container.setLogged(false);
      repaintAll();
    }
  }

  synchronized void dontSniff(Record record, boolean updateHistory) {
    //don't sniff it
    filteredObjects.remove(record);
    rebuildShowLists();
    rebuildFilteredMsg();
    mainPanel.hideACLMessageWindow();
    repaintAll();
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void doSniff(Record record, boolean updateHistory) {
    //start sniff it
    ContainerRecord container = record.getContainer();
    boolean founded = false;
    boolean copyRest = false;
    ContainerRecord actualRecord;
    ArrayList _newFilteredObjects = new ArrayList();
    for (Iterator iter = filteredObjects.iterator(); iter.hasNext(); ) {
      Record item = (Record)iter.next();
      if (copyRest) {
        _newFilteredObjects.add(item);
        continue;
      }
      actualRecord = item.getContainer();
      if (actualRecord == container) {
        if (!founded) founded = true;
      } else {
        if (founded) {
          _newFilteredObjects.add(record);
          copyRest = true;
        }
      }
      _newFilteredObjects.add(item);
    }
    if (!copyRest) _newFilteredObjects.add(record);
    filteredObjects = _newFilteredObjects;
    rebuildShowLists();
    if (updateHistory) {
      rebuildFilteredMsg();
      mainPanel.hideACLMessageWindow();
      repaintAll();
    } else {
      headerPanel.repaintThis();
      containerTree.repaint();
    }
  }

  synchronized void changeShowing(Record record, boolean updateHistory) {
    if (record.showPosition >= 0)
      dontSniff(record,updateHistory);
    else
      doSniff(record,updateHistory);
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void moveActorLeft(Record record) {
    if (!record.isFirstFromThisContainer() && record.isSniffed()) {
      int actPosition = filteredObjects.indexOf(record);
      filteredObjects.remove(actPosition);
      filteredObjects.add(actPosition-1,record);
      rebuildShowLists();
      rebuildFilteredMsg();
      mainPanel.hideACLMessageWindow();
      repaintAll();
    }
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void moveActorRight(Record record) {
    if (!record.isLast && record.isSniffed()) {
      int actPosition = filteredObjects.indexOf(record);
      filteredObjects.remove(actPosition);
      filteredObjects.add(actPosition+1,record);
      rebuildShowLists();
      rebuildFilteredMsg();
      mainPanel.hideACLMessageWindow();
      repaintAll();
    }
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void moveContainerLeft(Record record) {
    if (record.isSniffed()) {
      int first = getFirstFromThisContainer(record);
      int len = ((Record)filteredObjects.get(first)).countFromThisContainer;
      ArrayList buf = new ArrayList();
      for (int i=0; i<len; i++)
        buf.add(0,filteredObjects.remove(first));
      int before = getFirstFromThisContainer((Record)filteredObjects.get(first-1));
      for (int i=0; i<len; i++)
        filteredObjects.add(before,buf.get(i));
      rebuildShowLists();
      rebuildFilteredMsg();
      mainPanel.hideACLMessageWindow();
      repaintAll();
    }
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void moveContainerRight(Record record) {
    if (record.isSniffed()) {
      int first = getFirstFromThisContainer(record);
      int len = ((Record)filteredObjects.get(first)).countFromThisContainer;
      ArrayList buf = new ArrayList();
      for (int i=0; i<len; i++)
        buf.add(0,filteredObjects.remove(first));
      int before = ((Record)filteredObjects.get(first)).countFromThisContainer + first;
      for (int i=0; i<len; i++)
        filteredObjects.add(before,buf.get(i));
      rebuildShowLists();
      rebuildFilteredMsg();
      mainPanel.hideACLMessageWindow();
      repaintAll();
    }
  }

  /**
   * History length changed. Update the gui and history queue
   */
  synchronized void updateMsgHistoryLength() {
    // need remove some messages
    while (allMsg.size() >= owner.msgHistoryLength) {
      // remove first message
      MessageCover rmMc = (MessageCover)allMsg.remove(0);
      if (((FilteredMessageCover)filteredMsg.get(0)).msgCover == rmMc) {
        filteredMsg.remove(0);
      }
    }
    final long msgSize = allMsg.size();
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        owner.gui.historyUsed.setText(Long.toString(msgSize));
      }
    });
    // update gui
    mainPanel.repaintThis();
    leftHeaderPanel.repaintThis();
  }

  @SuppressWarnings(value={"unchecked"}) 
  synchronized void incomingMessageCopy(Message m, boolean undeliverable) {

      
      //System.out.println("---#-#-#---MESS---"+m.toString());
      
//      Iterator receivers = m.getAllReceiver();
//      while (receivers.hasNext()) {

      for (String receiver : m.getReceivers()) {



          AgentRecord from,to=null;
          from = getAgentRecord(m.getSender());
          to = getAgentRecord(receiver);


      //    System.out.println("Message from "+from.address.getLocalName()+" to "+to.address.getLocalName());
          if (from == null || to == null || from == to)return;
          final long a = nextMsgId;
          SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                  owner.gui.parsedMsg.setText(Long.toString(a));
              }
          });
          MessageCover mc = new MessageCover(from, to, undeliverable, m, nextMsgId++);
          if (allMsg.size() == owner.msgHistoryLength - 1) {
              // remove first message
              MessageCover rmMc = (MessageCover) allMsg.remove(0);
              if (((FilteredMessageCover) filteredMsg.get(0)).msgCover == rmMc) {
                  filteredMsg.remove(0);
              }
          }
          allMsg.add(mc);
          final long msgSize = allMsg.size();
          SwingUtilities.invokeLater(new Runnable() {
              public void run() {
                  owner.gui.historyUsed.setText(Long.toString(msgSize));
              }
          });
          if (parseMessage(mc)) {
              mainPanel.repaintLastMessage();
              leftHeaderPanel.repaintLastMessage();
          }
      }
      
  }


  @SuppressWarnings(value={"unchecked"}) 
  private boolean parseMessage(MessageCover mc) {
    int from,to;
    from = mc.from.showPosition;
    if (from<0) {
      from = mc.from.container.showPosition;
      if (from<0) return false;
    }
    to = mc.to.showPosition;
    if (to<0) {
      to = mc.to.container.showPosition;
      if (to<0) return false;
    }
    if (from == to) return false;
    filteredMsg.add(new FilteredMessageCover(from,to,mc));
    return true;
  }

  @SuppressWarnings(value={"unchecked"}) 
  private AgentRecord getAgentRecord(String agentAdress) {
    AgentRecord retVal;
    retVal = (AgentRecord)agents.get(agentAdress);
    if (retVal == null) {
//      ContainerRecord container = (ContainerRecord)containers.get(agentAdress.deriveContainerAddress());
// TODO - what to do with the containers
      ContainerRecord container = (ContainerRecord)containers.get("PANDA");
      if (container!=null) {
        retVal = new AgentRecord(container,agentAdress);
        agents.put(agentAdress,retVal);
        treeModel.agentAdded(retVal);
        if (owner.addressFilterPattern.matcher(agentAdress.toString()).find()) {
          changeShowing(retVal,false);
        }
      }
    }
    return retVal;
  }

  private void repaintAll() {
    mainPanel.repaintThis();
    headerPanel.repaintThis();
    leftHeaderPanel.repaintThis();
    containerTree.repaint();
  }

  private ContainerRecord getContainerWithSameName(String name) {
    for (Iterator iter = containers.values().iterator(); iter.hasNext(); ) {
      ContainerRecord item = (ContainerRecord)iter.next();
      if (item.name.equalsIgnoreCase(name)) return item;
    }
    return null;
  }

  private void removeAllFromMsg(AgentRecord agent) {
    for (Iterator iter = allMsg.iterator(); iter.hasNext(); ) {
      MessageCover item = (MessageCover)iter.next();
      if ((item.from == agent)||(item.to == agent)) iter.remove();
    }
  }

  private void removeThisContainer(ContainerRecord container) {
    containers.values().remove(container);
    filteredObjects.remove(container);
    for (Iterator iter = agents.values().iterator(); iter.hasNext(); ) {
      AgentRecord ar = (AgentRecord)iter.next();
      if (container == ar.container) {
        iter.remove();
        filteredObjects.remove(ar);
        removeAllFromMsg(ar);
      }
    }
    rebuildShowLists();
    rebuildFilteredMsg();
    mainPanel.hideACLMessageWindow();
    repaintAll();
    treeModel.containersRemoved(container);
  }

  private void rebuildShowLists() {
    lastShowPosition = 0;
    for (Iterator iter = containers.values().iterator(); iter.hasNext(); ) {
      ContainerRecord item = (ContainerRecord)iter.next();
      item.showPosition = -1;
      item.countFromThisContainer = -1;
      item.isLast = false;
    }
    for (Iterator iter = agents.values().iterator(); iter.hasNext(); ) {
      AgentRecord item = (AgentRecord)iter.next();
      item.showPosition = -1;
      item.countFromThisContainer = -1;
      item.isLast = false;
    }
    ContainerRecord actContainer,lastContainer = null;
    int count;
    Iterator iter2 = filteredObjects.iterator();
    Record item2 = null, lastRecord = null;
    if (iter2.hasNext()) item2 = (Record)iter2.next();
    for (Iterator iter = filteredObjects.iterator(); iter.hasNext(); ) {
      Record item = (Record)iter.next();
      item.showPosition = lastShowPosition++;
      actContainer = item.getContainer();
      if (actContainer != lastContainer) {
        count = 0;
        while (item2.getContainer() == actContainer && (++count > 0) && iter2.hasNext()) {
          item2 = (Record)iter2.next();
//          count++;
        }
        lastContainer = actContainer;
        item.countFromThisContainer = count;
        if (lastRecord != null)
          lastRecord.isLast = true;
      }
      lastRecord = item;
    }
    if (lastRecord != null)
      lastRecord.isLast = true;
  }

  private void rebuildFilteredMsg() {
    filteredMsg.clear();
    for (Iterator iter = allMsg.iterator(); iter.hasNext(); ) {
      MessageCover item = (MessageCover)iter.next();
      parseMessage(item);
    }
  }

  private int getFirstFromThisContainer(Record record) {
    int actPosition = filteredObjects.indexOf(record);
    while (!((Record)filteredObjects.get(actPosition)).isFirstFromThisContainer())
      actPosition--;
    return actPosition;
  }

  synchronized private boolean testFirstContainer(Record record) {
    int actPosition = getFirstFromThisContainer(record);
    return (actPosition == 0);
  }

  synchronized private boolean testLastContainer(Record record) {
    int actPosition = getFirstFromThisContainer(record);
    return ((actPosition + ((Record)filteredObjects.get(actPosition)).countFromThisContainer) >= filteredObjects.size());
  }

  public class Record {
    public int showPosition = -1;
    public int countFromThisContainer = -1;
    public boolean isLast = false;

    private Record() {};

    public boolean isSniffed() {
      return (showPosition>=0);
    }

    public ContainerRecord getContainer() {
      if (this instanceof ContainerRecord)
        return (ContainerRecord)this;
      return ((AgentRecord)this).container;
    }

    public boolean isFirstFromThisContainer() {
      return (countFromThisContainer >= 0);
    }

    public boolean isFisrtContainer() {
      if (!isSniffed()) return false;
      return testFirstContainer(this);
    }

    public boolean isLastContainer() {
      if (!isSniffed()) return false;
      return testLastContainer(this);
    }
  }

  class ContainerRecord extends Record {
    public final String name;
    public final String address;
    private boolean logged;

    public ContainerRecord(String _name, String _address) {
      name = _name;
      address = _address;
      logged = true;
    }

    public boolean isLogged() {
      return logged;
    }

    public void setLogged(boolean _status) {
      logged = _status;
    }
  }

  class AgentRecord extends Record {
    public final ContainerRecord container;
    //public final AID address;
    public final boolean isAgent;
    public final String name;

    public AgentRecord(ContainerRecord _container, String _name) {
      container = _container;
      //address = _address;
//      isAgent = address.isAgent();
      isAgent = true;
      name = _name;
    }
  }

  class MessageCover {
    public final AgentRecord from;
    public final AgentRecord to;
    public final long msgId;
    public final boolean undeliverable;
    public final Color messageColor;
    public final Performative performative;
    public final Protocol protocol;
    public final Message msg;

    public MessageCover(AgentRecord _from, AgentRecord _to, boolean _undeliverable, Message _msg, long msgId) {
      from = _from;
      to = _to;
      undeliverable = _undeliverable;
      msg = _msg;
      this.msgId = msgId;

      Color col = null;
      if (msg.getContent()instanceof ProtocolContent) {
            protocol = ((ProtocolContent)msg.getContent()).getProtocol();
            performative = ((ProtocolContent)msg.getContent()).getPerformative();
            col = (Color)messageTypeColors.get(performative.toString());//ACLMessage.getPerformative(msg.getPerformative()));
      } else {
          performative = null;
          protocol = null;
      }
      
//      System.out.println("---#-#-#---SNIFER-PERF---: " + msg.getPerformative() + " == " + ACLMessage.getPerformative(msg.getPerformative()) +" col="+ ((col != null) ? col.toString() : "(null) DEFUALT"));
      
//      if (msg.getPerformative() == ACLMessage.CFP) {
//          System.out.println("---#-#-#---SNIFFER-CFP---" + ACLMessage.getPerformative(msg.getPerformative()));
//          col.getRGB();
//      }
      
      if (col == null) col = DrawingConstants.OTHER_MESSAGE_TYPE_COLOR;
      messageColor = col;
    }
  }

  class FilteredMessageCover {
    public final int fromPosition;
    public final int toPosition;
    public final MessageCover msgCover;

    public FilteredMessageCover(int _from, int _to, MessageCover _msgCover) {
      fromPosition = _from;
      toPosition = _to;
      msgCover = _msgCover;
    }
  }
}
