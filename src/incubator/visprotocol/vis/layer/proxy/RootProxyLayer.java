package incubator.visprotocol.vis.layer.proxy;

import incubator.visprotocol.StructProcessor;

import java.util.LinkedList;
import java.util.List;

/**
 * All proxies send data from simulation in structures to the differ. The last layer will generate
 * common data to the structure, for now it is only simulation time.
 * 
 * @author Ondrej Milenovsky
 * */
public class RootProxyLayer implements GroupProxyLayer {

    private final List<ProxyLayer> subLayers;

    public RootProxyLayer() {
        subLayers = new LinkedList<ProxyLayer>();
    }

    @Override
    public void fillProcessor(StructProcessor processor) {
        for (ProxyLayer layer : subLayers) {
            layer.fillProcessor(processor);
        }
    }

    @Override
    public void addLayer(ProxyLayer layer) {
        subLayers.add(layer);
    }

}
