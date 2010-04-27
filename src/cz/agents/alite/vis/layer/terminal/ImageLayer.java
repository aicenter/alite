package cz.agents.alite.vis.layer.terminal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cz.agents.alite.vis.element.aggregation.ImageElements;
import cz.agents.alite.vis.layer.AbstractLayer;

public abstract class ImageLayer extends AbstractLayer {

    private final ImageElements imageElements;

    protected ImageLayer(ImageElements imageElements) {
        this.imageElements = imageElements;
    }

    protected ImageElements getImageElements() {
        return imageElements;
    }

    public static BufferedImage loadImage(File file) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

}
