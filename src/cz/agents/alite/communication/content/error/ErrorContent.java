package cz.agents.alite.communication.content.error;

import cz.agents.alite.communication.content.Content;

/**
 * Error content for encapsulating a {@link Exception} for messaging.
 * 
 * @author Jiri Vokrinek
 */
public class ErrorContent extends Content {

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
