package incubator.communication.channel.sniffer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

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

public class DetailMainPanel extends JPanel {
  private final SnifferDataContainer dataContainer;
  ACLMessageGUI aclMessageGUI = null;
  private long selectedMsg = -1;

  public DetailMainPanel(SnifferDataContainer _dataContainer) {
    super();
    dataContainer = _dataContainer;
    setBackground(DrawingConstants.BACKGROUND_COLOR);
    addMouseListener(new myMouseListener(this));
    repaintThis();
  }

  public void repaintThis() {
    synchronized (dataContainer) {
      setPreferredSize(new Dimension(DrawingConstants.SPACE_BETWEEN_ACTORS *
                                     (dataContainer.filteredObjects.size()),
                                     DrawingConstants.SPACE_BETWEEN_MESSAGES *
                                     (dataContainer.filteredMsg.size()+1)));
    }
    revalidate();
    repaint();
  }

  public void repaintLastMessage() {
    synchronized (dataContainer) {
      setPreferredSize(new Dimension(DrawingConstants.SPACE_BETWEEN_ACTORS *
                                     (dataContainer.filteredObjects.size()),
                                     DrawingConstants.SPACE_BETWEEN_MESSAGES *
                                     (dataContainer.filteredMsg.size()+1)));
    }
    revalidate();
    repaint();
  }

  private void drawMessage(Graphics2D g, int x1, int y1, int x2, int y2) {
    g.drawLine(x1,y1,x2,y2);
       // draw arrow at the end of the line
    g.setStroke(DrawingConstants.REGULAR_MESSAGE_STROKE);
    int direction = ((x2<x1)?1:-1) * DrawingConstants.ARROW_LENGTH;
    g.drawLine(x2,y2,x2+direction,y2+DrawingConstants.ARROW_WIDTH);
    g.drawLine(x2,y2,x2+direction,y2-DrawingConstants.ARROW_WIDTH);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    Rectangle currentClip = g2.getClipBounds();
    g2.setStroke(new BasicStroke(DrawingConstants.ACTOR_LINE_WIDTH));
    g2.setColor(DrawingConstants.ACTOR_LINE_COLOR);
    int msgNumFrom =  (int)Math.round((double)currentClip.y/(double)DrawingConstants.SPACE_BETWEEN_MESSAGES) - 1;
    int msgNumTo = (int)Math.floor((double)currentClip.height/(double)DrawingConstants.SPACE_BETWEEN_MESSAGES) + 1 + msgNumFrom;
    if (msgNumFrom < 0) msgNumFrom = 0;
    int x,y;

    synchronized (dataContainer) {
      int actorCnt = dataContainer.filteredObjects.size();
      int msgCnt = dataContainer.filteredMsg.size();
      y = (msgCnt+1) * DrawingConstants.SPACE_BETWEEN_MESSAGES;
      for (int k=0;k<actorCnt;k++) {
        x = DrawingConstants.FIRST_ACTOR_X_POSITION + k*DrawingConstants.SPACE_BETWEEN_ACTORS;
        g2.drawLine(x,0,x,y);
      }

      if (msgNumTo >= msgCnt) msgNumTo = msgCnt-1;
      for (int k=msgNumFrom;k<=msgNumTo;k++) {
        SnifferDataContainer.FilteredMessageCover mc = (SnifferDataContainer.FilteredMessageCover)dataContainer.filteredMsg.get(k);
        g2.setColor(mc.msgCover.messageColor);
        if (mc.msgCover.undeliverable)
          g2.setStroke((mc.msgCover.msgId != selectedMsg)?
                       DrawingConstants.INACCESSIBLE_MESSAGE_STROKE
                       :
                       DrawingConstants.SELECTED_INACCESSIBLE_MESSAGE_STROKE);
        else
          g2.setStroke((mc.msgCover.msgId != selectedMsg)?
                       DrawingConstants.REGULAR_MESSAGE_STROKE
                       :
                       DrawingConstants.SELECTED_REGULAR_MESSAGE_STROKE);

        y = (k+1) * DrawingConstants.SPACE_BETWEEN_MESSAGES;
        drawMessage(g2,
                    DrawingConstants.FIRST_ACTOR_X_POSITION + mc.fromPosition*DrawingConstants.SPACE_BETWEEN_ACTORS,
                    y,
                    DrawingConstants.FIRST_ACTOR_X_POSITION + mc.toPosition*DrawingConstants.SPACE_BETWEEN_ACTORS,
                    y);
      }
    }
  }

