package cz.agents.alite.vis.layer.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.element.Image;
import cz.agents.alite.vis.element.aggregation.ImageElements;
import cz.agents.alite.vis.element.implemetation.ImageImpl;
import cz.agents.alite.vis.layer.VisLayer;
import cz.agents.alite.vis.layer.terminal.ImageLayer;

public class LogoLayer extends ImageLayer {

    public LogoLayer(final BufferedImage image) {
        super(new ImageElements() {

            @Override
            public Iterable<? extends Image> getImages() {
                return Arrays.asList(new ImageImpl(image));
            }

        });

    }

    @Override
    public void paint(Graphics2D canvas) {
        int imageHeight = 0;
        Dimension dim = Vis.getDrawingDimension();
        for (Image image : getImageElements().getImages()) {
            canvas.drawImage(image.getImage(), dim.width - image.getImage().getWidth() - 25, -10, null);
            imageHeight = image.getImage().getHeight();
        }

        canvas.setColor(Color.BLUE);
        canvas.drawString("Press F1 for help", dim.width - 115, imageHeight);
    }

    @Override
    public String getLayerDescription() {
        String description = "[Logo] The layer shows logo.";
        return buildLayersDescription(description);
    }

    public static VisLayer create(BufferedImage image) {
        return new LogoLayer(image);
    }

    public static VisLayer create(File file) {
        return create(ImageLayer.loadImage(file));
    }

}
