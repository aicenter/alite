/**
 * 
 */
package cz.agents.alite.googleearth.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Point2d;

import cz.agents.alite.googleearth.KmlFileCreator;
import cz.agents.alite.googleearth.handler.GoogleEarthHandler;
import cz.agents.alite.googleearth.handler.Synthetiser;

/**
 * A demo handler, creates a few bodies and a few events from local storage every time
 * it is requested.
 * @author Ondrej Vanek
 *
 */
public class DemoHandler extends GoogleEarthHandler {

	/**
	 * Complete Link for Google Earth:
	 * http://localhost:{@link Synthetiser#PORT}/demo.kml
	 * 
	 */
	public static final String LINK = "demo.kml";
	
	private List<Point2d> bodies = new ArrayList<Point2d>();
	private List<Point2d> nodes = new ArrayList<Point2d>();

	private final double pragueLat = 50.08;
	private final double pragueLon = 14.45;
	
	/**
	 * An instance of a {@link KmlFileCreator}, a wrapper that handles
	 * the KML creation via JAXB.
	 * 
	 * if there are more handlers, create just one kml creator
	 */
	KmlFileCreator kmlCreator = new KmlFileCreator("resources/googleearth-origin/origin.kml");
	

	@Override
	protected String createContentFromEnvironment(String query) {
		long time = System.currentTimeMillis();

		// GENERATE SOME RANDOM DATA - gather it somewhere else.
		kmlCreator.clear();
		bodies.clear();
		nodes.clear();
		Random r = new Random();
		for(int i =0; i<10;i++ ) {
			double randomLat = r.nextDouble()*0.1-0.05;
			double randomLon = r.nextDouble()*0.1-0.05;
			Point2d bodyLoc = new Point2d(pragueLon + randomLon,pragueLat +randomLat);
			bodies.add(bodyLoc);
			
			randomLat = r.nextDouble()*0.1-0.05;
			randomLon = r.nextDouble()*0.1-0.05;
			Point2d eventLoc = new Point2d(pragueLon + randomLon,pragueLat +randomLat);
			nodes.add(eventLoc);
			
		}
		
		kmlCreator.newFolder("Agents");
		//PUSH THE DATA INTO KML CREATOR 
		int counter = 0;
		for(Point2d p: bodies) {
			//create placemark
			kmlCreator.createPlacemark(p.x,p.y,"Prazak "+counter,"Prazak", KmlFileCreator.ICON_STYLE);
			counter++;
		}
		List<String> coords = new ArrayList<String>(); 
		for(Point2d p : nodes) {
			//convert points to set of coordinates
			String coord = KmlFileCreator.createStringCoordinate(p.x, p.y, 0);
			coords.add(coord);
		}
		
		kmlCreator.newFolder("Roads");
		//push the list of coordinates
		kmlCreator.createRoadFromStringCoords(coords, "One fine Line", KmlFileCreator.LINE_STYLE);
		
		System.out.println("Content to KML: " + (System.currentTimeMillis() - time));
		
		time = System.currentTimeMillis();
		String kmlContent = kmlCreator.getKML(); // marshalles the KML and returns it
		System.out.println("KML creation: " + (System.currentTimeMillis() - time));

		return kmlContent;
		
	}


	@Override
	protected String getName() {
		return "Demo Handler";
	}

}
