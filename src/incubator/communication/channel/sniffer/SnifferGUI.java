package incubator.communication.channel.sniffer;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.TreeSelectionModel;

import incubator.communication.channel.sniffer.compatibility.Store;

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

public class SnifferGUI extends JFrame {
  final SnifferWrapper owner;
  private static final String GUI_X= "gui/x";
  private static final String GUI_Y= "gui/y";
  private static final String GUI_W= "gui/w";
  private static final String GUI_H= "gui/h";
  private static final String DIVIDER= "gui/divider";

  private final SnifferDataContainer dataContainer;
  private SnifferAboutGUI aboutWindow;

  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenu1 = new JMenu();
  JMenuItem closeSniffer = new JMenuItem();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JSplitPane jSplitPanel = new JSplitPane();
  JPanel previewPanel = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();
  JScrollPane detailedScrollPanel = new JScrollPane();
  final DetailMainPanel detailMainPanel;
  final DetailHeaderPanel detailHeaderPanel;
  final LeftHeaderPanel leftHeaderPanel;
  JPanel cornerPanel1 = new JPanel();
  JPanel cornerPanel2 = new JPanel();
  Border border1;
  JMenuItem showContainerGUI_MenuItem = new JMenuItem();
  JScrollPane previewScrollPanel = new JScrollPane();
  JTree previewTree;
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem1 = new JMenuItem();
  JPanel statusPanel = new JPanel();
  GridBagLayout gridBagLayout3 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel historyUsed = new JLabel();
  JLabel maxHistory = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel parsedMsg = new JLabel();
  JMenuItem option_MenuItem = new JMenuItem();

