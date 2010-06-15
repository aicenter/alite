package cz.agents.alite.communication.content;

/**
 * Basic content wrapper for the messaging.
 *
 * @author vokrinek
 */
public class Content {

    private final Object data;

    /**
     *
     * @param data the content data
     */
    public Content(Object data) {
        this.data = data;
    }

    /**
     * Returns the content data.
     *
     * @return the content data
     */
    public Object getData() {
        return data;
    }
}
