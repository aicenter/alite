package incubator.visprotocol.structure;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.utils.RandomStructGenerator;
import incubator.visprotocol.utils.StructUtils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StructureTest {

    @Test
    public void test1() {
        Structure s = new Structure();
        // folder test
        Folder f1 = s.getRoot("f1");
        Folder f2 = f1.getFolder("f2");
        Folder f3 = f1.getFolder("f3");
        assertTrue(f1.getFolder("f2") == f2);
        assertTrue(f1.getFolder(f2) == f2);
        assertTrue(f1.containsFolder("f3"));
        assertTrue(f1.containsFolder(f3));
        assertTrue(!f1.containsFolder("f4"));
        assertTrue(f2.isEmpty());
        assertTrue(!f1.isEmpty());

        // element test
        Element e1 = f3.getElement("e1", "type1");
        assertTrue(!f3.isEmpty());
        assertTrue(f3.getElement(e1) == e1);
        assertTrue(f3.getElement("e1", "type1") == e1);

        e1.setParameter("p1", 3);
        e1.setParameter("p3", "lama");
        e1.setParameter("p2", "grr");
        e1.setParameter("p3", 5.4);
        assertTrue(!e1.isEmpty());
        assertTrue(e1.getParameter("p1", Integer.class) == 3);
        assertTrue(e1.getParameter("p2", String.class).equals("grr"));
        assertTrue(e1.getParameter("p3", Double.class) == 5.4);

        Structure s2 = new Structure();
        // folder equal test
        Folder f1a = s2.getRoot("neco").getFolder("f1");
        f1a.getFolder("f3");
        assertTrue(f1a.equals(f1));
        assertTrue(!f1a.equals(f2));
        Folder f3a = new Folder("f3");
        assertTrue(f3a.equals(f3));

        f3.getFolder("f4");

        System.out.println(s.print());
        System.out.println(s2.print());
    }

    @Test
    public void test2() {
        Structure s1 = new Structure(666L);
        Structure s2 = new Structure(66L);
        assertTrue(s1.isEmpty());
        s1.getRoot("f1");
        assertTrue(!s1.isEmpty());
        assertTrue(!s1.equals(s2));
        s2.setTimeStamp(666L);
        assertTrue(s1.equals(s2));
        assertTrue(!s1.equalsDeep(s2));
        s2.getRoot("f1");
        assertTrue(s1.equalsDeep(s2));
        s2.clear();
        assertTrue(s2.isEmpty());

    }

    @Test
    public void testEquals() {
        RandomStructGenerator gen = new RandomStructGenerator();
        gen.setSeed(-666);
        Structure s1 = gen.next();
        gen.setSeed(-666);
        Structure s2 = gen.next();
        assertTrue(s1.equals(s2));
        assertTrue(s1.equalsDeep(s2));
        Element e = StructUtils.getLeaf(s1);
        e.setParameter("vemeno", "doji");
        assertTrue(s1.equals(s2));
        assertTrue(!s1.equalsDeep(s2));
        e = StructUtils.getLeaf(s2);
        e.setParameter("vemeno", "doji");
        assertTrue(s1.equalsDeep(s2));
        
        gen.setSeed(1);
        s1 = gen.next();
        gen.setSeed(2);
        s2 = gen.next();
        assertTrue(!s1.equalsDeep(s2));
    }

    @Test
    public void testDeepCopy() {
        RandomStructGenerator gen = new RandomStructGenerator();
        Structure s1 = gen.next();
        Structure s2 = s1.deepCopy();
        assertTrue(s1.equalsDeep(s2));
        Element e = StructUtils.getLeaf(s1);
        e.setParameter("vemeno", "doji");
        assertTrue(!s1.equalsDeep(s2));
        e.removeParameter("vemeno");
        
        assertTrue(s1.getRoot() != s2.getRoot());
        for(Folder f: s1.getRoot().getFolders()) {
            Folder f2 = s2.getRoot().getFolder(f);
            assertTrue(f != f2);
            assertTrue(f.equalsDeep(f2));
        }
    }

}
