package cz.cvut.fel.aic.alite.communication.content.error;

import cz.cvut.fel.aic.alite.communication.content.Content;

/**
 * Error content for encapsulating a {@link Exception} for messaging.
 * 
 * @author Jiri Vokrinek
 */
public class ErrorContent extends Content {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7857744158681575964L;

	/**
     *
     * @param exception
     */
    public ErrorContent(Exception exception) {
        super(exception);
    }

    @Override
    public Exception getData() {
        return (Exception) super.getData();
    }
}
