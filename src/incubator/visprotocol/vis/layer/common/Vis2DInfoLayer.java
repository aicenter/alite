package incubator.visprotocol.vis.layer.common;

import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.Vis2DCommonKeys;
import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.utils.StructUtils;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.TypedLayer;
import incubator.visprotocol.vis.output.Vis2DOutput;

import java.awt.Color;
import java.awt.Font;

import javax.vecmath.Point2d;

/**
 * Layer to show zoom and pos. Cannot pass differ or updater, must be pluged directly to
 * RootPainter. If you want to change something, that is not possible by the param element, inherit
 * new layer, call super.pull() and change what you want.
 * 
 * @author Ondrej Milenovsky
 * */
public class Vis2DInfoLayer extends TypedLayer {

    public static final String ROOT_ID = "root";
    public static final String POSX_ID = "posx";
    public static final String POSY_ID = "posy";
    public static final String ZOOM_ID = "zoom";

    protected final Vis2DOutput vis2d;
    protected final Element params;
    protected double precision;

    /**
     * Updates default setting by the element params. Note that Vis2DCommonPKeys.PRECISION can be
     * used.
     */
    public Vis2DInfoLayer(Vis2DOutput vis2d, Element params, FilterStorage filter) {
        this(vis2d, filter);
        this.params.updateParams(params);
        if (params.containsParameter(Vis2DCommonKeys.PRECISION)) {
            int prec = params.getParameter(Vis2DCommonKeys.PRECISION);
            precision = Math.pow(10, prec);
        }
    }

    /** Upper left position, Arial plain 10, white color */
    public Vis2DInfoLayer(Vis2DOutput vis2d, FilterStorage filter) {
        super(filter);
        this.vis2d = vis2d;
        precision = 1000;
        Element e = new Element("", TextKeys.TYPE);
        setParameter(e, TextKeys.COLOR, Color.WHITE);
        setParameter(e, TextKeys.FONT, new Font("Arial", Font.PLAIN, 10));
        setParameter(e, TextKeys.ALIGN_ON_SCREEN, Align.UPPER_LEFT);
        setParameter(e, TextKeys.CONSTANT_SIZE, true);
        this.params = e;
    }

    @Override
    public Structure pull() {
        Structure ret = new Structure(CommonKeys.STRUCT_PART);
        if (hasType(TextKeys.TYPE)) {
            Folder f = ret.getRoot(ROOT_ID);
            Element e;

            e = f.getElement(POSX_ID, TextKeys.TYPE);
            e.updateParams(params);
            double fontSize = e.getParameter(TextKeys.FONT).getSize();
            setParameter(e, TextKeys.TEXT, "Posx: " + cutNumber(vis2d.getCursorPosition().x));

            e = f.getElement(POSY_ID, TextKeys.TYPE);
            setParameter(e, TextKeys.TEXT, "Posy: " + cutNumber(vis2d.getCursorPosition().y));
            setParameter(e, TextKeys.POS, new Point2d(0, fontSize));

            e = f.getElement(ZOOM_ID, TextKeys.TYPE);
            setParameter(e, TextKeys.TEXT, "Zoom: " + cutNumber(vis2d.getZoomFactor()));
            fontSize = StructUtils.updateValue(e, TextKeys.FONT_SIZE, fontSize);
            setParameter(e, TextKeys.POS, new Point2d(0, fontSize * 2));
        }
        ret.setType(CommonKeys.STRUCT_PART);
        return ret;
    }

    protected double cutNumber(double a) {
        return Math.round(a * precision) / precision;
    }

}
