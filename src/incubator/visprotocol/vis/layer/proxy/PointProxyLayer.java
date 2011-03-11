package incubator.visprotocol.vis.layer.proxy;

import incubator.visprotocol.vis.element.Points;
import incubator.visprotocol.vis.protocol.Protocol;
import cz.agents.alite.vis.element.aggregation.PointElements;

public class PointProxyLayer implements ProxyLayer {

    private final PointElements pointElements;
    private final String id;

    public PointProxyLayer(PointElements pointElements, String id) {
        this.pointElements = pointElements;
        this.id = id;
    }

    @Override
    public void fillProtocol(Protocol protocol) {
        protocol.push(id, new Points(pointElements));
    }

}
