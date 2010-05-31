/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.agents.alite.googleearth;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.vecmath.Point2d;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import cz.agents.alite.googleearth.kml.AbstractFeatureType;
import cz.agents.alite.googleearth.kml.AbstractGeometryType;
import cz.agents.alite.googleearth.kml.AbstractObjectType;
import cz.agents.alite.googleearth.kml.AbstractStyleSelectorType;
import cz.agents.alite.googleearth.kml.AltitudeModeEnumType;
import cz.agents.alite.googleearth.kml.BoundaryType;
import cz.agents.alite.googleearth.kml.DocumentType;
import cz.agents.alite.googleearth.kml.FolderType;
import cz.agents.alite.googleearth.kml.KmlType;
import cz.agents.alite.googleearth.kml.LineStringType;
import cz.agents.alite.googleearth.kml.LookAtType;
import cz.agents.alite.googleearth.kml.ObjectFactory;
import cz.agents.alite.googleearth.kml.PairType;
import cz.agents.alite.googleearth.kml.PlacemarkType;
import cz.agents.alite.googleearth.kml.PointType;
import cz.agents.alite.googleearth.kml.PolygonType;
import cz.agents.alite.googleearth.kml.StyleMapType;
import cz.agents.alite.googleearth.kml.StyleType;

/**
 * Basic wrapper for KML file creation.
 * 
 * @author Petr Benda
 * @author Ondrej Milenovsky
 */
public class KmlFileCreator
{

	public static final String KML_ORIGIN_FILE = "resources/googleearth-origin/origin2.kml";

	private static final String BODY_STYLE = "body";
	private static final String EVENT_STYLE = "event";
	public static final String STYLE_WALKER = "sn_walker";
	public static final String STYLE_METRO = "sn_metro";
	public static final String STYLE_CAR = "sn_car";
	public static final String STYLE_STREET = "sh_ylw-pushpin00";

	public static final int LINE_STYLE_STREET = 0;
	public static final int LINE_STYLE_METROA = 1;
	public static final int LINE_STYLE_METROB = 2;
	public static final int LINE_STYLE_METROC = 3;

	public static final int POLY_STYLE_GREEN = 0;
	public static final int POLY_STYLE_YELLOW = 1;
	public static final int POLY_STYLE_RED = 2;
	public static final int POLY_STYLE_BLUE = 3;
	
	protected FolderType folder;
	protected ArrayList<JAXBElement<? extends AbstractFeatureType>> polygonsOrigin = new ArrayList<JAXBElement<? extends AbstractFeatureType>>();
	protected ArrayList<JAXBElement<? extends AbstractFeatureType>> roadsOrigin = new ArrayList<JAXBElement<? extends AbstractFeatureType>>();
	protected JAXBElement<? extends AbstractFeatureType> placemarkOrigin;

	DocumentType type;
	JAXBElement<? extends AbstractObjectType> placemarkStyleMapOrigin;
	List<JAXBElement<? extends AbstractFeatureType>> placemarkStyleElements;

	JAXBContext context;
	JAXBElement<? extends AbstractFeatureType> rootElement;

	public KmlFileCreator()
	{
		loadOrigins(KmlFileCreator.KML_ORIGIN_FILE);
	}

	public String createStringCoordinate(double x, double y, double z)
	{
		return x + "," + y + "," + z;
	}

	public List<String> createStringCoordinateList(List<Point2d> coordinates)
	{
		List<String> out = new ArrayList<String>(coordinates.size());
		for(Point2d point : coordinates)
		{
			out.add(createStringCoordinate(point.x, point.y, 0d));
		}
		return out;
	}

	public void createRoadFromStringCoords(List<String> coordinates, String name)
	{
		createRoadFromStringCoords(coordinates, name, 0);		
	}
	
