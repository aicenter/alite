package incubator.visprotocol.factory;

import incubator.visprotocol.processor.StateGetter;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.processor.updater.DiffUpdater;
import incubator.visprotocol.processor.updater.Differ;
import incubator.visprotocol.processor.updater.MergeUpdater;
import incubator.visprotocol.protocol.FileReaderProtocol;
import incubator.visprotocol.protocol.FileWriterProtocol;
import incubator.visprotocol.protocol.MemoryProtocol;
import incubator.visprotocol.protocol.StreamOutputProtocol;
import incubator.visprotocol.protocol.StreamProtocol;
import incubator.visprotocol.protocol.StreamProtocolCloser;
import incubator.visprotocol.structure.key.Vis2DCommonKeys;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.Vis2DParams;
import incubator.visprotocol.vis.output.painter.TreePainter;
import incubator.visprotocol.vis.output.vis2d.painter.Vis2DBasicPainters;
import incubator.visprotocol.vis.player.Player;
import incubator.visprotocol.vis.player.ui.PlayerControls;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;

/**
 * Factory to create layers, protocol and output. Layers added after creating protocols and outputs
 * will be used.
 * 
 * @author Ondrej Milenovsky
 * */
public class VisFactory {

    private StreamProtocolCloser streamCloser = new StreamProtocolCloser();
    private ArrayList<StructProcessor> layers = new ArrayList<StructProcessor>();
    private FilterStorage filter;

    private StructProcessor lastProtocol;
    
    private StructProcessor layerDiffer;
    private StructProcessor layerMerger;

    private StructProcessor vis2dPainter;

    public VisFactory() {
        filter = new FilterStorage(Vis2DBasicPainters.ELEMENT_TYPES, Vis2DCommonKeys.COMMON_PARAMS);
    }

    // Protocols ////////////

    /** Creates and returns new realtime protocol (=null), rewrites last protocol. */
    public StructProcessor createRealtimeProtocol() {
        lastProtocol = null;
        clearPainters();
        return lastProtocol;
    }

    /** Creates and returns new memory protocol, rewrites last protocol. */
    public StructProcessor createMemoryProcotol() {
        lastProtocol = new MemoryProtocol(getLayerDiffer());
        clearPainters();
        return lastProtocol;
    }

    /**
     * Creates and returns new file writer protocol, rewrites last protocol. No output can be
     * created on this protocol.
     */
    public StructProcessor createFileWriterProtocol(String fileName) {
        lastProtocol = new FileWriterProtocol(new File(fileName), getLayerDiffer());
        streamCloser.addStreamProtocol((StreamProtocol) lastProtocol);
        clearPainters();
        return lastProtocol;
    }

    /** Creates and returns new file reader protocol, rewrites last protocol. */
    public StructProcessor createFileReaderProtocol(String fileName) {
        lastProtocol = new FileReaderProtocol(new File(fileName));
        streamCloser.addStreamProtocol((StreamProtocol) lastProtocol);
        clearPainters();
        return lastProtocol;
    }

    // Updaters/differs ////////////

    /** Returns instance of differ on layers, always one instance. */
    public StructProcessor getLayerDiffer() {
        if (layerDiffer == null) {
            layerDiffer = new Differ(layers);
        }
        return layerDiffer;
    }

    /** Returns instance of merge updater on layers, always one instance. */
    public StructProcessor getLayerMerger() {
        if (layerMerger == null) {
            layerMerger = new MergeUpdater(layers);
        }
        return layerMerger;
    }

    // Outputs ////////////

    /** Creates and returns vis 2D on last protocol. */
    public Vis2DOutput createVis2DOutput(Vis2DParams params) {
        Vis2DOutput ret = new Vis2DOutput(params);
        ret.addInput(getVis2dPainter());
        ret.setStreamCloser(streamCloser);
        return ret;
    }

    /** Creates and returns vis 2D player on last protocol. */
    public Vis2DOutput createVis2DPlayerOutput(Vis2DParams params) {
        Vis2DOutput ret = new Vis2DOutput(params);
        Player player = new Player(lastProtocol);
        TreePainter painter = new TreePainter(new StateGetter(player));
        ret.addPanel(new PlayerControls(player), BorderLayout.SOUTH);
        ret.addInput(painter);
        ret.setStreamCloser(streamCloser);
        return ret;
    }
    
    // Painters ////////////

    /**
     * Returns instance of tree painter for vis 3D on last protocol, always one instance for last
     * protocol, new instance for new protocol.
     */
    public StructProcessor getVis2dPainter() {
        if (vis2dPainter == null) {
            if (lastProtocol == null) {
                vis2dPainter = new TreePainter(new MergeUpdater(layers));
            } else {
                if (lastProtocol instanceof StreamOutputProtocol) {
                    throw new RuntimeException("Painter cannot be created on output protocol.");
                }
                vis2dPainter = new TreePainter(new DiffUpdater(lastProtocol));
            }
        }
        return vis2dPainter;
    }

    // Layers ////////////

    /** adds layer which generates elements from environment */
    public void addLayer(StructProcessor layer) {
        layers.add(layer);
    }

    /** adds layer which does not depend environment to last output */
    public void addOutputLayer(StructProcessor layer) {
    }

    /** adds layer which does not depend environment to all output */
    public void addOutputLayerAll(StructProcessor layer) {
    }

    // ////////

    public FilterStorage getFilter() {
        return filter;
    }
    
    private void clearPainters() {
        vis2dPainter = null;
    }

}
