package incubator.visprotocol.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import incubator.visprotocol.structure.Structure;

public class StreamProtocol implements Protocol {

    private ObjectInputStream input;
    private ObjectOutputStream output;

    public StreamProtocol(InputStream input, OutputStream output) {
        if (input != null) {
            try {
                this.input = new ObjectInputStream(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (output != null) {
            try {
                this.output = new ObjectOutputStream(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeInput() {
        try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeOutput() {
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (input != null) {
            closeInput();
        }
        if (output != null) {
            closeOutput();
        }
    }

    @Override
    public void push(Structure newPart) {
        if (output == null) {
            throw new RuntimeException("Only input stream");
        }
        try {
            output.writeObject(newPart);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Structure pull() {
        if (input == null) {
            throw new RuntimeException("Only output stream");
        }
        try {
            return (Structure) input.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
