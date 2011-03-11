package incubator.visprotocol.vis.output.painter;

import java.util.LinkedList;
import java.util.List;

import incubator.visprotocol.vis.protocol.Protocol;

public class RootPainter implements GroupPainter {

    private final List<Painter> subPainters;

    public RootPainter() {
        subPainters = new LinkedList<Painter>();
    }

    @Override
    public void addPainter(Painter painter) {
        subPainters.add(painter);
    }

    @Override
    public void paint(Protocol protocol) {
        for (Painter painter : subPainters) {
            painter.paint(protocol);
        }
    }

}
