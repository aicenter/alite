package incubator.visprotocol.structprocessor;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.processor.updater.MergeUpdater;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.utils.RandomStructGenerator;

import org.junit.Test;

public class DiffMergeTest {

    public static int count = 100;

    /** generate random struct, split to parts and merge the parts */
    @Test
    public void testMerge() {
        RandomStructGenerator gen = new RandomStructGenerator(6, 10, 3, 30);
        for (int i = 0; i < count; i++) {
            Structure s1 = gen.next();
            if (s1.getRoot().getFolders().size() == 0) {
                continue;
            }
            s1.setType(CommonKeys.STRUCT_COMPLETE);
            Folder root = s1.getRoot();
            root.clearElements();
            root.clearParams();
            List<StructProcessor> parts = new ArrayList<StructProcessor>(root.getFolders().size());
            // System.out.println(s1.print());
            for (Folder f : root.getFolders()) {
                parts.add(new StructHolder(s1, f));
            }
            MergeUpdater merger = new MergeUpdater(parts);
            Structure s2 = merger.pull();
            assertTrue(s1.equalsDeep(s2));
        }
    }

    /** generate random struct, split to parts, diff and update */
    @Test
    public void testDiff() {
        // TODO
    }
    
    public static class StructHolder implements StructProcessor {
        private final Structure struct;

        public StructHolder(Structure s, Folder f) {
            struct = new Structure(s);
            struct.getRoot(s.getRoot()).addFolder(f);
        }

        @Override
        public Structure pull() {
            return struct.deepCopy();
        }
    }

}
