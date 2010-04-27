package cz.agents.alite.communication.content.error;

import cz.agents.alite.communication.content.Content;

public class ErrorContent extends Content {

    public ErrorContent(Exception exception) {
        super(exception);
    }

    @Override
    public Exception getData() {
        return (Exception) super.getData();
    }
}
