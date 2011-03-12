package incubator.visprotocol.protocol;

import incubator.visprotocol.structure.Structure;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Just extended StreamProtocol with constructor with file.
 * 
 * @author Ondrej Milenovsky
 * */
public class FileReaderProtocol extends StreamProtocol {

    public FileReaderProtocol(File file) {
        super(init(file), null);
    }

    public FileReaderProtocol(String fileName) {
        this(new File(fileName));
    }

    private static InputStream init(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    @Override
    public void push(Structure struct) {
        throw new RuntimeException("This is only file reader");
    }

}
