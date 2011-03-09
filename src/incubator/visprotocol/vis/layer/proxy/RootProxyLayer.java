package incubator.visprotocol.vis.layer.proxy;

import incubator.visprotocol.vis.protocol.Protocol;

import java.util.LinkedList;
import java.util.List;

public class RootProxyLayer implements GroupProxyLayer {

    private final List<ProxyLayer> subLayers;

    public RootProxyLayer() {
	subLayers = new LinkedList<ProxyLayer>();
    }

    @Override
    public void fillProtocol(Protocol protocol) {
	for (ProxyLayer layer : subLayers) {
	    layer.fillProtocol(protocol);
	}
    }

    @Override
    public void addLayer(ProxyLayer layer) {
	subLayers.add(layer);
    }

}
