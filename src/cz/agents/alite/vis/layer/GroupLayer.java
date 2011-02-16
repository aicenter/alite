package cz.agents.alite.vis.layer;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import cz.agents.alite.vis.Vis;

/**
 * A default implementation of the {@link GroupVisLayer} interface.
 *
 * @author Antonin Komenda
 */
public class GroupLayer extends AbstractLayer implements GroupVisLayer {

    private final LinkedList<VisLayer> subLayers = new LinkedList<VisLayer>();

    private List<VisLayer> sublayersToRemove = new LinkedList<VisLayer>();
    
    protected GroupLayer() {
    }

    public LinkedList<VisLayer> getSubLayers() {
        return subLayers;
    }

    @Override
    public void init(Vis vis) {
        for (VisLayer layer : getSubLayers()) {
            layer.init(vis);
        }
    }

    @Override
    public void addSubLayer(VisLayer layer) {
        subLayers.add(layer);
    }

    @Override
    public void removeSubLayer(VisLayer layer) {
//        synchronized (this) {
//        	subLayers.remove(layer);
//		}
    	sublayersToRemove.add(layer);
    }

    @Override
    public void paint(Graphics2D canvas) {
    	removeSubLayers();
    	List<VisLayer> toIterateThrough = (List<VisLayer>)subLayers.clone();
		for (VisLayer layer : toIterateThrough) {
            layer.paint(canvas);
        }
    }
    
    private void removeSubLayers() {
   		for(VisLayer visLayer: sublayersToRemove){
        	subLayers.remove(visLayer);
        }
   		sublayersToRemove.clear();
	}

    @Override
    public String getLayerDescription() {
        String description = "All sub-layers are always shown:";
        return buildLayersDescription(description);
    }

    public static GroupLayer create() {
        return new GroupLayer();
    }

    protected String buildLayersDescription(String description) {
        if (getHelpOverrideString() != null) {
            return getHelpOverrideString();
        }

        for (VisLayer layer : subLayers) {
            if (!layer.getLayerDescription().isEmpty()) {
                description += "<br/>   " + layer.getLayerDescription().replace("   ", "      ").replace("\n", "\n   ");
            }
        }
        return description;
    }

}
