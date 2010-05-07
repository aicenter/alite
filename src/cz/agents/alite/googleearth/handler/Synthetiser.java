package cz.agents.alite.googleearth.handler;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * Synthetizes various visual information and sends it through the {@link HttpServer}
 * to the Google Earth component.
 *
 * @author Ondrej Vanek
 *
 */
public class Synthetiser {

    private HttpServer server;
    public static final int PORT = 8080;
    public static final int MAXIMUM_SIMULTANEOUS_CONNECTIONS = 1000;
    private boolean serverInitialized = false;


    /**
     * Initialization of the Synthetizer - initializes the HttpServer.
     * @param container
     * @param analyzer
     */
    public void init() {
        initHttpServer();
        System.out.println("Server started.");

    }
    /**
     * Initialization of HTTP server and creation of contexts handled by various {@link GoogleEarthHandler}s.
     * @param container
     * @param analyzer
     */
    private void initHttpServer() {
        if (server == null) {
            try {
                // Set up server on port with max connections.
                server = HttpServer.create(new InetSocketAddress(PORT),
                        MAXIMUM_SIMULTANEOUS_CONNECTIONS);

                server.setExecutor(null);
                server.start(); //START!
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        serverInitialized = true;
    }

    public void addHandler(GoogleEarthHandler handler, String link) {
        if(!serverInitialized) initHttpServer();
        server.createContext("/"+link,handler);
    }

    public void startServer() {
        server.start();
    }

    public void stopServer() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }
}
