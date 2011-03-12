package incubator.visprotocol.protocol;

import incubator.visprotocol.structure.Structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Just extended StreamProtocol with constructor with file.
 * 
 * @author Ondrej Milenovsky
 * */
public class FileWriterProtocol extends StreamProtocol {

    public FileWriterProtocol(File file) {
        super(null, init(file));
    }

    public FileWriterProtocol(String fileName) {
        this(new File(fileName));
    }

    private static OutputStream init(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Structure pull() {
        throw new RuntimeException("This is only file writer");
    }

}
