package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.element.FillColorElement;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * This layer wills whole canvas by specified color, used as background.
 * 
 * @author Ondrej Milenovsky
 * */
public class FillColorLayer extends AbstractLayer {

    public static final String DEFAULT_ID = "Background";
    private final List<String> path;
    private final Color color;

    /**
     * leaf folder of the path will be set as static, so do not use it elsewhere for dynamic
     * elements
     */
    public FillColorLayer(Color color, String path, FilterStorage filter) {
        this(color, path, DEFAULT_ID, filter);
    }

    /**
     * leaf folder of the path will be set as static, so do not use it elsewhere for dynamic
     * elements
     */
    public FillColorLayer(Color color, FilterStorage filter, String... path) {
        this(color, DEFAULT_ID, filter, path);
    }

    /**
     * leaf folder of the path will be set as static, so do not use it elsewhere for dynamic
     * elements
     */
    public FillColorLayer(Color color, String path, String id, FilterStorage filter) {
        super(filter, true);
        this.color = color;
        this.path = StructUtils.parsePath(path);
    }

    /**
     * leaf folder of the path will be set as static, so do not use it elsewhere for dynamic
     * elements
     */
    public FillColorLayer(Color color, String id, FilterStorage filter, String... path) {
        super(filter, true);
        this.color = color;
        this.path = Arrays.asList(path);
    }

    @Override
    protected void generateFrame() {
        changeFolder(path);
        addElement(DEFAULT_ID, new FillColorElement(color));
    }
}
