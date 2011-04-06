package incubator.visprotocol.utils;

import static org.junit.Assert.assertTrue;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StructUtilsTest {

    @Test
    public void testPath() {
        List<String> path1 = StructUtils.parsePath(".lama.a.b.grr");
        List<String> path2 = StructUtils.parsePath("-lama-a-b-grr");
        List<String> path3 = StructUtils.parsePath("PPlamaPPPPPaPbPPPgrrPPPPPP");
        List<String> path4 = StructUtils.parsePath("lama.a.b.grr");
        List<String> path5 = Arrays.asList("lama", "a", "b", "grr");
        assertTrue(path1.equals(path2));
        assertTrue(path1.equals(path3));
        assertTrue(!path1.equals(path4));
        assertTrue(path1.equals(path5));
    }

    @Test
    public void testLeaf() {
        Folder f = new Folder("root");
        Folder leafF = f.getFolder("1").getFolder("2").getFolder("leafF");

        f.getFolder("1").getFolder("2").getFolder("a");
        f.getFolder("1").getFolder("b");
        f.getFolder("c");

        assertTrue(StructUtils.getLeafFolder(f) == leafF);

        Element leafE = leafF.getElement("leafE", "type");
        f.getElement("e1", "type");
        f.getFolder("1").getElement("e2", "type");
        leafF.getElement("aaa", "type");

        // System.out.println(StructUtils.getLeafElement(f).print());
        assertTrue(StructUtils.getLeafElement(f) == leafE);

        leafF.getFolder("zzz");
        assertTrue(StructUtils.getLeafFolder(f) != leafF);
        assertTrue(StructUtils.getLeafElement(f) != leafE);
        assertTrue(StructUtils.getLeafFolder(f) == leafF.getFolder("zzz"));

    }

}
