package incubator.visprotocol.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Just extended StreamInputProtocol with constructor with file.
 * 
 * @author Ondrej Milenovsky
 * */
public class FileReaderProtocol extends StreamInputProtocol {

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
}
