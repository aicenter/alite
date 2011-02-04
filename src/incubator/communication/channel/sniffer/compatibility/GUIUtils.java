package incubator.communication.channel.sniffer.compatibility;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
//import aglobe.container.task.*;

/**
 *
 * <p>Title: A-Globe</p>
 * <p>Description: Utils for positioning Components on screen.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 */

public class GUIUtils {
  /**
   * GUIUtils cannot be created
   */
  private GUIUtils() {
  }

  /**
   * Centers the given component on screen. It must be packed, but might be either
   * visible or invisible. This method might have strange results for components
   * that are part of another components.
   * @param frame Component - the component
   */
  public static void centerOnScreen(Component frame) {
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation( (screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);
  }

  /**
   * Positions the given component on screen. It must be packed, but might be either
   * visible or invisible. This method might have strange results for components
   * that are part of another components.
   * @param frame Component - the component
   * @param hpos Horizontal - The horizontal position, <code>null</code> means position is untuched.
   * @param vpos Vertical - The vertical position, <code>null</code> means position is untuched.
   */
  public static void positionOnScreen(Component frame, Horizontal hpos,
                                      Vertical vpos) {
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }

    int x;
    int y;

    if (hpos == hLEFT)
      x = 0;
    else if (hpos == hCENTER)
      x = (screenSize.width - frameSize.width) / 2;
    else if (hpos == hRIGHT)
      x = screenSize.width - frameSize.width;
    else
      x = frame.getX();

    if (vpos == vTOP)
      y = 0;
    else if (vpos == vCENTER)
      y = (screenSize.height - frameSize.height) / 2;
    else if (vpos == vBOTTOM)
      y = screenSize.height - frameSize.height;
    else
      y = frame.getY();

    frame.setLocation(x, y);
  }

  /**
   * Docks one component to another. The side, which the components will touch
   * is specified by the <code>pos</code> parameter with respect to <code>
   * the_dock</code> component.
   * @param the_dock Component
   * @param dockable Component
   * @param pos Position
   */
  public static void dockTo(Component the_dock, Component dockable,
                            Position pos) {
    if (pos == onTOP || pos == onLEFT) {
      Dimension dsize = dockable.getSize();
      Point loc = the_dock.getLocation();

      if (pos == onTOP)
        dockable.setLocation(loc.x, loc.y - dsize.height);
      else // LEFT
        dockable.setLocation(loc.x - dsize.width, loc.y);
    }
    else { // BOTTOM or RIGHT
      Dimension ssize = the_dock.getSize();
      Point sloc = the_dock.getLocation();

      if (pos == onBOTTOM)
        dockable.setLocation(sloc.x, sloc.y + ssize.height);
      else // RIGHT
        dockable.setLocation(sloc.x + ssize.width, sloc.y);
    }
  }

  /**
   * Horizontal Left
   */
  public static final Horizontal hLEFT = new Horizontal();

  /**
   * Horizontal Center
   */
  public static final Horizontal hCENTER = new Horizontal();

  /**
   * Horizontal Right
   */
  public static final Horizontal hRIGHT = new Horizontal();

  /**
   * Horizontal None
   */
  public static final Horizontal hNONE = new Horizontal();

  /**
   *
   * <p>Title: A-Globe</p>
   * <p>Description: Horizontal constant class</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: Gerstner Laboratory</p>
   *
   * @author David Sislak
   * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
   */
  public static final class Horizontal {
    private Horizontal() {}
  }

  /**
   * Vertical Top
   */
  public static final Vertical vTOP = new Vertical();

  /**
   * Vertical Center
   */
  public static final Vertical vCENTER = new Vertical();

  /**
   * Vertical Bottom
   */
  public static final Vertical vBOTTOM = new Vertical();

  /**
   * Vertical None
   */
  public static final Vertical vNONE = new Vertical();

  /**
   *
   * <p>Title: A-Globe</p>
   * <p>Description: Vertical constant class</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: Gerstner Laboratory</p>
   *
   * @author David Sislak
   * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
   */
  public static final class Vertical {
    private Vertical() {}
  }

  /**
   * Position Top
   */
  public static final Position onTOP = new Position();

  /**
   * Position Left
   */
  public static final Position onLEFT = new Position();

  /**
   * Position Right
   */
  public static final Position onRIGHT = new Position();

  /**
   * Position Bottom
   */
  public static final Position onBOTTOM = new Position();

  /**
   *
   * <p>Title: A-Globe</p>
   * <p>Description: Position constant class</p>
   * <p>Copyright: Copyright (c) 2004</p>
   * <p>Company: Gerstner Laboratory</p>
   *
   * @author David Sislak
   * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
   */
  public static final class Position {
    private Position() {}
  }
}
