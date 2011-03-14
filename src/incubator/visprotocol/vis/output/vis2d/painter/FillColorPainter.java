package incubator.visprotocol.vis.output.vis2d.painter;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.painter.Painter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FillColorPainter implements Painter {

    /** all parameter ids which this painter can paint */
    public static final Set<String> TYPES = new HashSet<String>(Arrays.asList(FillColorKeys.COLOR
            .toString()));

    private final Vis2DOutput vis2dOutput;

    private Color color = Color.BLACK;

    public FillColorPainter(Vis2DOutput vis2dOutput) {
        this.vis2dOutput = vis2dOutput;
    }

    @Override
    public void paint(Element e) {
        if (e.containsParameter(FillColorKeys.COLOR)) {
            color = e.getParameter(FillColorKeys.COLOR);
        }
        Graphics2D graphics2d = vis2dOutput.getGraphics2D();
        graphics2d.setColor(color);
        graphics2d.fillRect(0, 0, vis2dOutput.getWidth(), vis2dOutput.getHeight());
    }

}
