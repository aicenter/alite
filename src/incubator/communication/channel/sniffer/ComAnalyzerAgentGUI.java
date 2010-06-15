package incubator.communication.channel.sniffer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * ComAnalyzerAgent GUI.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Dusan Pavlicek
 * @version 1.0
 */
public class ComAnalyzerAgentGUI
      extends JFrame implements ActionListener {

    private SnifferWrapper owner;
    private ComAnalyzerDataContainer dataContainer;
    private JPanel canvasPanel;

    /**
     * Last mouse X position, updated during mouse dragging.
     */
    private int mousePosX;

    /**
     * Last mouse Y position, updated during mouse dragging.
     */
    private int mousePosY;

    private float dragAnchorAbsX;
    private float dragAnchorAbsY;

    private float dragAnchorRelX;
    private float dragAnchorRelY;

    /**
     * Constructor
     * @param owner ComAnalyzerAgent
     */
    public ComAnalyzerAgentGUI(SnifferWrapper owner) {
//        super(owner);
        this.owner = owner;

        this.setTitle("Communication Analyzer");

        // !!! menu hacked!
//        JMenuBar menuBar = new JMenuBar();
//        this.setJMenuBar(menuBar);
//        JMenuItem menuItem;
//
//        JMenu menuFile = new JMenu("File");
//        menuBar.add(menuFile);
//        menuItem = new JMenuItem("Options");
//        menuItem.setActionCommand("MenuFileOptions");
//        menuItem.addActionListener(this);
//        menuFile.add(menuItem);
//        menuFile.addSeparator();
//        menuItem = new JMenuItem("Close");
//        menuItem.setActionCommand("MenuFileClose");
//        menuItem.addActionListener(this);
//        menuFile.add(menuItem);
//
//        JMenu menuHelp = new JMenu("Help");
//        menuBar.add(menuHelp);
//        menuItem = new JMenuItem("About");
//        menuItem.setActionCommand("MenuHelpAbout");
//        menuItem.addActionListener(this);
//        menuHelp.add(menuItem);

        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());

        canvasPanel = new CanvasPanel();
        content.add(canvasPanel, BorderLayout.CENTER);

        this.addWindowListener(new MyWindowAdapter(owner));

        this.setSize(512, 512);
        this.setVisible(false);
    }

    /**
     * Handle menu items.
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("MenuFileOptions")) {
            if (dataContainer != null) {
                new ComAnalyzerOptionsGUI(dataContainer);
            }
            return;
        }

        if (e.getActionCommand().equals("MenuFileClose")) {
            //owner.killSelf();
            owner.finish();
            return;
        }

        if (e.getActionCommand().equals("MenuHelpAbout")) {
            new SnifferAboutGUI();
            return;
        }
    }

    /**
     * Get canvas panel
     * @return JPanel
     */
    JPanel getCanvasPanel() {
        return canvasPanel;
    }

    /**
     * Sets the associated ComAnalyzerDataContainer.
     * 
     * @param dataContainer ComAnalyzerDataContainer
     */
    public void setDataContainer(ComAnalyzerDataContainer dataContainer) {
        this.dataContainer = dataContainer;
    }

    /**
     * Canvas panel used for rendering 2D graphics.
     */
    private class CanvasPanel
          extends JPanel implements MouseListener, MouseMotionListener {

        private CanvasPanel() {
            this.setBackground(new Color(0.8f, 0.8f, 0.8f));

            this.addComponentListener(
                  new ComponentAdapter() {
                /**
                 * Handles component resizing.
                 *
                 * @param e ComponentEvent
                 */
                public void componentResized(ComponentEvent e) {
                    if (dataContainer != null) {
                        dataContainer.recalcAgentPositions();
                    }
                }
            }
            );

            this.addMouseListener(this);
            this.addMouseMotionListener(this);
        }

        /**
         * Repaints the whole panel.
         * @param g Graphics
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (dataContainer != null) {
                dataContainer.draw(g);
            }
        }

        /**
         * Mouse pressed => get ready for possible dragging.
         * @param e MouseEvent
         */
        public void mousePressed(MouseEvent e) {
            dragAnchorAbsX = mousePosX = e.getX();
            dragAnchorAbsY = mousePosY = e.getY();

            float guiScale = dataContainer.getGUIScale();
            dragAnchorRelX = (mousePosX - this.getWidth() / 2 -
                              dataContainer.getGUIOffsetX() * guiScale) /
                  dataContainer.getGUIRadiusX();
            dragAnchorRelY = (mousePosY - this.getHeight() / 2 -
                              dataContainer.getGUIOffsetY() * guiScale) /
                  dataContainer.getGUIRadiusY();
        }

        /**
         * Handles mouse dragging.
         * @param e MouseEvent
         */
        public void mouseDragged(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int deltaX = x - mousePosX;
            int deltaY = y - mousePosY;
            mousePosX = x;
            mousePosY = y;

            float guiScale = dataContainer.getGUIScale();

            boolean buttonLeft = (e.getModifiers() & e.BUTTON1_MASK) ==
                  e.BUTTON1_MASK;
            boolean buttonRight = (e.getModifiers() & e.BUTTON3_MASK) ==
                  e.BUTTON3_MASK;

            if (buttonLeft) {
                int guiOffsetX = Math.round(dataContainer.getGUIOffsetX() +
                                            deltaX / guiScale);
                int guiOffsetY = Math.round(dataContainer.getGUIOffsetY() +
                                            deltaY / guiScale);
                dataContainer.setGUIOffset(guiOffsetX, guiOffsetY, true);
            }

            if (buttonRight) {
                float k = 1 + Math.abs(deltaY) / 20.0f;
                if (deltaY < 0) {
                    guiScale *= k;
                }
                else {
                    guiScale /= k;
                }
                dataContainer.setGUIScale(guiScale, false);

                int guiOffsetX = Math.round( (dragAnchorAbsX -
                                              this.getWidth() / 2 -
                                              dragAnchorRelX *
                                              dataContainer.getGUIRadiusX()) /
                                            guiScale);
                int guiOffsetY = Math.round( (dragAnchorAbsY -
                                              this.getHeight() / 2 -
                                              dragAnchorRelY *
                                              dataContainer.getGUIRadiusY()) /
                                            guiScale);
                dataContainer.setGUIOffset(guiOffsetX, guiOffsetY, true);
            }
        }

        public void mouseMoved(MouseEvent e) {
            if (dataContainer != null) {
                dataContainer.zoomAgent(e.getX(), e.getY());
            }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    }

    private class MyWindowAdapter
          extends WindowAdapter {

        private SnifferWrapper owner;

        MyWindowAdapter(SnifferWrapper owner) {
            this.owner = owner;
        }

        public void windowClosing(WindowEvent e) {
            //owner.killSelf();
            owner.finish();
        }

    }
}
