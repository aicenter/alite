package incubator.visprotocol.protocol;

import java.util.ArrayList;
import java.util.List;

/**
 * Close all streams.
 * 
 * @author Ondrej Milenovsky
 * */
public class StreamProtocolCloser implements StreamProtocol {

    private final List<StreamProtocol> protocols;

    public StreamProtocolCloser() {
        protocols = new ArrayList<StreamProtocol>();
    }

    public void addStreamProtocol(StreamProtocol p) {
        protocols.add(p);
    }

    @Override
    public void close() {
        for (StreamProtocol p : protocols) {
            try {
                p.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
