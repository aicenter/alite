package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.StructProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Just extended StreamProtocol with constructor with file.
 * 
 * @author Ondrej Milenovsky
 * */
public class FileWriterProtocol extends StreamOutputProtocol {

    public FileWriterProtocol(File file, StructProcessor... inputs) {
        super(init(file), inputs);
    }

    private static OutputStream init(File file) {
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