	public void createRoadFromStringCoords(List<String> coordinates, String name, int style)
	{
		@SuppressWarnings("unchecked")
		JAXBElement<? extends AbstractFeatureType> clone = (JAXBElement<? extends AbstractFeatureType>) SerializableObjectCloner
				.clone(roadsOrigin.get(style));

		Object ob = clone.getValue();
		PlacemarkType placemark = (PlacemarkType) ob;
		placemark.setDescription("<html>Name: " + name + "</html>");
		JAXBElement<? extends AbstractGeometryType> geometry = placemark
				.getAbstractGeometryGroup();
		LineStringType line = (LineStringType) geometry.getValue();
		line.getCoordinates().clear();
		line.getCoordinates().addAll(coordinates);
		//if(style != null) placemark.setStyleUrl("#" + style);
		folder.getAbstractFeatureGroup().add(clone);
	}

	public void createBodyPlacemark(double lat, double lon, String name,
			String description)
	{
		createPlacemark(lat, lon, name, description, STYLE_WALKER);
	}

	public void createEventPlacemark(double lon, double lat, String name,
			String description)
	{
		createPlacemark(lon, lat, name, description, EVENT_STYLE);
	}

	/**creates icon*/
	public void createPlacemark(double lon, double lat, String name,
			String description, String style)
	{
		try
		{
			@SuppressWarnings("unchecked")
			JAXBElement<? extends AbstractFeatureType> clone = (JAXBElement<? extends AbstractFeatureType>) SerializableObjectCloner
					.clone(placemarkOrigin);
			Object ob = clone.getValue();
			PlacemarkType placemark = (PlacemarkType) ob;

			placemark.setName(name);
			if(description != null)
			{
				placemark.setDescription(description);
			}

			JAXBElement<? extends AbstractGeometryType> geometry = placemark
					.getAbstractGeometryGroup();
			PointType pt = (PointType) geometry.getValue();
			pt.getCoordinates().clear();
			pt.getCoordinates().add(lon + "," + lat + ",0");

			// StyleMapType st = (StyleMapType)currentStyleMap.getValue();

			placemark.setStyleUrl("#" + style);

			LookAtType lookAt = (LookAtType) placemark.getAbstractViewGroup()
					.getValue();

			lookAt.setLatitude(lat);
			lookAt.setLongitude(lon);

			folder.getAbstractFeatureGroup().add(clone);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createPolygonFromStringCoords(String name,
			List<String> coordinates, String description, boolean extruded)
	{
		createPolygonFromStringCoords(name, coordinates, description, extruded, 0);		
	}
	
	public void createPolygonFromStringCoords(String name,
			List<String> coordinates, String description, boolean extruded, int style)
	{
		@SuppressWarnings("unchecked")
		JAXBElement<? extends AbstractFeatureType> clone = (JAXBElement<? extends AbstractFeatureType>) SerializableObjectCloner
				.clone(polygonsOrigin.get(style));
		Object ob = clone.getValue();
		PlacemarkType placemark = (PlacemarkType) ob;
		placemark.setName(name);
		placemark.setDescription(description);
		// TODO set dynamic type
		JAXBElement<? extends AbstractGeometryType> geometry = placemark
				.getAbstractGeometryGroup();
		PolygonType polygon = (PolygonType) geometry.getValue();
		if(extruded)
		{
			polygon.setExtrude(true);
			polygon.setTessellate(true);
			ObjectFactory factory = new ObjectFactory();
			factory.createAltitudeMode(AltitudeModeEnumType.ABSOLUTE);
			polygon.setAltitudeModeGroup(factory
					.createAltitudeMode(AltitudeModeEnumType.ABSOLUTE));
		}
		BoundaryType boundaries = polygon.getOuterBoundaryIs();
		boundaries.getLinearRing().getCoordinates().clear();
		boundaries.getLinearRing().getCoordinates().addAll(coordinates);
		folder.getAbstractFeatureGroup().add(clone);
	}

	@SuppressWarnings("unchecked")
	public void loadOrigins(String fileName)
	{
		try
		{
			context = JAXBContext
					.newInstance("cz.agents.alite.googleearth.kml");
			Unmarshaller um = context.createUnmarshaller();
			rootElement = (JAXBElement<? extends AbstractFeatureType>) um
					.unmarshal(new File(fileName));
			Object o = rootElement.getValue();
			KmlType t = (KmlType) o;
			JAXBElement<? extends AbstractFeatureType> feature = t
					.getAbstractFeatureGroup();
			type = (DocumentType) feature.getValue();

			List<JAXBElement<? extends AbstractObjectType>> styles = new LinkedList<JAXBElement<? extends AbstractObjectType>>();
			List<JAXBElement<? extends AbstractStyleSelectorType>> styleMaps = new LinkedList<JAXBElement<? extends AbstractStyleSelectorType>>();

			for(JAXBElement<? extends AbstractStyleSelectorType> el: type
					.getAbstractStyleSelectorGroup())
			{
				Object obj2 = el.getValue();
				if(obj2 instanceof StyleType)
				{
					// StyleType s = (StyleType)obj2;
					styles.add(el);
				}
				if(obj2 instanceof StyleMapType)
				{
					// StyleMapType map = (StyleMapType)obj2;
					styleMaps.add(el);
				}
			}

			List<JAXBElement<? extends AbstractFeatureType>> elements = type
					.getAbstractFeatureGroup();
			JAXBElement<?> element2 = (JAXBElement<?>) elements.get(0);
			FolderType ft = (FolderType) element2.getValue();
			folder = ft;
			//this is very stupid...
			roadsOrigin.add(ft.getAbstractFeatureGroup().get(5)); //street
			roadsOrigin.add(ft.getAbstractFeatureGroup().get(6)); //metro A
			roadsOrigin.add(ft.getAbstractFeatureGroup().get(7)); //metro B
			roadsOrigin.add(ft.getAbstractFeatureGroup().get(8)); //metro C
			polygonsOrigin.add(ft.getAbstractFeatureGroup().get(9)); //light green
			polygonsOrigin.add(ft.getAbstractFeatureGroup().get(10)); //light yellow
			polygonsOrigin.add(ft.getAbstractFeatureGroup().get(11)); //light red
			polygonsOrigin.add(ft.getAbstractFeatureGroup().get(12)); //metro station (blue)
			// modelOrign = ft.getAbstractFeatureGroup().get(2);
			placemarkOrigin = ft.getAbstractFeatureGroup().get(3);
			// predictionRoadOrigin = ft.getAbstractFeatureGroup().get(4);

			PlacemarkType placemark = (PlacemarkType) placemarkOrigin
					.getValue();
			String style = placemark.getStyleUrl().substring(1);

			placemarkStyleElements = new LinkedList<JAXBElement<? extends AbstractFeatureType>>();

			for(JAXBElement<? extends AbstractObjectType> smtElement : styleMaps)
			{
				StyleMapType smt = (StyleMapType) smtElement.getValue();

				String id = smt.getId();

				if(id.equals(style))
				{
					placemarkStyleMapOrigin = smtElement;

					List<PairType> pairs = smt.getPair();

					List<String> relevantStyleNames = new LinkedList<String>();
					for(PairType pair : pairs)
					{
						relevantStyleNames.add(pair.getStyleUrl());
					}

					for(JAXBElement<? extends AbstractObjectType> styleElement : styles)
					{
						StyleType s = (StyleType) styleElement.getValue();

						if(relevantStyleNames.contains("#" + s.getId()))
						{
							placemarkStyleElements
									.add((JAXBElement<? extends AbstractFeatureType>) styleElement);
						}
					}
				}
			}

			// remove origins from KML
			folder.getAbstractFeatureGroup().clear();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String getKML()
	{
		Marshaller m;
		try
		{
			m = context.createMarshaller();

			StringWriter stringWriter = new StringWriter();
			m.marshal(rootElement, stringWriter);
			String s = stringWriter.toString();
			return s;
		} catch (JAXBException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void clear()
	{
		folder.getAbstractFeatureGroup().clear();
	}

}