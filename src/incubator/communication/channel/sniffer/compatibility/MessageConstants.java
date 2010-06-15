package incubator.communication.channel.sniffer.compatibility;

//import aglobe.container.task.*;

/**
 *
 * <p>Title: A-Globe</p>
 * <p>Description: String constants used in messages are defined here (such as protocols, performatives, etc.).</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Gerstner Laboratory</p>
 * @author David Sislak
 * @version $Revision: 1.1 $ $Date: 2008/02/28 12:58:06 $
 *
 */
// TODO: complete the javadoc

public interface MessageConstants
{
    
//       ACCEPT_PROPOSAL, AGREE, CFP, CONFIRM, DISCONFIRM, FAILURE,
//        INFORM, PROPOSE, REFUSE, REJECT_PROPOSAL, UNKNOWN;
    
  public final static String INFORM = "INFORM";
  public final static String INFORM_TF = "INFORM-T/F";
  public final static String INFORM_DONE = "INFORM-DONE";
  public final static String INFORM_RESULT = "INFORM-RESULT";
  public final static String SUBSCRIBE = "SUBSCRIBE";
  public final static String REQUEST = "REQUEST";
  public final static String AGREE = "AGREE";
  public final static String NOT_UNDERSTOOD = "NOT-UNDERSTOOD";
  public final static String DONE = "DONE";
  public final static String FAILURE = "FAILURE";
  public final static String REFUSE = "REFUSE";
  public final static String QUERY = "QUERY";
  public final static String QUERY_REF = "QUERY-REF";
  public final static String QUERY_IF = "QUERY-IF";
  public final static String CFP = "CFP";
  public final static String PROPOSAL = "PROPOSAL";
  public final static String ACCEPT_PROPOSAL = "ACCEPT-PROPOSAL";
  public final static String REJECT_PROPOSAL = "REJECT-PROPOSAL";
  
  public final static String COUNTER_PROPOSE = "COUNTER-PROPOSE";
  public final static String PROPOSE_PENALTY = "PROPOSE-PENALTY";
  public final static String COUNTER_PROPOSE_PENALTY = "COUNTER-PROPOSE-PENALTY";
  public final static String IMPOSE_PROPOSAL = "IMPOSE-PROPOSAL";  
  public final static String DECOMMIT = "DECOMMIT";
  public final static String TAKE_BACK = "TAKE-BACK";
          
  public final static String CONTRACT_NET = "CONTRACT-NET";
}
