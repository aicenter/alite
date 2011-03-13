package incubator.visprotocol.structure;

import static org.junit.Assert.assertTrue;
import incubator.visprotocol.utils.RandomStructGenerator;
import incubator.visprotocol.utils.StructUtils;

import org.junit.Test;

/**
 * In very very rare case can fail on NullPointerException (because of random struct generator).
 * 
 * @author Ondrej Milenovsky
 * */
public class FolderTest {

    @Test
    public void testFolders() {
        Folder f = new Folder("root");

        f.getFolder("f1").getFolder("f2").getFolder("f3");
        f.getFolder("f1").getFolder("f4");

        assertTrue(f.containsFolder("f1"));
        assertTrue(!f.containsFolder("f2"));
        assertTrue(f.getFolder("f1").containsFolder("f2"));
        assertTrue(f.getFolder("f1").containsFolder("f4"));
        assertTrue(f.getFolder("f1").getFolder("f2").containsFolder("f3"));

        assertTrue(!f.isEmpty());
        assertTrue(f.getFolder("f1").getFolder("f2").getFolder("f3").isEmpty());
        assertTrue(f.getFolder("f5").isEmpty());

        assertTrue(f.getFolders().size() == 2);

        assertTrue(!f.getFolder("f1").getFolder("f2").isEmpty());
        f.getFolder("f1").getFolder("f2").clear();
        assertTrue(f.getFolder("f1").getFolder("f2").isEmpty());

        f.getFolder("f5").getFolder("f6");
        assertTrue(!f.getFolder("f5").isEmpty());
        f.getFolder("f5").removeFolder("f6");
        assertTrue(f.getFolder("f5").isEmpty());

        f.clear();
        f.getFolder("f1").getFolder("f2");

        Folder f2 = new Folder("root");
        Folder f1a = new Folder("f1");
        f2.addFolder(f1a);
        Folder f2a = new Folder("f2");
        f1a.addFolder(f2a);
        assertTrue(f.equalsDeep(f2));
        
        Folder a1 = new Folder("f1");
        Folder a2 = a1.getFolder("f2");

        Folder b1 = new Folder("f1");
        Folder b2 = b1.getFolder("f2");
        
        assertTrue(a1.getFolder(b2) == a2);
        assertTrue(b1.getFolder(a2) == b2);

    }

    @Test
    public void testElements() {
        Folder f1 = new Folder("root");
        Folder f2 = new Folder("root");

        assertTrue(f1.isEmpty());
        f1.getElement("e1", "type");
        assertTrue(!f1.isEmpty());
        f2.addElement(new Element("e1", "type"));

        assertTrue(f1.equalsDeep(f2));
        f1.getElement("e1").setParameter("kalich", "krve");
        assertTrue(!f1.equalsDeep(f2));

        f1.clear();
        assertTrue(f1.isEmpty());
        f2.removeElement("e1");
        assertTrue(f1.isEmpty());
        assertTrue(f1.equalsDeep(f2));

        Folder a1 = new Folder("f1");
        Element a2 = a1.getElement("f2", "t");

        Folder b1 = new Folder("f1");
        Element b2 = b1.getElement("f2", "t");
        
        assertTrue(a1.getElement(b2) == a2);
        assertTrue(b1.getElement(a2) == b2);        
    }

    @Test
    public void testElements2() {

        Folder f = new Folder("f");
        Element e1 = f.getElement("e1", "type");
        assertTrue(e1.getId().equals("e1"));

        try {
            Element e2 = f.getElement("e2");
            System.out.println(e2.print());
            assertTrue(false);
        } catch (RuntimeException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testEquals() {
        RandomStructGenerator gen = new RandomStructGenerator();
        gen.setSeed(9);
        Folder f1 = gen.next().getRoot();
        gen.setSeed(9);
        Folder f2 = gen.next().getRoot();

        assertTrue(f1.equalsDeep(f2));

        StructUtils.getLeafElement(f1).setParameter("streva", "ven");
        assertTrue(f1.equals(f2));
        assertTrue(!f1.equalsDeep(f2));

        StructUtils.getLeafElement(f2).setParameter("streva", "ven");
        assertTrue(f1.equalsDeep(f2));

    }

    @Test
    public void testDeepCopy() {
        RandomStructGenerator gen = new RandomStructGenerator();
        Folder f1 = gen.next().getRoot();
        Folder f2 = f1.deepCopy();
        assertTrue(f1.equalsDeep(f2));

        StructUtils.getLeafElement(f1).setParameter("zpev", "aaaggghhhrrr");
        assertTrue(!f1.equalsDeep(f2));
        StructUtils.getLeafElement(f2).setParameter("zpev", "aaaggghhhrrr");
        assertTrue(f1.equalsDeep(f2));
    }

}
