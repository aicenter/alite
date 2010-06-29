package cz.agents.alite.googleearth.handler;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;

/**
 * Synthetizes various visual information and sends it through the
 * {@link HttpServer} to the Google Earth component.
 * 
 * @author Ondrej Vanek
 * @author Ondrej Milenovsky
 */
public class Synthetiser
{

    private HttpServer server;
    public static final int PORT = 8080;
    public static final int MAXIMUM_SIMULTANEOUS_CONNECTIONS = 1000;
    private boolean serverInitialized = false;

    private final Map<String, GoogleEarthHandler> handlerMap = new HashMap<String, GoogleEarthHandler>();

    /**
     * Initialization of the Synthetizer - initializes the HttpServer.
     * 
     * @param container
     * @param analyzer
     */
    public void init() throws Exception
    {
        initHttpServer();
        System.out.println("Server started.");

    }

    /**
     * Initialization of HTTP server and creation of contexts handled by various
     * {@link GoogleEarthHandler}s.
     * 
     * @param container
     * @param analyzer
     */
    private void initHttpServer() throws Exception
    {
        if(server == null)
        {
            // Set up server on port with max connections.
            server = HttpServer.create(new InetSocketAddress(PORT),
                    MAXIMUM_SIMULTANEOUS_CONNECTIONS);

            server.setExecutor(null);
            server.start(); // START!
            serverInitialized = true;
        } else
            serverInitialized = true;
    }

    public void addHandler(GoogleEarthHandler handler, String link) throws Exception
    {
        if(!serverInitialized)
            initHttpServer();
        server.createContext("/" + link, handler);
        // store link and handler (will be readded when server restart)
        handlerMap.put(link, handler);
    }

    public void startServer() throws Exception
    {
        if(server == null)
            init();
        else
            server.start();

        // now readd handlers (used when server restart)
        for(String link: handlerMap.keySet())
        {
            server.createContext("/" + link, handlerMap.get(link));
        }
    }

    public void stopServer()
    {
        if(server != null)
        {
            server.stop(0);
            server = null;
        }
    }
}
