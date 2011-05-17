package incubator.visprotocol.vis.layer.complex.terminal;

import incubator.visprotocol.structure.key.struct.Align;
import incubator.visprotocol.vis.layer.complex.AbstractLayer;
import incubator.visprotocol.vis.layer.complex.element.TextElementMut;
import incubator.visprotocol.vis.output.Vis2DOutput;

import java.awt.Color;
import java.awt.Font;

import org.apache.commons.math.geometry.Vector3D;


/**
 * Layer to show zoom and pos. Cannot pass differ or updater, must be pluged directly to
 * RootPainter. If you want to change something, that is not possible by the param element, inherit
 * new layer, call super.pull() and change what you want.
 * 
 * @author Ondrej Milenovsky
 * */
public class Vis2DInfoLayer extends AbstractLayer {

    public static final String ROOT_ID = "root";
    public static final String POSX_ID = "posx";
    public static final String POSY_ID = "posy";
    public static final String ZOOM_ID = "zoom";

    protected final Vis2DOutput vis2d;
    protected final TextElementMut params;
    protected double precision;

    /**
     * Updates default setting by the element params. Note that Vis2DCommonPKeys.PRECISION can be
     * used.
     */
    public Vis2DInfoLayer(Vis2DOutput vis2d, TextElementMut params, int precision) {
        this(vis2d);
        updateParams(params);
        this.precision = Math.pow(10, precision);
    }

    /** Upper left position, Arial plain 10, white color */
    public Vis2DInfoLayer(Vis2DOutput vis2d) {
        super(ROOT_ID);
        this.vis2d = vis2d;
        precision = 1000;
        params = new TextElementMut(null, Color.WHITE, true, Align.UPPER_LEFT, new Vector3D(0, 0, 0),
                new Font("Arial", Font.PLAIN, 10));
    }

    private void updateParams(TextElementMut par) {
        if (par.color != null) {
            params.color = par.color;
        }
        if (par.font != null) {
            params.font = par.font;
        }
        if (par.alignOnScreen != null) {
            params.alignOnScreen = par.alignOnScreen;
        }
        if (par.alignRatio != null) {
            params.alignRatio = par.alignRatio;
        }
        if (par.pos != null) {
            params.pos = par.pos;
        }
        if (par.fontName != null) {
            params.fontName = par.fontName;
        }
        if (par.fontSize > 0) {
            params.fontSize = par.fontSize;
        }
        if (par.fontStyle >= 0) {
            params.fontStyle = par.fontStyle;
        }
        params.constantSize = par.constantSize;
    }

    @Override
    protected void generateFrame() {
        TextElementMut e;
        double fontSize = params.fontSize;
        if (fontSize <= 0) {
            fontSize = params.font.getSize();
        }

        e = params.copy();
        e.text = "Posx: " + cutNumber(vis2d.getCursorPosition().x);
        addElement(POSX_ID, e);

        e = params.copy();
        e.text = "Posy: " + cutNumber(vis2d.getCursorPosition().y);
        e.pos = new Vector3D(e.pos.getX(), e.pos.getY() + fontSize, e.pos.getZ());
        addElement(POSY_ID, e);

        e = params.copy();
        e.text = "Zoom: " + cutNumber(vis2d.getZoomFactor());
        e.pos = new Vector3D(e.pos.getX(), e.pos.getY() + fontSize * 2, e.pos.getZ());
        addElement(ZOOM_ID, e);
    }

    protected double cutNumber(double a) {
        return Math.round(a * precision) / precision;
    }

}
