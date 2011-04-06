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

    public FillColorLayer(Color color, String path, FilterStorage filter) {
        this(color, path, DEFAULT_ID, filter);
    }

    public FillColorLayer(Color color, FilterStorage filter, String... path) {
        this(color, DEFAULT_ID, filter, path);
    }

    public FillColorLayer(Color color, String path, String id, FilterStorage filter) {
        super(filter);
        this.color = color;
        this.path = StructUtils.parsePath(path);
    }

    public FillColorLayer(Color color, String id, FilterStorage filter, String... path) {
        super(filter);
        this.color = color;
        this.path = Arrays.asList(path);
    }

    // private Structure generateStruct(Color color, Structure struct, String id) {
    // Folder f = StructUtils.getLeafFolder(struct);
    // setParameter(f, CommonKeys.NOT_CHANGE, true);
    // Element e = f.getElement(id, FillColorKeys.TYPE);
    // setParameter(e, FillColorKeys.COLOR, color);
    // setParameter(e, CommonKeys.NOT_CHANGE, true);
    // struct.setType(CommonKeys.STRUCT_PART);
    // return struct;
    // }

    // @Override
    // public Structure pull() {
    // if (hasType(FillColorKeys.TYPE) && typeHasParam(FillColorKeys.TYPE, FillColorKeys.COLOR)) {
    // return struct.deepCopy();
    // }
    // return new Structure(CommonKeys.STRUCT_PART);
    // }

    @Override
    protected void generateFrame() {
        changeFolder(path);
        setStaticFolder();
        addElement(DEFAULT_ID, new FillColorElement(color));
    }
}
