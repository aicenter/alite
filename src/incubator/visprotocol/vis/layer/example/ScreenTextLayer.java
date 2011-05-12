package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.element.TextElementMut;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

/**
 * Some text on screen including simulation time
 * 
 * @author Ondrej Milenovsky
 * */
public class ScreenTextLayer extends AbstractLayer {

    private final ExampleEnvironment env;

    public ScreenTextLayer(ExampleEnvironment env) {
        super("Text");
        this.env = env;
    }

    @Override
    protected void generateFrame() {
        addElement("welcome", new TextElementMut("Welcome", new Color(255, 100, 0), true,
                Align.UPPER_CENTER, new Point3d(), new Font("GothicE", Font.PLAIN, 30)));
        addElement("time", new TextElementMut("Time: " + env.getCurrentTimeMillis(), Color.CYAN, true,
                Align.UPPER_LEFT, new Point3d(0, 50, 0), new Font("arial", Font.PLAIN, 20)));
        addElement("grr", new TextElementMut(">:-)", Color.WHITE, false, new Point2d(0.7, 1),
                new Point3d(0, -10, 0), new Font("arial", Font.PLAIN, 20)));
    }
}
