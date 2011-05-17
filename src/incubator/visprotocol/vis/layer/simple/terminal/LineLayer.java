package incubator.visprotocol.vis.layer.simple.terminal;

import incubator.visprotocol.structure.key.LineKeys;
import incubator.visprotocol.vis.layer.simple.SimpleAbstractLayer;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Layer to draw lines. Non static layers should generate names!
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class LineLayer extends SimpleAbstractLayer {

    private static int layerCount = 0;

    public LineLayer(String name) {
        super(name);
    }

    public LineLayer() {
        this("Lines " + ++layerCount);
    }

    @Override
    protected String getDefaultElementName() {
        return "Line";
    }

    @Override
    protected final void generateFrame() {
        boolean constantSize = isConstantSize();
        Iterator<List<Vector3D>> itLines = getLines().iterator();
        Iterator<Color> itColors = getColors().iterator();
        Iterator<Double> itWidths = getWidths().iterator();
        if (!itLines.hasNext()) {
            return;
        }
        if (!itColors.hasNext()) {
            throw new RuntimeException("Colors are empty, must contain at least one item");
        }
        if (!itWidths.hasNext()) {
            throw new RuntimeException("Widths are empty, must contain at least one item");
        }

        Iterator<String> itNames = null;
        Iterable<String> names = getNames();
        if (names != null) {
            itNames = names.iterator();
        }

        Color lastColor = null;
        double lastWidth = 0;
        while (itLines.hasNext()) {
            String name = null;
            if ((itNames != null) && (itNames.hasNext())) {
                name = itNames.next();
            }
            if (itColors.hasNext()) {
                lastColor = itColors.next();
            }
            if (itWidths.hasNext()) {
                lastWidth = itWidths.next();
            }
            addElement(name, LineKeys.TYPE).with(LineKeys.POINTS, itLines.next()).with(
                    LineKeys.CONSTANT_LINE_WIDTH, constantSize).with(LineKeys.COLOR, lastColor).with(
                    LineKeys.LINE_WIDTH, lastWidth).end();
        }
    }

    /** create line points for the lines */
    protected abstract Iterable<List<Vector3D>> getLines();

    /** create colors for the lines (can contain only one item) */
    protected abstract Iterable<Color> getColors();

    /** create widths for the lines (can contain only one item) */
    protected abstract Iterable<Double> getWidths();

}
