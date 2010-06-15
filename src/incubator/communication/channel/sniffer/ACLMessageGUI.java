package incubator.communication.channel.sniffer;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * <p>Title: A-Globe</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
// TODO: complete the javadoc

public class ACLMessageGUI extends JFrame {
  private final DetailMainPanel owner;
  private SnifferDataContainer.MessageCover[] mcs;
  private int curPos;

  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel jPanel1 = new JPanel();
  JButton closeButton = new JButton();
  Border border1;
  TitledBorder titledBorder1;
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField sender = new JTextField();
//  JTextField receiver = new JTextField();
  JComboBox receiver = new JComboBox();
  JLabel jLabel3 = new JLabel();
  JTextField performative = new JTextField();
  JLabel jLabel4 = new JLabel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextPane content = new JTextPane();
  JTextField protocol = new JTextField();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JTextField ontology = new JTextField();
  JLabel jLabel7 = new JLabel();
  JTextField conversationID = new JTextField();
  JLabel jLabel8 = new JLabel();
  JTextField inReplyTo = new JTextField();
  JLabel jLabel9 = new JLabel();
  JTextField replyWith = new JTextField();
  JLabel jLabel10 = new JLabel();
  JTextField reason = new JTextField();
  JLabel jLabel11 = new JLabel();
  JTextField msgId = new JTextField();
  JPanel buttonPanel = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JButton prevButton = new JButton();
  JButton nextButton = new JButton();

