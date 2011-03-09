package incubator.visprotocol.vis.layer.proxy;

import incubator.visprotocol.vis.protocol.Protocol;

import java.util.LinkedList;
import java.util.List;

// TODO: new GroupProxyLayer has to be created, only it can have subLayers
// TODO: RootProxyLayer should extend GroupProxyLayer
public class RootProxyLayer implements ProxyLayer {

    // TODO: private + getters/setters
    public final List<ProxyLayer> subLayers = new LinkedList<ProxyLayer>();

    @Override
    public void fillProtocol(Protocol protocol) {
        for (ProxyLayer layer : subLayers) {
            layer.fillProtocol(protocol);
        }
    }

}
