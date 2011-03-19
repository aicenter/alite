package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Pull writes to the stream and returns null.
 * 
 * @author Ondrej Milenovsky
 * */
public class StreamOutputProtocol extends MultipleInputProcessor implements StreamProtocol {

    private ObjectOutputStream output;
    private int flushInterval = 1000;
    private int lastFlush = -1;

    public StreamOutputProtocol(OutputStream output, StructProcessor... inputs) {
        this(output, Arrays.asList(inputs));
    }

    public StreamOutputProtocol(OutputStream output, List<StructProcessor> inputs) {
        super(inputs);
        try {
            this.output = new ObjectOutputStream(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            output.flush();
            output.close();
            output = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void push(Structure newPart) {
        if(output == null) {
            return;
        }
        try {
            output.writeObject(newPart);
            if (lastFlush < 0) {
                lastFlush = (int)System.currentTimeMillis();
            } else if (lastFlush + flushInterval < System.currentTimeMillis()) {
                output.flush();
                lastFlush = (int)System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** write to the output stream and return null */
    @Override
    public Structure pull() {
        for (StructProcessor pr : getInputs()) {
            push(pr.pull());
        }
        return null;
    }

}
