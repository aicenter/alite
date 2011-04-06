package incubator.communication.channel.sniffer;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

/**
 *
 * <p>Title: A-Globe</p>
 *
 * <p>Description: About gui of the comanalyezer tool</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Agent Technology Group, Gerstner Laboratory</p>
 *
 * @author Dusan Pavlicek
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */
class ComAnalyzerAboutGUI extends JDialog implements ActionListener {

    /**
     * Constructor
     */
    ComAnalyzerAboutGUI() {
    Container content = this.getContentPane();
    content.setLayout(new GridBagLayout());

    JLabel jLabel1 = new JLabel("Developed by Dusan Pavlicek");
    jLabel1.setFont(new Font("Dialog", 1, 16));
    JLabel jLabel2 = new JLabel(" ");
    JLabel jLabel3 = new JLabel("Gerstner Laboratory");
    jLabel3.setFont(new Font("Dialog", 1, 14));
    JLabel jLabel4 = new JLabel("Department of Cybernetics");
    JLabel jLabel5 = new JLabel("Czech Technical University in Prague");
    JLabel jLabel6 = new JLabel(" ");
    JLabel jLabel7 = new JLabel("http://gerstner.felk.cvut.cz");
    JLabel jLabel8 = new JLabel(" ");
    JLabel jLabel9 = new JLabel("Communication Analyzer v1.0");
    jLabel9.setFont(new Font("Dialog", 1, 24));
    jLabel9.setForeground(new Color(164, 0, 0));

    JPanel jPanel = new JPanel();
    jPanel.setLayout(new GridBagLayout());
    jPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)));
    jPanel.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel4, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel5, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel6, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel7, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel8, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel.add(jLabel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    content.add(jPanel, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 30, 30));

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(this);
    content.add(closeButton,  new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

    this.setTitle("About Communication Analyzer");
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    this.dispose();
  }

}
