/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.agents.alite.googleearth;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.vecmath.Point2d;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import cz.agents.alite.googleearth.kml.AbstractFeatureType;
import cz.agents.alite.googleearth.kml.AbstractGeometryType;
import cz.agents.alite.googleearth.kml.AltitudeModeEnumType;
import cz.agents.alite.googleearth.kml.BasicLinkType;
import cz.agents.alite.googleearth.kml.BoundaryType;
import cz.agents.alite.googleearth.kml.DocumentType;
import cz.agents.alite.googleearth.kml.FolderType;
import cz.agents.alite.googleearth.kml.IconStyleType;
import cz.agents.alite.googleearth.kml.KmlType;
import cz.agents.alite.googleearth.kml.LabelStyleType;
import cz.agents.alite.googleearth.kml.LatLonAltBoxType;
import cz.agents.alite.googleearth.kml.LineStringType;
import cz.agents.alite.googleearth.kml.LineStyleType;
import cz.agents.alite.googleearth.kml.LodType;
import cz.agents.alite.googleearth.kml.LookAtType;
import cz.agents.alite.googleearth.kml.ObjectFactory;
import cz.agents.alite.googleearth.kml.PlacemarkType;
import cz.agents.alite.googleearth.kml.PointType;
import cz.agents.alite.googleearth.kml.PolyStyleType;
import cz.agents.alite.googleearth.kml.PolygonType;
import cz.agents.alite.googleearth.kml.RegionType;
import cz.agents.alite.googleearth.kml.StyleType;

/**
 * Basic wrapper for KML file creation. should be inherited
 * 
 * if replace array != null in contructor, it will replace text in array[i][0]
 * by array[i][1] before parsing, save to file and this file will be new source
 * 
 * in inherited class override methods createStyles method replaceText could be
 * also overrided
 * 
 * how to use: first create folder, then add some objects, create another
 * folder...
 * 
 * 
 * under construction !
 * 
 * @author Petr Benda
 * @author Ondrej Milenovsky
 */
public class KmlFileCreator
{

    public static final String FILE_NAME2 = "tmp.kml";

    public static final String ICON_STYLE = "body";
    public static final String POLY_STYLE = "polygon1";
    public static final String LINE_STYLE = "line1";

    protected JAXBElement<? extends AbstractFeatureType> polygonOrigin;
    protected JAXBElement<? extends AbstractFeatureType> lineOrigin;
    protected JAXBElement<? extends AbstractFeatureType> modelOrigin;
    protected JAXBElement<? extends AbstractFeatureType> placemarkOrigin;

    protected DocumentType type;
    protected FolderType currentFolder;

    protected JAXBContext context;
    protected JAXBElement<? extends AbstractFeatureType> rootElement;

    protected ObjectFactory factory;

    public KmlFileCreator(String fileName)
    {
        this(fileName, null);
    }

