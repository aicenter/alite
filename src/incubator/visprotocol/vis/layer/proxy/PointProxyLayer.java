package incubator.visprotocol.vis.layer.proxy;

import incubator.visprotocol.vis.element.Points;
import incubator.visprotocol.vis.protocol.Protocol;
import cz.agents.alite.vis.element.aggregation.PointElements;

public class PointProxyLayer implements ProxyLayer {

    private PointElements pointElements;

    public PointProxyLayer(PointElements pointElements) {
	this.pointElements = pointElements;
    }

    @Override
    public void fillProtocol(Protocol protocol) {
	protocol.push(new Points(pointElements));
    }

}
