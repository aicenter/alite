package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;

public class ZombieProxyLayer extends TypedLayer {

    private final ExampleEnvironment env;

    public ZombieProxyLayer(ExampleEnvironment env, FilterStorage filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure();
        Folder f = struct.getRoot("Undead land").getFolder("Zombies");
        if (hasType(PointKeys.TYPE)) {
            Element e = f.getElement(env.getPersonName(), PointKeys.TYPE);
            Point2d pos = new Point2d(env.getPersonPosition().x, env.getPersonPosition().y);
            setParameter(e, PointKeys.CENTER, pos);
            setParameter(e, PointKeys.SIZE, 60.0);
            setParameter(e, PointKeys.COLOR, new Color(0, Math.min(255, env.getPersonHealth()), 0));
            setParameter(e, PointKeys.CONSTANT_SIZE, false);
        }
        if (hasType(TextKeys.TYPE)) {
            Element e = f.getElement(env.getPersonName() + "-text", TextKeys.TYPE);
            Point2d pos = new Point2d(env.getPersonPosition().x, env.getPersonPosition().y - 50);
            setParameter(e, TextKeys.CENTER, pos);
            setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.NONE);
            setParameter(e, TextKeys.FONT, new Font("GothicE", Font.PLAIN, 40));
            setParameter(e, TextKeys.CONSTANT_SIZE, false);
            setParameter(e, TextKeys.COLOR, Color.YELLOW);
            setParameter(e, TextKeys.TEXT, "Brainzzz");

            e = f.getElement(env.getPersonName() + "-text2", TextKeys.TYPE);
            pos = new Point2d(env.getPersonPosition().x, env.getPersonPosition().y + 50);
            setParameter(e, TextKeys.POS, pos);
            setParameter(e, TextKeys.FONT, new Font("Arial", Font.ITALIC, 14));
            setParameter(e, TextKeys.COLOR, new Color(160, 80, 0));
            setParameter(e, TextKeys.CONSTANT_SIZE, true);
            setParameter(e, TextKeys.TEXT, "Fart...");
        }
        return struct;
    }

}
