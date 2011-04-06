package incubator.communication.channel.sniffer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Options dialog box.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Dusan Pavlicek
 * @version 1.0
 */
public class ComAnalyzerOptionsGUI extends JDialog implements ActionListener {

  private ComAnalyzerDataContainer dataContainer;

  private JTextField filter;
  private JTextField refreshTime;
  private JTextField fadingTime;
  private JTextField timeFrame;
  private JTextField msgCountSensitivity;
  private JTextField minLineWidth;
  private JTextField maxLineWidth;

  private JButton button;

  ComAnalyzerOptionsGUI(ComAnalyzerDataContainer dataContainer) {
    this.dataContainer = dataContainer;

    Container content = this.getContentPane();
    content.setLayout(new GridBagLayout());

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(10, 10, 10, 10);
    constraints.anchor = GridBagConstraints.WEST;

    constraints.gridy = 0;
    content.add(new JLabel("Filter [regular expression]:"), constraints);
    content.add(filter = new JTextField(20), constraints);
    filter.setText(dataContainer.getRegularExpression());

    constraints.gridy = 1;
    content.add(new JLabel("Refresh Time [ms]:"), constraints);
    content.add(refreshTime = new JTextField(5), constraints);
    refreshTime.setText(Integer.toString(dataContainer.paramRefreshTime));

    constraints.gridy = 2;
    content.add(new JLabel("Message Fading Time [ms]:"), constraints);
    content.add(fadingTime = new JTextField(5), constraints);
    fadingTime.setText(Integer.toString(dataContainer.paramFadingTime));

    constraints.gridy = 3;
    content.add(new JLabel("Message Time Frame [ms]:"), constraints);
    content.add(timeFrame = new JTextField(8), constraints);
    timeFrame.setText(Integer.toString(dataContainer.paramTimeFrame));

    constraints.gridy = 4;
    content.add(new JLabel("Message Count Sensitivity [%]:"), constraints);
    content.add(msgCountSensitivity = new JTextField(8), constraints);
    msgCountSensitivity.setText(Integer.toString(Math.round(dataContainer.paramMsgCountSensitivity * 100)));

    constraints.gridy = 5;
    content.add(new JLabel("Message Line Min Width [pxs]:"), constraints);
    content.add(minLineWidth = new JTextField(8), constraints);
    minLineWidth.setText(Float.toString(dataContainer.paramMinLineWidth));

    constraints.gridy = 6;
    content.add(new JLabel("Message Line Max Width [pxs]:"), constraints);
    content.add(maxLineWidth = new JTextField(8), constraints);
    maxLineWidth.setText(Float.toString(dataContainer.paramMaxLineWidth));

    constraints.gridy = 7;
    constraints.gridx = 1;
    constraints.anchor = GridBagConstraints.EAST;
    button = new JButton("Apply");
    button.addActionListener(this);
    content.add(button, constraints);

    this.setTitle("Options");
    this.setModal(true);
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Handles ActionEvent performed by the Apply button.
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    if (!filter.getText().equals(dataContainer.getRegularExpression())) {
      try {
        dataContainer.setRegularExpression(filter.getText());
      }
      catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, "Invalid filter format!", "Error",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }
    }

    try {
      dataContainer.paramRefreshTime = Integer.parseInt(refreshTime.getText());
      dataContainer.paramFadingTime = Integer.parseInt(fadingTime.getText());
      dataContainer.paramTimeFrame = Integer.parseInt(timeFrame.getText());
      dataContainer.paramMsgCountSensitivity = Integer.parseInt(msgCountSensitivity.getText()) / 100.0f;
      dataContainer.paramMinLineWidth = Float.parseFloat(minLineWidth.getText());
      dataContainer.paramMaxLineWidth = Float.parseFloat(maxLineWidth.getText());
    }
    catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Invalid numeric format!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    dataContainer.saveStore();
    this.dispose();
  }

}
