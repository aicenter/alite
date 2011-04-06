package incubator.communication.channel.sniffer;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <p>Title: A-Globe</p>
 * <p>Description: agent platform A-Globe</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
// TODO: complete the javadoc

public class SnifferOptionGUI extends JDialog {
  private SnifferGUI owner;

  JPanel panel1 = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton jButtonOK = new JButton();
  JLabel jLabel1 = new JLabel();
  JTextField filterTextField = new JTextField();
  JLabel jLabel2 = new JLabel();
  JTextField msgHistoryLengthTextField = new JTextField();

  private SnifferOptionGUI(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public SnifferOptionGUI(SnifferGUI owner) {
    this(owner, "Options", true);
    this.owner = owner;

    // fill values
    msgHistoryLengthTextField.setText(Integer.toString(owner.owner.msgHistoryLength));
    filterTextField.setText(owner.owner.addressFilter);
  }

  private void jbInit() throws Exception {
    panel1.setLayout(gridBagLayout1);
    jButtonOK.setText("OK");
    jButtonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonOK_actionPerformed(e);
      }
    });
    jLabel1.setText("Address filter:");
    filterTextField.setMinimumSize(new Dimension(6, 21));
    filterTextField.setPreferredSize(new Dimension(150, 21));
    filterTextField.setSelectionStart(0);
    filterTextField.setText("");
    jLabel2.setText("Message history length:");
    getContentPane().add(panel1);
    panel1.add(jButtonOK,         new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(20, 0, 10, 0), 0, 0));
    panel1.add(jLabel1,        new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
    panel1.add(filterTextField,           new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
    panel1.add(jLabel2,     new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 5, 0, 5), 0, 0));
    panel1.add(msgHistoryLengthTextField,  new GridBagConstraints(1, 0, 1, 1, 0.1, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(10, 5, 0, 5), 0, 0));
  }

  void jButtonOK_actionPerformed(ActionEvent e) {
    try {
      int newMsgHistoryLength = Integer.parseInt(msgHistoryLengthTextField.
                                                 getText());
      if (newMsgHistoryLength != owner.owner.msgHistoryLength) {
        owner.owner.updateMsgHistoryLength(newMsgHistoryLength);
      }
    }
    catch (NumberFormatException ex) {
    }

    if (!filterTextField.getText().equalsIgnoreCase(owner.owner.addressFilter)) {
      owner.owner.updateAddressFilter(filterTextField.getText());
    }

    this.dispose();
  }
}
