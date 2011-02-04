package incubator.communication.channel.sniffer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import incubator.communication.channel.sniffer.SnifferDataContainer.AgentRecord;
import incubator.communication.channel.sniffer.SnifferDataContainer.ContainerRecord;

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

public class DetailHeaderPanel extends JPanel {
  private final SnifferDataContainer dataContainer;
  private SnifferDataContainer.Record selectedItem = null;
  private boolean selectedContainer = false;

  private JPopupMenu popupMenu = new JPopupMenu();
  private JMenuItem dontSniffMenuItem = new JMenuItem();
  private JMenuItem moveLeft = new JMenuItem();
  private JMenuItem moveRight = new JMenuItem();

  public DetailHeaderPanel(SnifferDataContainer _dataContainer) {
    super();
    dataContainer = _dataContainer;

    setBackground(DrawingConstants.BACKGROUND_COLOR);
    repaintThis();

    dontSniffMenuItem.setText("Remove from filter");
    dontSniffMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        if (!selectedContainer && selectedItem != null && selectedItem.isSniffed())
          dataContainer.dontSniff(selectedItem,true);
      }
    }
        );
    moveLeft.setText("Move Left");
    moveLeft.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        if (selectedItem != null)
         if (!selectedContainer)
          dataContainer.moveActorLeft(selectedItem);
         else
         dataContainer.moveContainerLeft(selectedItem);
      }
    }
        );
    moveRight.setText("Move Right");
    moveRight.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        if (selectedItem != null)
         if (!selectedContainer)
          dataContainer.moveActorRight(selectedItem);
         else
          dataContainer.moveContainerRight(selectedItem);
      }
    }
        );

    popupMenu.add(dontSniffMenuItem);
    popupMenu.addSeparator();
    popupMenu.add(moveLeft);
    popupMenu.add(moveRight);

    addMouseListener(new MyMouseListener(this));
  }

  private void drawActorBox(Graphics2D g, int position, Color color, String text, boolean selected) {
    int box_X_center = DrawingConstants.FIRST_ACTOR_X_POSITION + position*DrawingConstants.SPACE_BETWEEN_ACTORS;
    if (selected)
      g.setStroke(DrawingConstants.BOX_ACTOR_SELECTED_STROKE);
    else
      g.setStroke(DrawingConstants.BOX_ACTOR_STROKE);
    g.setColor(color);
    g.fillRoundRect(box_X_center-DrawingConstants.BOX_WIDTH/2,DrawingConstants.BOX_Y_POSITION,
               DrawingConstants.BOX_WIDTH,DrawingConstants.BOX_HEIGHT,
               DrawingConstants.BOX_ROUNDING, DrawingConstants.BOX_ROUNDING);
    g.setColor(DrawingConstants.BOX_BORDER_COLOR);
    g.drawRoundRect(box_X_center-DrawingConstants.BOX_WIDTH/2,DrawingConstants.BOX_Y_POSITION,
               DrawingConstants.BOX_WIDTH,DrawingConstants.BOX_HEIGHT,
               DrawingConstants.BOX_ROUNDING, DrawingConstants.BOX_ROUNDING);
    g.setStroke(new BasicStroke(DrawingConstants.ACTOR_LINE_WIDTH));
    g.setColor(DrawingConstants.ACTOR_LINE_COLOR);
    g.drawLine(box_X_center,DrawingConstants.BOX_Y_POSITION+DrawingConstants.BOX_HEIGHT,
               box_X_center,DrawingConstants.HEADER_HEIGHT);

    FontMetrics metrics = g.getFontMetrics();
    int width = metrics.stringWidth(text);
    int height = metrics.getHeight()-4;
    if (width <= DrawingConstants.BOX_WIDTH) {
      g.drawString(text, box_X_center - width / 2,
                   DrawingConstants.BOX_BOTTOM_TEXT_POSITION);
    } else {
      int numLines = (int)Math.ceil((double)width/(DrawingConstants.BOX_WIDTH-2));
      int startY = DrawingConstants.BOX_BOTTOM_TEXT_POSITION - height*(int)Math.floor((double)numLines/2);
      int partLen = (int)Math.floor((double)text.length()/numLines);
      for (int i=0;i<numLines;i++) {
        String part;
        if (i+1 == numLines) part = text.substring(i*partLen);
        else part = text.substring(i*partLen,((i+1)*partLen));
        int partWidth = metrics.stringWidth(part);
        g.drawString(part,box_X_center-partWidth/2,startY);
        startY += height;
      }
    }
  }

  private void drawContainerBox(Graphics2D g, int position, int length, Color color, String text, boolean selected) {
    int x1 = DrawingConstants.FIRST_ACTOR_X_POSITION + position*DrawingConstants.SPACE_BETWEEN_ACTORS - DrawingConstants.BOX_WIDTH/2 - DrawingConstants.BOX_CONTAINER_OFFSET;
    int width = (length-1) * DrawingConstants.SPACE_BETWEEN_ACTORS + 2*DrawingConstants.BOX_CONTAINER_OFFSET + DrawingConstants.BOX_WIDTH;
    if (selected)
      g.setStroke(DrawingConstants.BOX_CONTAINER_SELECTED_STROKE);
    else
      g.setStroke(DrawingConstants.BOX_CONTAINER_STROKE);
    g.setColor(color);
    g.fillRect(x1,DrawingConstants.BOX_CONTAINER_Y1,width,DrawingConstants.BOX_CONTAINER_HEIGHT);
    g.setColor(DrawingConstants.BOX_BORDER_COLOR);
    g.drawRect(x1,DrawingConstants.BOX_CONTAINER_Y1,width,DrawingConstants.BOX_CONTAINER_HEIGHT);

    FontMetrics metrics = g.getFontMetrics();
    int textWidth = metrics.stringWidth(text);
    g.drawString(text,x1+width/2-textWidth/2,DrawingConstants.BOX_CONTAINER_BOTTOM_TEXT_POSITION);
  }

  public void repaintThis() {
    synchronized (dataContainer) {
      setPreferredSize(new Dimension(DrawingConstants.SPACE_BETWEEN_ACTORS *
                                     (dataContainer.filteredObjects.size()),
                                     DrawingConstants.HEADER_HEIGHT));
    }
    revalidate();
    repaint();
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    int i = -1;
    g2.setStroke(new BasicStroke(DrawingConstants.ACTOR_LINE_WIDTH));
    g2.setFont(DrawingConstants.BOX_FONT);

    synchronized (dataContainer) {
      for (Iterator iter = dataContainer.filteredObjects.iterator(); iter.hasNext(); ) {
        SnifferDataContainer.Record item = (SnifferDataContainer.Record)iter.next();
        i++;
        if (item.isFirstFromThisContainer()) {
          //draw container windows
          ContainerRecord container = item.getContainer();
          drawContainerBox(g2,i,item.countFromThisContainer,
                          container.isLogged()?DrawingConstants.BOX_LOGGED_CONTAINER_COLOR:DrawingConstants.BOX_UNLOGGED_CONTAINER_COLOR,
                          container.name,
                          (selectedContainer && (selectedItem!=null) && (container == selectedItem.getContainer())));
        }
        if (item instanceof ContainerRecord) {
          //draw "others" actor
          ContainerRecord container = (ContainerRecord) item;
          drawActorBox(g2,i,DrawingConstants.BOX_OTHER_ACTOR,SnifferTreeModel.OTHERS,
                       ((!selectedContainer) && (selectedItem == container)));
        } else if (item instanceof AgentRecord) {
          //draw "regular" actor
          AgentRecord agent = (AgentRecord) item;
          drawActorBox(g2,i,
                  agent.isAgent?DrawingConstants.BOX_AGENT_ACTOR:DrawingConstants.BOX_SERVICE_ACTOR,
                  agent.name,
                  ((!selectedContainer) && (selectedItem == agent)));
        }
      }
    }
  }

  private void clearSelection() {
    if (selectedItem != null) {
      selectedItem = null;
      selectedContainer = false;
      repaint();
    }
  }

  private void changeSelection(SnifferDataContainer.Record _record,boolean _isContainer) {
    if (selectedItem != _record || selectedContainer != _isContainer) {
      selectedItem = _record;
      selectedContainer = _isContainer;
      repaint();
    }
  }

  private void findItem(int x,int y) {
    synchronized (dataContainer) {
      int position = (x-(DrawingConstants.FIRST_ACTOR_X_POSITION-DrawingConstants.SPACE_BETWEEN_ACTORS/2));
      if (position < 0) {
        clearSelection();
        return;
      }
      position = (int)Math.floor((double)position/DrawingConstants.SPACE_BETWEEN_ACTORS);
      if (position >= dataContainer.filteredObjects.size()) {
        clearSelection();
        return;
      }
      if (y > DrawingConstants.BOX_Y_POSITION && y < (DrawingConstants.BOX_Y_POSITION + DrawingConstants.BOX_HEIGHT)) {
        // actor selected
        changeSelection((SnifferDataContainer.Record)dataContainer.filteredObjects.get(position),false);
      } else if (y > DrawingConstants.BOX_CONTAINER_Y1 && y < DrawingConstants.BOX_Y_POSITION) {
        // container selected
        changeSelection((SnifferDataContainer.Record)dataContainer.filteredObjects.get(position),true);
      }
    }
  }

  private class MyMouseListener implements MouseListener {
    private final DetailHeaderPanel owner;

    public MyMouseListener(DetailHeaderPanel _owner) {
      owner = _owner;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
      if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
        if (selectedItem != null) {
          if (selectedContainer) {
            dontSniffMenuItem.setEnabled(false);
            if (selectedItem.isFisrtContainer())
              moveLeft.setEnabled(false);
            else
              moveLeft.setEnabled(true);
            if (selectedItem.isLastContainer())
              moveRight.setEnabled(false);
            else
              moveRight.setEnabled(true);
          } else {
            dontSniffMenuItem.setEnabled(true);
            if (selectedItem.isFirstFromThisContainer())
              moveLeft.setEnabled(false);
            else
              moveLeft.setEnabled(true);
            if (selectedItem.isLast)
              moveRight.setEnabled(false);
            else
              moveRight.setEnabled(true);
          }
          popupMenu.show(mouseEvent.getComponent(),mouseEvent.getX(),mouseEvent.getY());
        }
      }
    }

    public void mousePressed(MouseEvent mouseEvent) {
      owner.findItem(mouseEvent.getX(),mouseEvent.getY());
    }

    public void mouseReleased(MouseEvent mouseEvent) {
    }

    public void mouseEntered(MouseEvent mouseEvent) {
    }

    public void mouseExited(MouseEvent mouseEvent) {
    }
  }
}
