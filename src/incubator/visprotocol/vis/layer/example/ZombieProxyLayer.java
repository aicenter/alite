package incubator.visprotocol.vis.layer.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.vis.layer.TypeParamIdFilter;
import incubator.visprotocol.vis.layer.TypedLayer;

import java.awt.Color;

import javax.vecmath.Point2d;

public class ZombieProxyLayer extends TypedLayer {

    private final ExampleEnvironment env;

    public ZombieProxyLayer(ExampleEnvironment env, TypeParamIdFilter filter) {
        super(filter);
        this.env = env;
    }

    @Override
    public Structure pull() {
        Structure struct = new Structure();
        Element e = struct.getRoot("Undead land").getFolder("Zombies").getElement(
                env.getPersonName(), PointKeys.TYPE);
        Point2d pos = new Point2d(env.getPersonPosition().x, env.getPersonPosition().y);
        e.setParameter(PointKeys.CENTER, pos);
        e.setParameter(PointKeys.SIZE, 60.0);
        e.setParameter(PointKeys.COLOR, new Color(0, Math.min(255, env.getPersonHealth()), 0));
        e.setParameter(PointKeys.CONSTANT_SIZE, false);
        return struct;
    }

}
