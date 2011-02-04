package incubator.communication.channel.sniffer;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * <p>Title: A-Globe</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 *
 */
// TODO: complete the javadoc

public class SnifferAboutGUI extends JFrame {
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton closeButton = new JButton();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  Border border1;
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JLabel jLabel9 = new JLabel();

  public SnifferAboutGUI() {
    try {
      jbInit();
      pack();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(148, 145, 140));
    closeButton.setText("Close");
    closeButton.addActionListener(new AboutGUI_closeButton_actionAdapter(this));
    this.getContentPane().setLayout(gridBagLayout1);
    this.setResizable(false);
    this.setState(Frame.NORMAL);
    this.setTitle("About Sniffer");
    jPanel1.setLayout(gridBagLayout2);
    jLabel1.setFont(new java.awt.Font("Dialog", 1, 16));
    jLabel1.setText("Developed by David");
    jPanel1.setBorder(border1);
    jLabel2.setText(" ");
    jLabel3.setFont(new java.awt.Font("Dialog", 1, 14));
    jLabel3.setText("Gerstner Laboratory");
    jLabel4.setText("Department of Cybernetics");
    jLabel5.setText("Czech Technical University in Prague");
    jLabel6.setText(" ");
    jLabel7.setText("http://agents.felk.cvut.cz");
    jLabel8.setText(" ");
    jLabel9.setFont(new java.awt.Font("Dialog", 1, 28));
    jLabel9.setForeground(new Color(164, 0, 0));
    jLabel9.setText("Sniffer Agent v 1.0b");
    this.getContentPane().add(closeButton,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    this.getContentPane().add(jPanel1,     new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 30, 30));
    jPanel1.add(jLabel1,    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel2,    new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel3,    new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel4,    new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel5,    new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel6,    new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel7,    new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel8,   new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel9,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
  }

  void closeButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }

}

class AboutGUI_closeButton_actionAdapter implements java.awt.event.ActionListener {
  SnifferAboutGUI adaptee;

  AboutGUI_closeButton_actionAdapter(SnifferAboutGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.closeButton_actionPerformed(e);
  }
}