  public ACLMessageGUI(DetailMainPanel _owner) {
    owner = _owner;
    try {
      setTitle("ACL Message");
      jbInit();
      pack();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(134, 134, 134));
    titledBorder1 = new TitledBorder(border1,"ACL Message details");
    this.getContentPane().setLayout(gridBagLayout1);
    closeButton.setSelected(true);
    closeButton.setText("Close");
    closeButton.addActionListener(new ACLMessageGUI_closeButton_actionAdapter(this));
    jPanel1.setBorder(titledBorder1);
    jPanel1.setLayout(gridBagLayout2);
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setIconTextGap(4);
    jLabel1.setText("Sender:");
    jLabel2.setVerifyInputWhenFocusTarget(true);
    jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
    jLabel2.setHorizontalTextPosition(SwingConstants.TRAILING);
    jLabel2.setText("Receiver:");
    sender.setPreferredSize(new Dimension(300, 21));
    sender.setEditable(false);
    sender.setText("");
    receiver.setEditable(false);
//    receiver.setText("");
    receiver.addItem("");
    jLabel3.setText("Performative:");
    performative.setEditable(false);
    performative.setText("");
    performative.setBackground(Color.WHITE);
    jLabel4.setText("Content:");
    jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    content.setBackground(Color.LIGHT_GRAY);
    content.setOpaque(false);
    content.setPreferredSize(new Dimension(300, 84));
    content.setDisabledTextColor(Color.LIGHT_GRAY);
    content.setEditable(false);
    content.setText("");
    protocol.setEditable(false);
    protocol.setText("");
    jLabel5.setText("Protocol:");
    jLabel6.setText("Ontology:");
    ontology.setToolTipText("");
    ontology.setVerifyInputWhenFocusTarget(true);
    ontology.setEditable(false);
    ontology.setText("");
    jLabel7.setText("ConversationID:");
    conversationID.setEditable(false);
    conversationID.setText("");
    jLabel8.setText("InReplyTo:");
    inReplyTo.setEnabled(true);
    inReplyTo.setEditable(false);
    inReplyTo.setText("");
    jLabel9.setText("ReplyWith:");
    replyWith.setEditable(false);
    replyWith.setSelectionStart(11);
    replyWith.setText("");
    jLabel10.setText("Reason:");
    reason.setEditable(false);
    reason.setText("");
    this.setResizable(false);
    jLabel11.setText("Sniffer msgId:");
    msgId.setDoubleBuffered(false);
    msgId.setEditable(false);
    msgId.setText("jTextField1");
    buttonPanel.setLayout(gridBagLayout3);
    prevButton.setText("<< Conversation");
    prevButton.addActionListener(new ACLMessageGUI_prevButton_actionAdapter(this));
    nextButton.setText("Converation >>");
    nextButton.addActionListener(new ACLMessageGUI_nextButton_actionAdapter(this));
    this.getContentPane().add(jPanel1,       new GridBagConstraints(1, 0, 1, 1, 0.1, 0.1
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    buttonPanel.add(closeButton,          new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 20, 5, 20), 0, 0));
    buttonPanel.add(prevButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    buttonPanel.add(nextButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(buttonPanel,  new GridBagConstraints(1, 1, 1, 1, 0.1, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel1,         new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(jLabel2,       new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(sender,          new GridBagConstraints(2, 1, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(receiver,     new GridBagConstraints(2, 2, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel3,       new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(performative,      new GridBagConstraints(2, 3, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel4,        new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 0), 0, 0));
    jPanel1.add(jScrollPane1,         new GridBagConstraints(2, 4, 1, 1, 0.1, 0.1
            ,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
    jScrollPane1.getViewport().add(content, null);
    jPanel1.add(protocol,     new GridBagConstraints(2, 5, 1, 1, 0.1, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel5,     new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(jLabel6,     new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(ontology,    new GridBagConstraints(2, 6, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel7,    new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(conversationID,    new GridBagConstraints(2, 7, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel8,    new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(inReplyTo,    new GridBagConstraints(2, 8, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel9,    new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(replyWith,    new GridBagConstraints(2, 9, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel10,    new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(reason,    new GridBagConstraints(2, 10, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    jPanel1.add(jLabel11,     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));
    jPanel1.add(msgId,    new GridBagConstraints(2, 0, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
  }

  private String parseText(Object object) {
    return (object != null)?object.toString():"{null}";
  }

  public void setACLMessage(final SnifferDataContainer.MessageCover[] mc,final int pos) {
    this.mcs = mc;
    this.curPos = pos;
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        msgId.setText(Long.toString(mc[pos].msgId));
        sender.setText(parseText(mc[pos].msg.getSender()));
//        receiver.setText(parseText(mc[pos].msg.getReceiver()));
        receiver.removeAllItems();
//        Iterator iter = mc[pos].msg.getAllIntendedReceiver();
//        while (iter.hasNext()) {
//            Object item = (Object) iter.next();
        for (String receiverS : mc[pos].msg.getReceivers()) {
            receiver.addItem(receiverS);
          }
//        }
//        performative.setText(parseText(mc[pos].msg.getPerformative()));
        //performative.setText(parseText(ACLMessage.getPerformative(mc[pos].msg.getPerformative())));
        performative.setText(parseText(mc[pos].performative.toString()));
        performative.setForeground(mc[pos].messageColor);
        content.setText(parseText(mc[pos].msg.getContent()));
        protocol.setText(parseText(mc[pos].protocol.getName()));
        //ontology.setText(parseText(mc[pos].msg.getOntology()));
        ontology.setText("Not applicable");
        conversationID.setText(parseText(mc[pos].msg.getId()));
        inReplyTo.setText(parseText(null));
        replyWith.setText(parseText(null));
//        reason.setText(parseText(mc[pos].msg.getReason()));
        reason.setText(parseText("not supported"));
        if (pos > 0) {
          prevButton.setEnabled(true);
        } else {
          prevButton.setEnabled(false);
        }
        if (pos < (mc.length-1)) {
          nextButton.setEnabled(true);
        } else {
          nextButton.setEnabled(false);
        }
      }
    }
        );
  }

  public void showGUI(final boolean state) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        setVisible(state);
      }
    }
        );
  }

  void closeButton_actionPerformed(ActionEvent e) {
    showGUI(false);
  }

  public void setVisible(boolean b) {
    super.setVisible(b);
    if (!b) owner.clearSelection();
  }

  private void updateSelectedMessage() {
    owner.setSelectedMsg(mcs[curPos].msgId);
  }

  void prevButton_actionPerformed(ActionEvent e) {
    if (curPos > 0) {
      curPos--;
      this.setACLMessage(mcs,curPos);
      updateSelectedMessage();
    }
  }

  void nextButton_actionPerformed(ActionEvent e) {
    if (curPos < (mcs.length - 1)) {
      curPos++;
      this.setACLMessage(mcs,curPos);
      updateSelectedMessage();
    }
  }

}

class ACLMessageGUI_closeButton_actionAdapter implements java.awt.event.ActionListener {
  ACLMessageGUI adaptee;

  ACLMessageGUI_closeButton_actionAdapter(ACLMessageGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.closeButton_actionPerformed(e);
  }
}

class ACLMessageGUI_prevButton_actionAdapter implements java.awt.event.ActionListener {
  ACLMessageGUI adaptee;

  ACLMessageGUI_prevButton_actionAdapter(ACLMessageGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.prevButton_actionPerformed(e);
  }
}

class ACLMessageGUI_nextButton_actionAdapter implements java.awt.event.ActionListener {
  ACLMessageGUI adaptee;

  ACLMessageGUI_nextButton_actionAdapter(ACLMessageGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.nextButton_actionPerformed(e);
  }
}
