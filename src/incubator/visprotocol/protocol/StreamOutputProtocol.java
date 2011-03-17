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
public class StreamOutputProtocol extends MultipleInputProcessor {

    private ObjectOutputStream output;

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
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void push(Structure newPart) {
        try {
            output.writeObject(newPart);
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
