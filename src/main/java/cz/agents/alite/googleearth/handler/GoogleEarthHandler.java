package cz.agents.alite.googleearth.handler;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import cz.agents.alite.googleearth.KmlFileCreator;

/**
 * Abstract class for Google Earth requests. Extend this class to create another
 * handler for separate GE layer.
 * 
 * @author Ondrej Vanek
 * 
 */
public abstract class GoogleEarthHandler implements HttpHandler {
    private boolean reports = false;

    /**
     * For identification and logging purposes.
     * 
     * @return
     */
    protected abstract String getName();

    @Override
    public void handle(HttpExchange exchange) throws IOException
    {

        long time = System.currentTimeMillis();
        try {
            doResponse(exchange);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(reports) {
            System.out.println(getName() + "> request served in "
                    + (System.currentTimeMillis() - time) + "ms.");
        }
    }

    /**
     * Create Response in form of a KML file and send it.
     * 
     * @param exchange
     * @throws IOException
     */
    protected void doResponse(HttpExchange exchange) throws IOException
    {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Content-Type", "application/vnd.google-earth.kml+xml; charset=UTF-8");
        responseHeaders.add("Content-Encoding", "identity");
        String content = "";
        content = createContentFromEnvironment(exchange.getRequestURI().getQuery());

        try {
            if(System.getProperty("os.name").toLowerCase().contains("win")) {
                exchange.sendResponseHeaders(200, content.length());
            } else {
                exchange.sendResponseHeaders(200, 0);
            }
            OutputStream output = exchange.getResponseBody();
            output.write(content.getBytes());
            output.close();
        } catch (IOException e) {
        }
    }

    /**
     * This method should be implemented to provide the KML file in form of a
     * String. Use {@link KmlFileCreator} for KML creation, then use
     * {@link KmlFileCreator#getKML()} to retrieve the KML String and return
     * this String.
     * 
     * @return
     */
    protected abstract String createContentFromEnvironment(String query);

}
