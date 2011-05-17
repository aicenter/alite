package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.layer.simple.SimpleAbstractLayer;

import java.awt.Color;
import java.util.Iterator;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Layer to draw points. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class SimplePointLayer extends SimpleAbstractLayer {

    private static int layerCount = 0;

    public SimplePointLayer(String name) {
        super(name);
    }

    public SimplePointLayer() {
        this("Points " + ++layerCount);
    }

    @Override
    protected String getDefaultElementName() {
        return "Point";
    }

    @Override
    protected final void generateFrame() {
        boolean constantSize = isConstantSize();
        Iterator<Vector3D> itPoints = getPoints().iterator();
        Iterator<Color> itColors = getColors().iterator();
        Iterator<Double> itSizes = getSizes().iterator();
        if (!itPoints.hasNext()) {
            return;
        }
        if (!itColors.hasNext()) {
            throw new RuntimeException("Colors are empty, must contain at least one item");
        }
        if (!itSizes.hasNext()) {
            throw new RuntimeException("Sizes are empty, must contain at least one item");
        }

        Iterator<String> itNames = null;
        Iterable<String> names = getNames();
        if (names != null) {
            itNames = names.iterator();
        }

        Color lastColor = null;
        double lastSize = 0;
        while (itPoints.hasNext()) {
            String name = null;
            if ((itNames != null) && (itNames.hasNext())) {
                name = itNames.next();
            }
            if (itColors.hasNext()) {
                lastColor = itColors.next();
            }
            if (itSizes.hasNext()) {
                lastSize = itSizes.next();
            }
            addElement(name, PointKeys.TYPE).with(PointKeys.CENTER, itPoints.next()).with(
                    PointKeys.CONSTANT_SIZE, constantSize).with(PointKeys.COLOR, lastColor).with(
                    PointKeys.SIZE, lastSize).end();
        }
    }

    /** create center positions for the points */
    protected abstract Iterable<Vector3D> getPoints();

    /** create colors for the points (can contain only one item) */
    protected abstract Iterable<Color> getColors();

    /** create sizes for the points (can contain only one item) */
    protected abstract Iterable<Double> getSizes();

}
