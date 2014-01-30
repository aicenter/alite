package cz.agents.alite.communication.channel;

/**
 * Used to implement passive communication - in order to receive any messages 
 * via a MessageHandler, the performReceive() method must be invoked. 
 * The message (if any) is then delivered using the same thread which called 
 * the performReceive() method. The call is non-blocking.
 * 
 * @author stolba
 *
 */
public interface CommunicationPerformerChannel extends CommunicationChannel {
	
	/**
	 * Use the current thread to poll a message and give it to the handlers.
	 * The call is non-blocking.
	 */
	public void performReceive();
	
}
