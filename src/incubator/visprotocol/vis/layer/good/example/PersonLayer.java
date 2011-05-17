package incubator.visprotocol.vis.layer.good.example;

import incubator.visprotocol.creator.TestCreator.ExampleEnvironment;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.good.AbstractLayer;
import incubator.visprotocol.vis.layer.good.element.PointElement;
import incubator.visprotocol.vis.layer.good.element.TextElementMut;

import java.awt.Color;
import java.awt.Font;

import org.apache.commons.math.geometry.Vector3D;

/**
 * Green zombie from the environment
 * 
 * @author Ondrej Milenovsky
 * */
public class PersonLayer extends AbstractLayer {

    private final ExampleEnvironment env;

    public PersonLayer(ExampleEnvironment env) {
        super("People");
        this.env = env;
    }

    @Override
    protected void generateFrame() {
        Vector3D pos = new Vector3D(env.getPersonPosition().getX(), env.getPersonPosition().getY(),
                0);
        addElement(env.getPersonName(), new PointElement(pos, new Color(0, Math.min(255, env
                .getPersonHealth()), 0), 60, false));
        pos = new Vector3D(env.getPersonPosition().getX(), env.getPersonPosition().getY() - 50, 0);
        addElement("-text1", new TextElementMut(env.getPersonName(), pos, Color.YELLOW, false,
                Align.NONE, new Font("GothicE", Font.PLAIN, 40)));
        pos = new Vector3D(env.getPersonPosition().getX(), env.getPersonPosition().getY() + 50, 0);
        addElement("-text2", new TextElementMut("Random...", new Color(160, 80, 0), true,
                Align.NONE, pos, new Font("Arial", Font.ITALIC, 14)));
    }
}
