package incubator.visprotocol.vis.layer.complex.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.complex.AbstractLayer;
import incubator.visprotocol.vis.layer.complex.element.TextElementMut;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;

import org.apache.commons.math.geometry.Vector3D;

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
                Align.UPPER_CENTER, new Vector3D(0, 0, 0), new Font("GothicE", Font.PLAIN, 30)));
        addElement("time", new TextElementMut("Time: " + env.getCurrentTimeMillis(), Color.CYAN,
                true, Align.UPPER_LEFT, new Vector3D(0, 50, 0), new Font("arial", Font.PLAIN, 20)));
        addElement("grr", new TextElementMut(">:-)", Color.WHITE, false, new Point2d(0.7, 1),
                new Vector3D(0, -10, 0), new Font("arial", Font.PLAIN, 20)));
    }
}
