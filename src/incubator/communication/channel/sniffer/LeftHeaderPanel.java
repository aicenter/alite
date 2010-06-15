package incubator.communication.channel.sniffer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

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

public class LeftHeaderPanel extends JPanel {
  private final SnifferDataContainer dataContainer;

  public LeftHeaderPanel(SnifferDataContainer _dataContainer) {
    super();
    dataContainer = _dataContainer;

    setBackground(DrawingConstants.BACKGROUND_COLOR);
    repaintThis();
  }

  public void repaintThis() {
    synchronized (dataContainer) {
      setPreferredSize(new Dimension(DrawingConstants.LEFT_WIDTH,
                                     (dataContainer.filteredMsg.size()+1)*
                                     DrawingConstants.SPACE_BETWEEN_MESSAGES));
    }
    revalidate();
    repaint();
  }

  public void repaintLastMessage() {
    synchronized (dataContainer) {
      setPreferredSize(new Dimension(DrawingConstants.LEFT_WIDTH,
                                     (dataContainer.filteredMsg.size()+1)*
                                     DrawingConstants.SPACE_BETWEEN_MESSAGES));
    }
    revalidate();
    repaint();
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    Rectangle currentClip = g2.getClipBounds();
    g2.setColor(Color.BLACK);
    g2.setFont(DrawingConstants.BOX_FONT);
    int msgNumFrom =  (int)Math.round((double)currentClip.y/(double)DrawingConstants.SPACE_BETWEEN_MESSAGES) - 1;
    int msgNumTo = (int)Math.floor((double)currentClip.height/(double)DrawingConstants.SPACE_BETWEEN_MESSAGES) + 1 + msgNumFrom;
    if (msgNumFrom < 0) msgNumFrom = 0;
    int x,y;
    String text;
    FontMetrics metrics = g.getFontMetrics();
    int width;

    synchronized (dataContainer) {
      int msgCnt = dataContainer.filteredMsg.size();
      if (msgNumTo >= msgCnt) msgNumTo = msgCnt-1;

      for (int k=msgNumFrom;k<=msgNumTo;k++) {
        SnifferDataContainer.FilteredMessageCover mc = (SnifferDataContainer.FilteredMessageCover)dataContainer.filteredMsg.get(k);

        y = (k+1) * DrawingConstants.SPACE_BETWEEN_MESSAGES + DrawingConstants.MESSAGE_NUMBER_Y_OFFSET;
//        text = ""+(k+1);
        text = ""+mc.msgCover.msgId;
        width = metrics.stringWidth(text);
        g2.drawString(text,DrawingConstants.MESSAGE_NUMBER_X_POSITION-width,y);
      }
    }
  }
}
