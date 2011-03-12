package incubator.visprotocol.utils;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StructUtilsTest {

    @Test
    public void testPath() {
        List<String> path1 = StructUtils.getFolderIds(".lama.a.b.grr");
        List<String> path2 = StructUtils.getFolderIds("-lama-a-b-grr");
        List<String> path3 = StructUtils.getFolderIds("PPlamaPPPPPaPbPPPgrrPPPPPP");
        List<String> path4 = StructUtils.getFolderIds("lama.a.b.grr");
        List<String> path5 = Arrays.asList("lama", "a", "b", "grr");
        assertTrue(path1.equals(path2));
        assertTrue(path1.equals(path3));
        assertTrue(!path1.equals(path4));
        assertTrue(path1.equals(path5));
    }
    
}
