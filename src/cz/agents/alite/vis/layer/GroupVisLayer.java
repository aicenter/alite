package cz.agents.alite.vis.layer;


public interface GroupVisLayer extends VisLayer {

    public void addSubLayer(VisLayer layer);

    public void removeSubLayer(VisLayer layer);

}
