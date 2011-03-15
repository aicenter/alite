package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Painter to fill the canvas with color from the element. Also sets bg color to vis2d.
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorPainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays
            .asList(FillColorKeys.COLOR.id));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;

    public FillColorPainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
        vis2dOutput.setBackground(color);
    }

    @Override
    public void paint(Element e) {
        color = StructUtils.updateValue(e, FillColorKeys.COLOR, color);
        if (!vis2dOutput.getBackground().equals(color)) {
            vis2dOutput.setBackground(color);
        }
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();
        graphics2d.setColor(color);
        graphics2d.fillRect(0, 0, vis2dOutput.getWidth(), vis2dOutput.getHeight());
    }

}
