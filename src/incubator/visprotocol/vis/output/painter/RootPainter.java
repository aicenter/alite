package incubator.visprotocol.vis.output.painter;

import java.util.LinkedList;
import java.util.List;

import incubator.visprotocol.vis.protocol.Protocol;

// TODO: new GroupPainter has to be created, only it can have subPainters
//TODO: RootPainter should extend GroupPainter
public class RootPainter implements Painter {

    public List<Painter> subPainters = new LinkedList<Painter>();

    @Override
    public void paint(Protocol protocol) {
        for (Painter painter : subPainters) {
            painter.paint(protocol);
        }
    }

}
