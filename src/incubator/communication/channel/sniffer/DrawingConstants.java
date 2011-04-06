package incubator.communication.channel.sniffer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

import incubator.communication.channel.sniffer.compatibility.MessageConstants;
import cz.agents.alite.communication.protocol.Performative;
//import aglobe.ontology.MessageConstants;

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

public interface DrawingConstants {
  public final static int HEADER_HEIGHT = 60;
  public final static int LEFT_WIDTH = 30;
  public final static int MESSAGE_NUMBER_X_POSITION = 29;
  public final static int MESSAGE_NUMBER_Y_OFFSET = 4;
  public final static int FIRST_ACTOR_X_POSITION = 27;
  public final static int SPACE_BETWEEN_ACTORS = 54;
  public final static int BOX_WIDTH = 48;   // must be even
  public final static int BOX_HEIGHT = 30; // must be even
  public final static int BOX_Y_POSITION = 20;
  public final static int BOX_BOTTOM_TEXT_POSITION = 40;
  public final static int BOX_CONTAINER_OFFSET = 2;
  public final static int BOX_CONTAINER_Y1 = 5;
  public final static int BOX_CONTAINER_HEIGHT = 50;
  public final static int BOX_CONTAINER_BOTTOM_TEXT_POSITION = 15;
  public final static int ACTOR_LINE_WIDTH = 1;
  public final static int BOX_ROUNDING = 10;
  public final static int SPACE_BETWEEN_MESSAGES = 10;

  public final static int ARROW_LENGTH = 7;
  public final static int ARROW_WIDTH = 4;

  public final static Color BACKGROUND_COLOR = Color.WHITE;
  public final static Color BOX_BORDER_COLOR = Color.BLACK;
  public final static Color BOX_LOGGED_CONTAINER_COLOR = Color.LIGHT_GRAY;
  public final static Color BOX_UNLOGGED_CONTAINER_COLOR = Color.WHITE;
  public final static Color BOX_OTHER_ACTOR = Color.WHITE;
  public final static Color BOX_AGENT_ACTOR = Color.RED;
  public final static Color BOX_SERVICE_ACTOR = Color.GREEN;
  public final static Color ACTOR_LINE_COLOR = Color.BLACK;

  public final static Font BOX_FONT = new Font("Arial",Font.PLAIN, 10);

  public final static Stroke BOX_ACTOR_STROKE = new BasicStroke(1);
  public final static Stroke BOX_CONTAINER_STROKE = new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1.0f,new float[]{3,2},0);
  public final static Stroke BOX_ACTOR_SELECTED_STROKE = new BasicStroke(2);
  public final static Stroke BOX_CONTAINER_SELECTED_STROKE = new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1.0f,new float[]{3,2},0);
  public final static Stroke REGULAR_MESSAGE_STROKE = new BasicStroke(2);
  public final static Stroke INACCESSIBLE_MESSAGE_STROKE = new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1.0f,new float[]{5,5},0);
  public final static Stroke SELECTED_REGULAR_MESSAGE_STROKE = new BasicStroke(4);
  public final static Stroke SELECTED_INACCESSIBLE_MESSAGE_STROKE = new BasicStroke(4,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1.0f,new float[]{5,5},0);

  public final static Color OTHER_MESSAGE_TYPE_COLOR = Color.PINK;

  public final static String[] MESSAGE_TYPES = new String[]{
   Performative.ACCEPT_PROPOSAL.toString(),
   Performative.AGREE.toString(),
   Performative.CALL_FOR_PROPOSAL.toString(),
   Performative.CONFIRM.toString(),
   Performative.DISCONFIRM.toString(),
   Performative.FAILURE.toString(),
   Performative.INFORM.toString(),
   Performative.PROPOSE.toString(),
   //Performative.COUNTER_PROPOSE",
   Performative.REFUSE.toString(),
   Performative.REJECT_PROPOSAL.toString(),
   Performative.NONE.toString(),

   Performative.SUBSCRIBE.toString(),
   Performative.REQUEST.toString(),

//      MessageConstants.INFORM,
//      MessageConstants.REQUEST,
//      MessageConstants.FAILURE,
//      MessageConstants.AGREE,
//      MessageConstants.REFUSE,
//      MessageConstants.NOT_UNDERSTOOD,
//      MessageConstants.INFORM_RESULT,
//      MessageConstants.INFORM_DONE,
////      MessageConstants.CFP,      
//      MessageConstants.PROPOSAL,
//      MessageConstants.ACCEPT_PROPOSAL,
//      MessageConstants.REJECT_PROPOSAL,
//      MessageConstants.COUNTER_PROPOSE,
//      MessageConstants.PROPOSE_PENALTY,
//      MessageConstants.COUNTER_PROPOSE_PENALTY,
//      MessageConstants.IMPOSE_PROPOSAL,
//      MessageConstants.DECOMMIT,
//      MessageConstants.TAKE_BACK
  };
  public final static Color[] MESSAGE_TYPE_COLORS = {
    Color.GREEN,  //   "ACCEPT_PROPOSAL",
    Color.GREEN,  //   "AGREE",
    Color.BLUE,   //   "CFP",
    Color.MAGENTA,//   "CONFIRM",
    Color.RED,    //   "DISCONFIRM",
    Color.ORANGE, //   "FAILURE",
    Color.BLACK,  //   "INFORM", 
    Color.getHSBColor( (float)(48.0/360.0) , (float)(77.0/100.0) , (float)(1.0) ),//   "PROPOSE", 
    //Color.YELLOW,
    //Color.getHSBColor( (float)(48.0/360.0) , (float)(77.0/100.0) , (float)(0.8) ),//"COUNTER_PROPOSE",
    Color.RED,    //   "REFUSE", 
    Color.RED,    //   "REJECT_PROPOSAL", 
    Color.PINK,    //   "UNKNOWN"
      
    Color.BLUE,
    Color.BLUE,

//      Color.BLACK,
//      Color.BLUE,
//      Color.ORANGE,
//      Color.GREEN,
//      Color.RED,
//      Color.GRAY,
//      Color.MAGENTA,
//      Color.CYAN,
//      //     Color.PINK,
//      Color.getHSBColor( (float)(48.0/360.0) , (float)(77.0/100.0) , (float)(1.0) ), //   Color.YELLOW,
//      Color.GREEN,
//      Color.RED,
//      Color.getHSBColor( (float)(50.0/360.0) , (float)(77.0/100.0) , (float)(1.0) ), //      Color.YELLOW,
//      Color.CYAN,
//      Color.MAGENTA,
//      Color.BLUE,
//      Color.RED,
//      Color.ORANGE
  };
}