  public SnifferGUI(SnifferWrapper _owner,SnifferDataContainer _dataContainer) {
    owner = _owner;
    dataContainer = _dataContainer;
    detailMainPanel = new DetailMainPanel(dataContainer);
    detailHeaderPanel = new DetailHeaderPanel(dataContainer);
    leftHeaderPanel = new LeftHeaderPanel(dataContainer);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    dataContainer.setHeaderPanel(detailHeaderPanel);
    dataContainer.setMainPanel(detailMainPanel);
    dataContainer.setLeftHeaderPanel(leftHeaderPanel);
    dataContainer.setContainerTree(previewTree);
    loadStore();
    setVisible(false);
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEmptyBorder();
    // !!!! menu hacked!
    //this.setJMenuBar(jMenuBar1);
    this.setState(Frame.NORMAL);
    this.setTitle("Sniffer");
    this.addWindowListener(new SnifferGUI_this_windowAdapter(this));
    this.getContentPane().setLayout(gridBagLayout1);

    jSplitPanel.setMinimumSize(new Dimension(250, 250));
    jSplitPanel.setDividerSize(3);
    jSplitPanel.setBorder(null);
    previewPanel.setLayout(gridBagLayout2);
    cornerPanel1.setBackground(DrawingConstants.BACKGROUND_COLOR);
    cornerPanel2.setBackground(DrawingConstants.BACKGROUND_COLOR);

//    try {
//      UIManager.setLookAndFeel(
//          "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//    }
//    catch (UnsupportedLookAndFeelException ex) {
//    }
//    catch (IllegalAccessException ex) {
//    }
//    catch (InstantiationException ex) {
//    }
//    catch (ClassNotFoundException ex) {
//    }
    previewTree = new JTree();
    previewTree.setModel(dataContainer.getTreeModel());
    previewTree.setCellRenderer(dataContainer.getTreeCellRenderer());
    previewTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    previewTree.addMouseListener(dataContainer.getMouseListener(previewTree));
//    try {
//      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//    }
//    catch (UnsupportedLookAndFeelException ex1) {
//    }
//    catch (IllegalAccessException ex1) {
//    }
//    catch (InstantiationException ex1) {
//    }
//    catch (ClassNotFoundException ex1) {
//    }

    jMenu1.setText("File");
    closeSniffer.setText("Close Sniffer");
    closeSniffer.addActionListener(new SnifferGUI_closeSniffer_actionAdapter(this));
    showContainerGUI_MenuItem.setText("Show agent container GUI");
    showContainerGUI_MenuItem.addActionListener(new SnifferGUI_showContainerGUI_MenuItem_actionAdapter(this));
    jMenu2.setText("About");
    jMenuItem1.setText("About Sniffer");
    jMenuItem1.addActionListener(new SnifferGUI_jMenuItem1_actionAdapter(this));
    statusPanel.setLayout(gridBagLayout3);
    jLabel1.setText("Message history buffer:");
    historyUsed.setText("0");
    maxHistory.setText("/0");
    jLabel3.setText("Total sniffed messages:");
    parsedMsg.setVerifyInputWhenFocusTarget(true);
    parsedMsg.setText("0");
    option_MenuItem.setText("Options");
    option_MenuItem.addActionListener(new SnifferGUI_option_MenuItem_actionAdapter(this));
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    jMenu1.add(showContainerGUI_MenuItem);
    jMenu1.addSeparator();
    jMenu1.add(option_MenuItem);
    jMenu1.addSeparator();
    jMenu1.add(closeSniffer);

    this.getContentPane().add(jSplitPanel,   new GridBagConstraints(0, 1, 1, 1, 0.1, 0.1
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jSplitPanel.add(previewPanel, JSplitPane.LEFT);
    previewPanel.add(previewScrollPanel,  new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    jSplitPanel.add(detailedScrollPanel, JSplitPane.RIGHT);
    this.getContentPane().add(statusPanel,    new GridBagConstraints(0, 2, 1, 1, 0.1, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    detailedScrollPanel.getViewport().add(detailMainPanel, null);
    previewScrollPanel.getViewport().add(previewTree, null);
    jMenu2.add(jMenuItem1);
    statusPanel.add(jLabel1,         new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(historyUsed,         new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    statusPanel.add(maxHistory,      new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 15), 0, 0));
    statusPanel.add(jLabel3,     new GridBagConstraints(7, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
    statusPanel.add(parsedMsg,   new GridBagConstraints(8, 0, 1, 1, 0.1, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    detailedScrollPanel.setColumnHeaderView(detailHeaderPanel);
    detailedScrollPanel.setRowHeaderView(leftHeaderPanel);
    detailedScrollPanel.setCorner(JScrollPane.UPPER_RIGHT_CORNER,cornerPanel1);
    detailedScrollPanel.setCorner(JScrollPane.UPPER_LEFT_CORNER,cornerPanel2);
  }

  void closeSniffer_actionPerformed(ActionEvent e) {
    owner.close_gui();
  }

  private void loadStore() {
      Store store = new Store("ATGSniffer",null);
        int x=0,y=0;
        x = store.getInt(GUI_X,x);
        y = store.getInt(GUI_Y,y);
        setLocation(x, y);

        int w=800,h=600;
        w = store.getInt(GUI_W, w);
        h= store.getInt(GUI_H, h);
        setSize(w, h);

        int div=200;
        div = store.getInt(DIVIDER, div);
        jSplitPanel.setDividerLocation(div);
  }
  
  private void saveStore() {
    
      Store store = new Store("ATGSniffer",null);
      if (detailMainPanel.aclMessageGUI != null)
        detailMainPanel.aclMessageGUI.showGUI(false);
      java.awt.Point p= getLocation();
      store.putInt(GUI_X, p.x);
      store.putInt(GUI_Y, p.y);

      Dimension d= getSize();
      store.putInt(GUI_W, d.width);
      store.putInt(GUI_H, d.height);

      store.putInt(DIVIDER, jSplitPanel.getDividerLocation());
  }
  
  public void setVisible(boolean b)
  {
//    Store store = owner.getContainer().getAgentStore(owner.getName());
//    Store store = new Store("ATGSniffer",null);
    if (b)
    {
      loadStore();
    } else {
      saveStore();
    }
    super.setVisible(b);
  }

  void showContainerGUI_MenuItem_actionPerformed(ActionEvent e) {
    owner.showContainerGUI();
  }

  void aboutItem_actionPerformed(ActionEvent e) {
    if (aboutWindow == null) aboutWindow = new SnifferAboutGUI();
    aboutWindow.setVisible(true);
  }

  void this_windowClosed(WindowEvent e) {
    owner.close_gui();
  }

  void this_windowClosing(WindowEvent e) {
    owner.close_gui();
  }

  @SuppressWarnings(value={"deprication"}) 
  void option_MenuItem_actionPerformed(ActionEvent e) {
    SnifferOptionGUI options = new SnifferOptionGUI(this);
    options.show();
  }


}

class SnifferGUI_closeSniffer_actionAdapter implements java.awt.event.ActionListener {
  SnifferGUI adaptee;

  SnifferGUI_closeSniffer_actionAdapter(SnifferGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.closeSniffer_actionPerformed(e);
  }
}

class SnifferGUI_showContainerGUI_MenuItem_actionAdapter implements java.awt.event.ActionListener {
  SnifferGUI adaptee;

  SnifferGUI_showContainerGUI_MenuItem_actionAdapter(SnifferGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.showContainerGUI_MenuItem_actionPerformed(e);
  }
}

class SnifferGUI_jMenuItem1_actionAdapter implements java.awt.event.ActionListener {
  SnifferGUI adaptee;

  SnifferGUI_jMenuItem1_actionAdapter(SnifferGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.aboutItem_actionPerformed(e);
  }
}

class SnifferGUI_this_windowAdapter extends java.awt.event.WindowAdapter {
  SnifferGUI adaptee;

  SnifferGUI_this_windowAdapter(SnifferGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void windowClosed(WindowEvent e) {
    adaptee.this_windowClosed(e);
  }
  public void windowClosing(WindowEvent e) {
    adaptee.this_windowClosing(e);
  }
}

class SnifferGUI_option_MenuItem_actionAdapter implements java.awt.event.ActionListener {
  SnifferGUI adaptee;

  SnifferGUI_option_MenuItem_actionAdapter(SnifferGUI adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.option_MenuItem_actionPerformed(e);
  }
}
