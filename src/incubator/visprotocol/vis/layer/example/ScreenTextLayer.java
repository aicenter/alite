package incubator.visprotocol.vis.layer.example;

import java.awt.Color;
import java.awt.Font;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.TypeParamIdFilter;
import incubator.visprotocol.vis.layer.TypedLayer;

public class ScreenTextLayer extends TypedLayer {

    private final ExampleEnvironment env;

    public ScreenTextLayer(ExampleEnvironment env, TypeParamIdFilter filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure();
        if (hasType(TextKeys.TYPE)) {
            Folder f = struct.getRoot("Undead land").getFolder("Text");
            Element e;
            // time
            e = f.getElement("upper_left", TextKeys.TYPE);
            setParameter(e, TextKeys.COLOR, Color.CYAN);
            setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 20));
            setParameter(e, TextKeys.CONSTANT_SIZE, true);
            setParameter(e, TextKeys.TEXT, "Time: " + env.getTime());
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_LEFT);
            // title
            e = f.getElement("upper_center", TextKeys.TYPE);
            setParameter(e, TextKeys.COLOR, Color.YELLOW);
            setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 30));
            setParameter(e, TextKeys.TEXT, "Welcome to Undead Land");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_CENTER);
            //
            e = f.getElement("upper_right", TextKeys.TYPE);
            setParameter(e, TextKeys.COLOR, Color.CYAN);
            setParameter(e, TextKeys.FONT_SIZE, 10.0);
            setParameter(e, TextKeys.TEXT, "Grrrrr");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_RIGHT);
            //
            e = f.getElement("left_center", TextKeys.TYPE);
            setParameter(e, TextKeys.FONT_STYLE, Font.BOLD);
            setParameter(e, TextKeys.TEXT, "Aaaaaaaa");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LEFT_CENTER);
            //
            e = f.getElement("center", TextKeys.TYPE);
            setParameter(e, TextKeys.COLOR, Color.WHITE);
            setParameter(e, TextKeys.FONT, new Font("gothicE", Font.PLAIN, 100));
            setParameter(e, TextKeys.CONSTANT_SIZE, false);
            setParameter(e, TextKeys.TEXT, ">:-)");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.CENTER);
            //
            e = f.getElement("right_center", TextKeys.TYPE);
            setParameter(e, TextKeys.COLOR, Color.CYAN);
            setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 10));
            setParameter(e, TextKeys.CONSTANT_SIZE, false);
            setParameter(e, TextKeys.TEXT,
                    "I am the death unlimited and death is all I have to give");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.RIGHT_CENTER);
            //
            e = f.getElement("lower_left", TextKeys.TYPE);
            setParameter(e, TextKeys.FONT_STYLE, Font.ITALIC);
            setParameter(e, TextKeys.CONSTANT_SIZE, true);
            setParameter(e, TextKeys.TEXT, "Bla bla bla");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LOWER_LEFT);
            //
            e = f.getElement("lower_center", TextKeys.TYPE);
            setParameter(e, TextKeys.FONT_STYLE, Font.BOLD + Font.ITALIC);
            setParameter(e, TextKeys.FONT_SIZE, 15.4);
            setParameter(e, TextKeys.TEXT, "Line1\nLine2\nLine3 (no next lines)");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LOWER_CENTER);
            //
            e = f.getElement("lower_right", TextKeys.TYPE);
            setParameter(e, TextKeys.COLOR, Color.CYAN);
            setParameter(e, TextKeys.FONT, new Font("arial", Font.PLAIN, 60));
            setParameter(e, TextKeys.CONSTANT_SIZE, false);
            setParameter(e, TextKeys.TEXT, ":-(");
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.LOWER_RIGHT);

        }
        return struct;
    }

}
