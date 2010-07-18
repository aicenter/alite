package cz.agents.alite.googleearth.handler;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import cz.agents.alite.googleearth.KmlFileCreator;

/**
 * Http handler for images
 * 
 * @author Ondrej Milenovsky
 * 
 */
public abstract class ImageHandler implements HttpHandler
{
    private boolean reports = false;
    private String imageType = "png";
    
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
        try
        {
            doResponse(exchange);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if(reports)
            System.out.println(getName() + "> request served in " + (System.currentTimeMillis() - time)
                + "ms.");
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
        responseHeaders.add("Content-Type", "image/" + imageType);
        responseHeaders.add("Content-Encoding", "identity");
        BufferedImage content = createContentFromEnvironment(exchange.getRequestURI().getQuery());
        
        try
        {
            exchange.sendResponseHeaders(200, 0);
            OutputStream output = exchange.getResponseBody();
            ImageIO.write(content, imageType, output);
            output.close();
        } catch (IOException e)
        {}
    }

    /**
     * This method should be implemented to provide the KML file in form of a
     * String. Use {@link KmlFileCreator} for KML creation, then use
     * {@link KmlFileCreator#getKML()} to retrieve the KML String and return
     * this String.
     * 
     * @return
     */
    protected abstract BufferedImage createContentFromEnvironment(String query);

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }
    
    public String getImageType()
    {
        return imageType;
    }
    
}