  private void parseMouseClick(int x,int y) {
    synchronized (dataContainer) {
      int msgCnt = dataContainer.filteredMsg.size();
      int _selectedMsg = (int)Math.round((double)y/(double)DrawingConstants.SPACE_BETWEEN_MESSAGES) - 1;
      if (_selectedMsg < 0 || _selectedMsg >= msgCnt) {
        selectedMsg = -1;
        repaint();
        showACLMessage(null);
        return;
      }
      SnifferDataContainer.FilteredMessageCover mc = (SnifferDataContainer.FilteredMessageCover)dataContainer.filteredMsg.get(_selectedMsg);
      int left,right;
      if (mc.fromPosition < mc.toPosition) {
        left = mc.fromPosition * DrawingConstants.SPACE_BETWEEN_ACTORS + DrawingConstants.FIRST_ACTOR_X_POSITION;
        right = mc.toPosition * DrawingConstants.SPACE_BETWEEN_ACTORS + DrawingConstants.FIRST_ACTOR_X_POSITION;
      } else {
        right = mc.fromPosition * DrawingConstants.SPACE_BETWEEN_ACTORS + DrawingConstants.FIRST_ACTOR_X_POSITION;
        left = mc.toPosition * DrawingConstants.SPACE_BETWEEN_ACTORS + DrawingConstants.FIRST_ACTOR_X_POSITION;
      }
      if (x < left || x > right) {
        selectedMsg = -1;
        repaint();
        showACLMessage(null);
        return;
      }
      selectedMsg = mc.msgCover.msgId;
      repaint();
      showACLMessage(mc.msgCover);
    }
  }

  public void setSelectedMsg(final long selectedMsgId) {
    this.selectedMsg = selectedMsgId;
    repaint();
  }

  @SuppressWarnings(value={"unchecked"}) 
  public void showACLMessage(final SnifferDataContainer.MessageCover mc) {
    if (mc == null && aclMessageGUI != null) {
      aclMessageGUI.showGUI(false);
      return;
    }
    if (mc == null) return;
    if (aclMessageGUI == null) aclMessageGUI = new ACLMessageGUI(this);
    /** @todo vyhledat celou konverzaci, a posilat array **/
    ArrayList mcs = new ArrayList();
    mcs.add(mc);
    int cur = 0;
    String convId = ""+mc.msg.getId();
    if (convId != null) {
      ArrayList next = new ArrayList();
      for (Iterator iter = dataContainer.filteredMsg.iterator(); iter.hasNext(); ) {
        SnifferDataContainer.FilteredMessageCover item = (SnifferDataContainer.FilteredMessageCover)iter.next();
        if (item.msgCover != mc &&
          convId.equals(""+item.msgCover.msg.getId())) {
           next.add(item.msgCover);
        }
      }
      // sort conversation
      // find all before the message
//      String inReplyTo = mc.msg.getInReplyTo();
//      boolean stop = false;
//      boolean found;
//      int i;
//      while (!stop && (inReplyTo != null)) {
//        found = false;
//        for (i = 0; i < next.size(); i++) {
//          if (inReplyTo.equals( ((SnifferDataContainer.MessageCover) next.get(i)).msg.getReplyWith())) {
//            found = true;
//            break;
//          }
//        }
//        if (found) {
//          SnifferDataContainer.MessageCover p = (SnifferDataContainer.MessageCover) next.
//              remove(i);
//          mcs.add(0, p);
//          cur++;
//          inReplyTo = p.msg.getInReplyTo();
//        }
//        else {
//          stop = true;
//        }
//      }
//      // all conversation after selected message
//
//      String replyWith = mc.msg.getReplyWith();
//      if (replyWith != null) {
//        stop = false;
//        while (!stop) {
//          found = false;
//          for (i = 0; i < next.size() ; i++) {
//            if (replyWith.equals(((SnifferDataContainer.MessageCover) next.get(i)).msg.getInReplyTo())) {
//              found = true;
//              break;
//            }
//          }
//          if (found) {
//            SnifferDataContainer.MessageCover p = (SnifferDataContainer.MessageCover) next.
//                remove(i);
//            mcs.add(p);
//            replyWith = p.msg.getReplyWith();
//          } else {
//            stop = true;
//          }
//        }
//      }

    }
    aclMessageGUI.setACLMessage((SnifferDataContainer.MessageCover[])mcs.toArray(new SnifferDataContainer.MessageCover[mcs.size()]),cur);
    aclMessageGUI.showGUI(true);
  }

  public void clearSelection() {
    selectedMsg = -1;
    repaint();
  }

  public void hideACLMessageWindow() {
    if (aclMessageGUI != null) aclMessageGUI.showGUI(false);
  }

  class myMouseListener implements MouseListener {
    private final DetailMainPanel owner;

    myMouseListener(DetailMainPanel _owner) {
      owner = _owner;
    }

    public void mouseClicked(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() >= 2) {
        owner.parseMouseClick(e.getX(),e.getY());
      }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
  }
}

