package incubator.visprotocol.protocol;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Pull reads from the stream.
 * 
 * @author Ondrej Milenovsky
 * */
public class StreamInputProtocol implements StructProcessor, StreamProtocol {

    private ObjectInputStream input;

    public StreamInputProtocol(InputStream input) {
        try {
            this.input = new ObjectInputStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Structure pull() {
        try {
            return (Structure) input.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