    public KmlFileCreator(String fileName, String[][] replaceArray)
    {
        // first replace some text in the file
        String file2 = fileName;
        if(replaceArray != null)
        {
            try
            {
                saveFile(FILE_NAME2, replaceText(loadFile(fileName), replaceArray));
                file2 = FILE_NAME2;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        // now parse xml
        loadOrigins(file2);
        // delete tmp file
        deleteFile(FILE_NAME2);
    }

    public static List<String> createStringCoordinateList(List<Point2d> coordinates)
    {
        List<String> out = new ArrayList<String>(coordinates.size());
        for(Point2d point: coordinates)
        {
            out.add(createStringCoordinate(point.x, point.y, 0d));
        }
        return out;
    }

    public void createRoadFromStringCoords(List<String> coordinates, String name, String style)
    {
        createRoadFromStringCoords(coordinates, name, "", style);
    }

    public void createRoadFromStringCoords(List<String> coordinates, String name,
            String description, String style)
    {
        @SuppressWarnings("unchecked")
        JAXBElement<? extends AbstractFeatureType> clone = (JAXBElement<? extends AbstractFeatureType>)SerializableObjectCloner
                .clone(lineOrigin);

        Object ob = clone.getValue();
        PlacemarkType placemark = (PlacemarkType)ob;
        placemark.setDescription(description);
        placemark.setName(name);
        JAXBElement<? extends AbstractGeometryType> geometry = placemark.getAbstractGeometryGroup();
        LineStringType line = (LineStringType)geometry.getValue();
        line.getCoordinates().clear();
        line.getCoordinates().addAll(coordinates);
        placemark.setStyleUrl("#" + style);
        currentFolder.getAbstractFeatureGroup().add(clone);
    }

    public void createPlacemark(double lon, double lat, String name, String description,
            String style)
    {
        try
        {
            @SuppressWarnings("unchecked")
            JAXBElement<? extends AbstractFeatureType> clone = (JAXBElement<? extends AbstractFeatureType>)SerializableObjectCloner
                    .clone(placemarkOrigin);
            Object ob = clone.getValue();
            PlacemarkType placemark = (PlacemarkType)ob;

            placemark.setName(name);
            if(description != null)
            {
                placemark.setDescription(description);
            }

            JAXBElement<? extends AbstractGeometryType> geometry = placemark
                    .getAbstractGeometryGroup();
            PointType pt = (PointType)geometry.getValue();
            pt.getCoordinates().clear();
            pt.getCoordinates().add(lon + "," + lat + ",0");

            // StyleMapType st = (StyleMapType)currentStyleMap.getValue();

            placemark.setStyleUrl("#" + style);

            LookAtType lookAt = (LookAtType)placemark.getAbstractViewGroup().getValue();

            lookAt.setLatitude(lat);
            lookAt.setLongitude(lon);

            currentFolder.getAbstractFeatureGroup().add(clone);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void createPolygonFromStringCoords(List<String> coordinates, String name,
            boolean extruded, String style)
    {
        createPolygonFromStringCoords(coordinates, name, "", extruded, style);
    }

    public void createPolygonFromStringCoords(List<String> coordinates, String name,
            String description, boolean extruded, String style)
    {
        @SuppressWarnings("unchecked")
        JAXBElement<? extends AbstractFeatureType> clone = (JAXBElement<? extends AbstractFeatureType>)SerializableObjectCloner
                .clone(polygonOrigin);
        Object ob = clone.getValue();
        PlacemarkType placemark = (PlacemarkType)ob;
        placemark.setName(name);
        placemark.setDescription(description);
        placemark.setStyleUrl("#" + style);
        JAXBElement<? extends AbstractGeometryType> geometry = placemark.getAbstractGeometryGroup();
        PolygonType polygon = (PolygonType)geometry.getValue();
        if(extruded)
        {
            polygon.setExtrude(true);
            polygon.setTessellate(true);
            ObjectFactory factory = new ObjectFactory();
            factory.createAltitudeMode(AltitudeModeEnumType.ABSOLUTE);
            polygon.setAltitudeModeGroup(factory.createAltitudeMode(AltitudeModeEnumType.ABSOLUTE));
        }
        BoundaryType boundaries = polygon.getOuterBoundaryIs();
        boundaries.getLinearRing().getCoordinates().clear();
        boundaries.getLinearRing().getCoordinates().addAll(coordinates);
        currentFolder.getAbstractFeatureGroup().add(clone);
    }

    /** create new styles, override this method */
    protected void createStyles()
    {
        // this is just an example, override this method
        addLineStyle(LINE_STYLE, 2.0, Color.RED);
        addPolyStyle(POLY_STYLE, Color.GREEN);
        addIconStyle(ICON_STYLE, 1.5, "body.gif", Color.WHITE);
    }

    /** replace text in the file before parsing, ovveride this method */
    protected String replaceText(String text, String[][] replaceArray)
    {
        for(int i = 0; i < replaceArray.length; i++)
        {
            text = text.replace(replaceArray[i][0], replaceArray[i][1]);
        }
        return text;
    }

    @SuppressWarnings("unchecked")
    public void loadOrigins(String fileName)
    {
        try
        {
            context = JAXBContext.newInstance("cz.agents.alite.googleearth.kml");
            Unmarshaller um = context.createUnmarshaller();
            rootElement = (JAXBElement<? extends AbstractFeatureType>)um.unmarshal(new File(
                    fileName));
            Object o = rootElement.getValue();
            KmlType t = (KmlType)o;
            JAXBElement<? extends AbstractFeatureType> feature = t.getAbstractFeatureGroup();
            type = (DocumentType)feature.getValue();

            factory = new ObjectFactory();
            createStyles();

            List<JAXBElement<? extends AbstractFeatureType>> elements = type
                    .getAbstractFeatureGroup();
            JAXBElement<?> element2 = (JAXBElement<?>)elements.get(0);
            FolderType ft = (FolderType)element2.getValue();

            // this exactly fits to origin.kml
            polygonOrigin = ft.getAbstractFeatureGroup().get(0);
            lineOrigin = ft.getAbstractFeatureGroup().get(1);
            // modelOrign = ft.getAbstractFeatureGroup().get(2);
            placemarkOrigin = ft.getAbstractFeatureGroup().get(3);
            // predictionRoadOrigin = ft.getAbstractFeatureGroup().get(4);

            // remove origins from KML
            ft.getAbstractFeatureGroup().clear();

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
        // remove all folders
        for(Iterator<JAXBElement<? extends AbstractFeatureType>> el = type
                .getAbstractFeatureGroup().iterator(); el.hasNext();)
        {
            if(el.next().getDeclaredType() == FolderType.class)
            {
                el.remove();
            }
        }
    }

    public void newFolder(String folderName)
    {
        FolderType type = factory.createFolderType();
        type.setName(folderName);

        QName name = new QName("http://earth.google.com/kml/2.2", "Folder");
        JAXBElement<FolderType> ft = new JAXBElement<FolderType>(name, FolderType.class,
                JAXBElement.GlobalScope.class, type);

        this.type.getAbstractFeatureGroup().add(ft);
        currentFolder = type;
    }

    /** this method will set region for LOD to a folder */
    public void setFolderLOD(RegionBounds bounds, double min, double max)
    {
        RegionType region = factory.createRegionType();

        LatLonAltBoxType box = factory.createLatLonAltBoxType();
        box.setSouth(bounds.south);
        box.setNorth(bounds.north);
        box.setEast(bounds.east);
        box.setWest(bounds.west);

        LodType lod = factory.createLodType();
        lod.setMinLodPixels(min);
        lod.setMaxLodPixels(max);

        region.setLatLonAltBox(box);
        region.setLod(lod);

        currentFolder.setRegion(region);

    }

    public void addLineStyle(String styleName, Double width, Color color)
    {
        // new StyleType() can be used
        StyleType type = factory.createStyleType();
        type.setId(styleName);
        LineStyleType value = new LineStyleType();
        if(color != null)
            value.setColor(color2byte(color));
        if(width != null)
            value.setWidth(width);
        type.setLineStyle(value);

        // create JAXBElement
        QName name = new QName("http://earth.google.com/kml/2.2", "Style");
        JAXBElement<StyleType> element = new JAXBElement<StyleType>(name, StyleType.class,
                JAXBElement.GlobalScope.class, type);

        // add this element to the styleGroup
        this.type.getAbstractStyleSelectorGroup().add(element);
    }

    public void addPolyStyle(String styleName, Color color)
    {
        // new StyleType() can be used
        StyleType type = factory.createStyleType();
        type.setId(styleName);
        PolyStyleType value = new PolyStyleType();
        if(color != null)
            value.setColor(color2byte(color));
        type.setPolyStyle(value);

        LineStyleType line = new LineStyleType();
        line.setWidth(0.0);
        type.setLineStyle(line);

        // create JAXBElement
        QName name = new QName("http://earth.google.com/kml/2.2", "Style");
        JAXBElement<StyleType> element = new JAXBElement<StyleType>(name, StyleType.class,
                JAXBElement.GlobalScope.class, type);

        // add this element to the styleGroup
        this.type.getAbstractStyleSelectorGroup().add(element);
    }

    public void addIconStyle(String styleName, Double scale, String iconPath, Color labelColor)
    {
        StyleType type = factory.createStyleType();
        type.setId(styleName);

        // icon
        IconStyleType value = new IconStyleType();
        if(scale != null)
            value.setScale(scale);
        // url
        BasicLinkType blt = new BasicLinkType();
        blt.setHref(iconPath);
        value.setIcon(blt);
        type.setIconStyle(value);

        if(labelColor != null)
        {
            LabelStyleType label = new LabelStyleType();
            label.setColor(color2byte(labelColor));
            type.setLabelStyle(label);
        }

        // create JAXBElement
        QName name = new QName("http://earth.google.com/kml/2.2", "Style");
        JAXBElement<StyleType> element = new JAXBElement<StyleType>(name, StyleType.class,
                JAXBElement.GlobalScope.class, type);

        // add this element to the styleGroup
        this.type.getAbstractStyleSelectorGroup().add(element);

    }

    public static byte[] color2byte(Color color)
    {
        return new byte[] {(byte)color.getAlpha(), (byte)color.getBlue(), (byte)color.getGreen(),
                (byte)color.getRed()};
    }

    /**create RegionBounds from string "north south east west"*/
    public static RegionBounds getBoundsFromString(String s)
	{
        String[] parts = s.split(" ");
        try
        {
            return new RegionBounds(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
	}

    /** returns altitude from string */
    public static double getAltFromString(String s)
    {
        String[] parts = s.split(" ");
        if(parts.length >= 5)
            return Double.parseDouble(parts[4]);
        return -1;
    }
    
    public static String getCurrentPath()
    {
        String path = "";
        try
        {
            File currDir = new File(".");
            path = currDir.getCanonicalPath() + File.separator;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return path;
    }

    /** loads file to string */
    public static String loadFile(String name)
    {
        String ret = "";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(name));
            while(true)
            {
                String line = br.readLine();
                if(line == null)
                {
                    break;
                }
                ret += line + "\n";
            }
            br.close();
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return ret;
    }

    /**
     * finds first file that not exist, the file is [part1]+i+[part2]
     * 
     * Example: part1 = "report", initIndex = 1, part2 = ".txt"
     * 
     * tries filenames: report1.txt, report2.txt, report3.txt, ... when found first not existing file
     * */
    public static String findFirstNotExistingFile(String part1, int initIndex, String part2)
    {
        int i = initIndex;
        while(true)
        {
            String s = part1 + i + part2;
            if(!(new File(s).exists()))
                return s;
            i++;
        }
    }
    
    public static String createStringCoordinate(double x, double y, double z)
    {
        return x + "," + y + "," + z;
    }

    /** save string to file */
    public static void saveFile(String name, String content) throws IOException
    {
        File f = new File(name);
        BufferedWriter out = new BufferedWriter(new FileWriter(f));
        out.write(content);
        out.close();
    }

    /** deletes file */
    public static void deleteFile(String name)
    {
        File f = new File(name);
        f.delete();
    }

}
