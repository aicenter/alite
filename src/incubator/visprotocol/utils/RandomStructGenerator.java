package incubator.visprotocol.utils;

import java.util.Random;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;

/**
 * Generates random structure tree. Used is normal distribtion with sigma = mean / 2.
 * 
 * Folders means mean value of subfolders of an folder.
 * 
 * Elements means mean value of subelements of an folder.
 * 
 * Params means mean value of params of an folder/element.
 * 
 * MaxFolders means maximum folders in the structure.
 * 
 * @author Ondrej Milenovsky
 * */
public class RandomStructGenerator {
    public int folders = 3;
    public int elements = 20;
    public int params = 10;
    public int maxFolders = 30;

    private Random rnd = new Random();

    public RandomStructGenerator() {
    }

    public RandomStructGenerator(int folders, int elements, int params, int maxFolders) {
        this.folders = folders;
        this.elements = elements;
        this.params = 10;
        this.maxFolders = maxFolders;
    }

    public void setSeed(long seed) {
        rnd = new Random(seed);
    }

    public Structure next() {
        Structure ret = new Structure(rnd.nextLong());
        generate(ret.getRoot("root" + rnd.nextInt(3)), 1);
        return ret;
    }

    private void generate(Element e, int folderCount) {
        // params
        int ps = genNum(params);
        for (int i = 0; i < ps; i++) {
            e.setParameter("p" + rnd.nextInt(params * 2), rnd.nextInt(256));
        }
        if (!(e instanceof Folder)) {
            return;
        }
        Folder f = (Folder) e;
        // elements
        int es = genNum(elements);
        for (int i = 0; i < es; i++) {
            folderCount++;
            generate(f.getElement("e" + rnd.nextInt(elements * 2), "t" + rnd.nextInt(64)), 0);
        }
        if (folderCount >= maxFolders) {
            return;
        }
        // folders
        int fs = genNum(folders);
        for (int i = 0; i < fs; i++) {
            folderCount++;
            generate(f.getFolder("f" + rnd.nextInt(folders * 2)), folderCount);
        }
    }

    private int genNum(int a) {
        return (int) Math.max(0, rnd.nextGaussian() * a / 2.0 + a);
    }
}
