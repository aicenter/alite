package incubator.visprotocol.vis.layer.graphicslike;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.structure.key.ShapeKeys;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.structure.key.struct.Shape;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.VisLayer;
import incubator.visprotocol.vis.layer.element.AbstractElement;
import incubator.visprotocol.vis.layer.element.LineElement;
import incubator.visprotocol.vis.layer.element.PointElement;
import incubator.visprotocol.vis.layer.element.ShapeElement;
import incubator.visprotocol.vis.layer.element.TextElementMut;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.vecmath.Point2d;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Similar usage to Graphics. Stores elements to static folders, no changing of elements, only
 * adding and clearing.
 * 
 * @author Ondrej Milenovsky
 * */
// TODO not working well (clearing not working, allows only one pull)
public class GraphicsLike implements VisLayer, Runnable {

    // pointers
    private FilterStorage filter;
    private Structure struct;
    private List<String> path = new ArrayList<String>(2);
    private Folder mainFolder;
    private Folder currentFolder;
    private int folderCount = 0;
    private final HashMap<String, Element> lastElements;
    private final HashMap<String, Integer> elementCount;

    private int flushInterval = 100;
    private Thread flushThread;
    private boolean threadRunning;
    private boolean started = false;

    // state
    private boolean constatnSize = true;
    private Color color = Color.BLACK;
    private double width = 1;
    private Font font = new Font("Arial", Font.PLAIN, 10);
    private double fontSize = 10;

    public GraphicsLike() {
        this("Graphics");
    }

    public GraphicsLike(String name) {
        path.add("World");
        path.add(name);
        lastElements = new HashMap<String, Element>();
        elementCount = new HashMap<String, Integer>();
        clear();
    }

    /** sets path of folder, discards name */
    @Deprecated
    public void setPath(String... newPath) {
        String root = path.get(0);
        path = new ArrayList<String>(newPath.length + 1);
        path.add(root);
        for (int i = 0; i < newPath.length; i++) {
            path.add(newPath[i]);
        }
    }

    public void setFilter(FilterStorage filter) {
        this.filter = filter;
    }

    public void setRoot(String root) {
        path.set(0, root);
        clear();
    }

    public String getName() {
        return StructUtils.printPath(path);
    }

    @Override
    public Structure pull() {
        return struct;
    }

    public void clear() {
        folderCount = 0;
        newStruct();
        nextFolder();
    }

    private void newStruct() {
        struct = new Structure(CommonKeys.STRUCT_PART);
        mainFolder = StructUtils.getFolderOfStruct(struct, path);
    }

    private void nextFolder() {
        lastElements.clear();
        elementCount.clear();
        currentFolder = new Folder(++folderCount + "");
        currentFolder.setParameter(CommonKeys.NOT_CHANGE, true);
    }

    // FILTER /////////////////////////

    private boolean hasType(String type) {
        if (filter == null) {
            return true;
        }
        return filter.hasType(type);
    }

    // ELEMENTS //////////////////

    private void addElement(Element e) {
        Element last = lastElements.get(e.getType());
        last.updateParams(e);
        currentFolder.addElement(e);
        if (!started) {
            started = true;
            startFlushing();
        }
    }

    /** makes swallow copy of the element so it can be used again */
    private void addElement(String name, AbstractElement element) {
        String type = element.getType();
        if (!hasType(type)) {
            return;
        }
        if (!lastElements.containsKey(type)) {
            lastElements.put(type, new Element("", type));
        }
        Element last = lastElements.get(type);
        Element e = element.createElement(last, name, filter);
        addElement(e);
    }

    // SETTERS ///////////////////

    public void setColor(Color color) {
        this.color = color;
    }

    public void setConstatnSize(boolean constatnSize) {
        this.constatnSize = constatnSize;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setFont(Font font) {
        this.font = font;
        fontSize = font.getSize();
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
        font = new Font(font.getName(), font.getStyle(), (int) fontSize);
    }

    // DRAWING ///////////////////

    private int getElementCount(String type) {
        int count = 1;
        if (elementCount.containsKey(type)) {
            count = elementCount.get(type) + 1;
            elementCount.put(type, count);
        }
        return count;
    }

    public void drawPoint(Vector3D point) {
        int count = getElementCount(PointKeys.TYPE);
        addElement("Point " + count, new PointElement(point, color, width, constatnSize));
    }

    public void drawLine(List<Vector3D> points) {
        int count = getElementCount(LineKeys.TYPE);
        addElement("Line " + count, new LineElement(points, color, width, constatnSize));
    }

    public void drawShape(Vector3D center, double sizeX, double sizeY, Shape shape) {
        int count = getElementCount(ShapeKeys.TYPE);
        addElement(shape + " " + count, new ShapeElement(shape, center, color, sizeX, sizeY, width,
                false, constatnSize));
    }

    public void drawText(String text, Vector3D center) {
        int count = getElementCount(TextKeys.TYPE);
        TextElementMut element = new TextElementMut(text, center, color, constatnSize, Align.NONE,
                font);
        element.fontSize = fontSize;
        addElement("Text " + count, element);
    }

    public void drawTextOnScreen(String text, Vector3D pos, Align align) {
        int count = getElementCount(TextKeys.TYPE);
        TextElementMut element = new TextElementMut(text, color, constatnSize, align, pos, font);
        element.fontSize = fontSize;
        addElement("Text on screen " + count, element);
    }

    /** align (0, 0) means upper left corner, (0.5, 1) means middle down */
    public void drawTextOnScreen(String text, Vector3D pos, Point2d align) {
        int count = getElementCount(TextKeys.TYPE);
        TextElementMut element = new TextElementMut(text, color, constatnSize, align, pos, font);
        element.fontSize = fontSize;
        addElement("Text on screen " + count, element);
    }

    // Flushing //////////////
    public void flush() {
        if (currentFolder.getElements().isEmpty()) {
            return;
        }
        mainFolder.addFolder(currentFolder);
        nextFolder();
    }

    public void setFlushInterval(int flushInterval) {
        this.flushInterval = flushInterval;
    }

    public void startFlushing() {
        if (flushThread == null) {
            flushThread = new Thread(this);
            threadRunning = true;
            flushThread.start();
        }
    }

    public void stopFlushing() {
        threadRunning = false;
    }

    @Override
    public void run() {
        while (threadRunning) {
            try {
                Thread.sleep(flushInterval);
            } catch (InterruptedException e) {
            }
            flush();
        }

    }

}
