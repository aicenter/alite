package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.utils.ProcessorUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Just extended StreamProtocol with constructor with file.
 * 
 * @author Ondrej Milenovsky
 * */
public class FileWriterProtocol extends StreamOutputProtocol {

    public FileWriterProtocol(File file, StructProcessor... inputs) {
        this(file, ProcessorUtils.asList(inputs));
    }

    public FileWriterProtocol(File file, List<StructProcessor> inputs) {
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
