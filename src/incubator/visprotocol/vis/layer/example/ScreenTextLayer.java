package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.element.TextElement;

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

    public ScreenTextLayer(ExampleEnvironment env, FilterStorage filter) {
        super(filter);
        this.env = env;
    }

    // @Override
    // public Structure pull() {
    // Structure struct = new Structure(CommonKeys.STRUCT_PART);
    // if (hasType(TextKeys.TYPE)) {
    // Folder f = struct.getRoot("World").getFolder("Text");
    // Element e;
    // // time
    // e = f.getElement("upper_left", TextKeys.TYPE);
    // setParameter(e, TextKeys.COLOR, Color.CYAN);
    // setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 20));
    // setParameter(e, TextKeys.CONSTANT_SIZE, true);
    // setParameter(e, TextKeys.TEXT, "Time: " + env.getCurrentTimeMillis());
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_LEFT);
    // setParameter(e, TextKeys.POS, new Point3d(0, 50, 0));
    // // title
    // e = f.getElement("upper_center", TextKeys.TYPE);
    // setParameter(e, TextKeys.COLOR, new Color(255, 100, 0));
    // setParameter(e, TextKeys.FONT, new Font("GothicE", Font.PLAIN, 30));
    // setParameter(e, TextKeys.TEXT, "Welcome");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_CENTER);
    // //
    // e = f.getElement("upper_right", TextKeys.TYPE);
    // setParameter(e, TextKeys.FONT_NAME, "arial");
    // setParameter(e, TextKeys.COLOR, Color.CYAN);
    // setParameter(e, TextKeys.FONT_SIZE, 10.0);
    // setParameter(e, TextKeys.TEXT, "Text1");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_RIGHT);
    // //
    // e = f.getElement("left_center", TextKeys.TYPE);
    // setParameter(e, TextKeys.FONT_STYLE, Font.BOLD);
    // setParameter(e, TextKeys.TEXT, "Text2");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.CENTER_LEFT);
    // //
    // // e = f.getElement("center", TextKeys.TYPE);
    // // setParameter(e, TextKeys.COLOR, Color.WHITE);
    // // setParameter(e, TextKeys.FONT, new Font("gothicE", Font.PLAIN, 100));
    // // setParameter(e, TextKeys.CONSTANT_SIZE, false);
    // // setParameter(e, TextKeys.TEXT, ">:-)");
    // // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.CENTER);
    // //
    // e = f.getElement("right_center1", TextKeys.TYPE);
    // setParameter(e, TextKeys.COLOR, Color.CYAN);
    // setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 10));
    // setParameter(e, TextKeys.CONSTANT_SIZE, false);
    // setParameter(e, TextKeys.TEXT, "Example environment");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.CENTER_RIGHT);
    //
    // e = f.getElement("right_center2", TextKeys.TYPE);
    // setParameter(e, TextKeys.TEXT, "Using visprotocol");
    // setParameter(e, TextKeys.POS, new Point3d(0, 11, 0));
    // //
    // e = f.getElement("lower_left", TextKeys.TYPE);
    // setParameter(e, TextKeys.FONT_STYLE, Font.ITALIC);
    // setParameter(e, TextKeys.POS, new Point3d(20, -20, 0));
    // setParameter(e, TextKeys.CONSTANT_SIZE, true);
    // setParameter(e, TextKeys.TEXT, "Text3");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LOWER_LEFT);
    // //
    // e = f.getElement("lower_center", TextKeys.TYPE);
    // setParameter(e, TextKeys.FONT_STYLE, Font.BOLD + Font.ITALIC);
    // setParameter(e, TextKeys.FONT_SIZE, 15.4);
    // setParameter(e, TextKeys.TEXT, "Line1\nLine2\nLine3 (no next lines)");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LOWER_CENTER);
    // //
    // e = f.getElement("lower_right", TextKeys.TYPE);
    // setParameter(e, TextKeys.COLOR, Color.CYAN);
    // setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 60));
    // setParameter(e, TextKeys.CONSTANT_SIZE, false);
    // setParameter(e, TextKeys.TEXT, ":-(");
    // setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LOWER_RIGHT);
    //
    // }
    // return struct;
    // }

    @Override
    protected void generateFrame() {
        changeFolder("World", "Text");
        addElement("welcome", new TextElement("Welcome", new Color(255, 100, 0), true,
                Align.UPPER_CENTER, new Point3d(), new Font("GothicE", Font.PLAIN, 30)));
        addElement("time", new TextElement("Time: " + env.getCurrentTimeMillis(), Color.CYAN, true,
                Align.UPPER_LEFT, new Point3d(0, 50, 0), new Font("arial", Font.PLAIN, 20)));
        addElement("grr", new TextElement(">:-)", Color.WHITE, false, new Point2d(0.7, 1),
                new Point3d(0, -10, 0), new Font("arial", Font.PLAIN, 20)));
    }
}
