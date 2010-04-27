package cz.agents.alite.vis.layer;

import java.awt.Graphics2D;

import cz.agents.alite.vis.Vis;


public interface VisLayer {

    public void init(Vis vis);

    public void paint(Graphics2D canvas);

    public String getLayerDescription();

    public void setHelpOverrideString(String string);

}
