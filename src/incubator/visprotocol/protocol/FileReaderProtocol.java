package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.StateHolder;
import incubator.visprotocol.structure.Structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Just extended StreamInputProtocol with constructor with file.
 * 
 * @author Ondrej Milenovsky
 * */
public class FileReaderProtocol extends StreamInputProtocol implements StateHolder {

    private Structure struct;

    public FileReaderProtocol(File file) {
        super(init(file));
    }

    private static InputStream init(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Structure pull() {
        struct = super.pull();
        return struct;
    }

    @Override
    public Structure getState() {
        return struct;
    }
}
