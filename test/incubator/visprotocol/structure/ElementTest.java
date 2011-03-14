package incubator.visprotocol.structure;

import static org.junit.Assert.assertTrue;

import java.awt.Color;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.PointKeys;
import incubator.visprotocol.utils.RandomStructGenerator;

import org.junit.Test;

public class ElementTest {

    private int tests = 100;

    @Test
    public void testParams() {
        Element e = new Element("streva", "zvratky");
        assertTrue(e.isEmpty());
        assertTrue(e.getId().equals("streva") && e.getType().equals("zvratky"));

        e.setParameter("mozky", "vyhrezly");
        assertTrue(e.getParameter("mozky", String.class).equals("vyhrezly"));
        assertTrue(!e.isEmpty());

        e.setParameter(PointKeys.COLOR, new Color(1, 2, 3));
        assertTrue(e.getParameter(PointKeys.COLOR).equals(new Color(1, 2, 3)));

        e.removeParameter("mozky");
        assertTrue(!e.containsParameter("mozky"));

        e.setParameter(PointKeys.SIZE, 20.0);
        assertTrue(e.getParameter(PointKeys.SIZE) == 20.0);

        e.setParameter(PointKeys.SIZE, 30.0);
        assertTrue(e.parameterEqual(PointKeys.SIZE, 30.0));

        e.clear();
        assertTrue(e.isEmpty());

    }

    @Test
    public void testEquals() {
        RandomStructGenerator gen = new RandomStructGenerator(0, tests, 50, 0);
        gen.setSeed(666);
        Structure s1 = gen.next();
        gen.setSeed(666);
        Structure s2 = gen.next();

        assertTrue(s1.equals(s2));
        assertTrue(s1.equalsDeep(s2));

        for (Element e : s1.getRoot().getElements()) {
            Element e2 = s2.getRoot().getElement(e);
            assertTrue(e.equalsDeep(e2));
            if (Math.random() < 0.5) {
                e.clear();
                assertTrue(e2.isEmpty() == e.equalsDeep(e2));
            }
            assertTrue(e.equals(e2));
        }
    }

    @Test
    public void testDeepCopy() {
        RandomStructGenerator gen = new RandomStructGenerator(0, tests, 50, 0);
        Structure s1 = gen.next();
        Structure s2 = s1.deepCopy();
        assertTrue(s1.equalsDeep(s2));
        for (Element e : s1.getRoot().getElements()) {
            assertTrue(e.equalsDeep(s2.getRoot().getElement(e)));
        }
    }

}
