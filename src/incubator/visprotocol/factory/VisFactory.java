package incubator.visprotocol.factory;

import incubator.visprotocol.processor.MultipleInputProcessor;
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
import incubator.visprotocol.vis.layer.AbstractLayer;
import incubator.visprotocol.vis.layer.FilterStorage;
import incubator.visprotocol.vis.layer.common.TimeHolder;
import incubator.visprotocol.vis.layer.common.TimeLayer;
import incubator.visprotocol.vis.output.Vis2DOutput;
import incubator.visprotocol.vis.output.Vis2DParams;
import incubator.visprotocol.vis.output.painter.TreePainter;
import incubator.visprotocol.vis.output.vis2d.Vis2DBasicTransformators;
import incubator.visprotocol.vis.output.vis2d.painter.Vis2DBasicPainters;
import incubator.visprotocol.vis.player.Player;
import incubator.visprotocol.vis.player.ui.PlayerControls;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Factory to create layers, protocol and output. Layers added after creating protocols and outputs
 * will be used.
 * 
 * @author Ondrej Milenovsky
 * */
public class VisFactory {

    private StreamProtocolCloser streamCloser;
    private ArrayList<StructProcessor> layers;
    private FilterStorage filter;

    private StructProcessor lastProtocol;

    private StructProcessor layerDiffer;
    private StructProcessor layerMerger;

    private MultipleInputProcessor painter;

    private String projectName;

    private Set<String> layerNames;
    private boolean timeLayerAdded = false;

    public VisFactory(String projectName) {
        this.projectName = projectName;
        filter = new FilterStorage(Vis2DBasicPainters.ELEMENT_TYPES, Vis2DCommonKeys.COMMON_PARAMS);
        layers = new ArrayList<StructProcessor>();
        streamCloser = new StreamProtocolCloser();
        layerNames = new HashSet<String>();
    }

    public VisFactory() {
        this("World");
    }

    // Protocols ////////////

    /** Creates and returns new realtime protocol (=null), rewrites last protocol. */
    public StructProcessor createRealtimeProtocol() {
        lastProtocol = null;
        return lastProtocol;
    }

    /** Creates and returns new memory protocol, rewrites last protocol. */
    public StructProcessor createMemoryProcotol() {
        if (layerDiffer == null) {
            lastProtocol = new MemoryProtocol(getLayerDiffer());
        } else {
            // TODO remove after caching
            lastProtocol = new MemoryProtocol(new StateGetter(getLayerDiffer()));
        }
        return lastProtocol;
    }

    /**
     * Creates and returns new file writer protocol, rewrites last protocol. No output can be
     * created on this protocol.
     */
    public StructProcessor createFileWriterProtocol(String fileName) {
        if (!timeLayerAdded) {
            System.err.println("Time layer should be added if using file protocol");
        }
        lastProtocol = new FileWriterProtocol(new File(fileName), getLayerDiffer());
        streamCloser.addStreamProtocol((StreamProtocol) lastProtocol);
        return lastProtocol;
    }

    /** Creates and returns new file reader protocol, rewrites last protocol. */
    public StructProcessor createFileReaderProtocol(String fileName) {
        lastProtocol = new FileReaderProtocol(new File(fileName));
        streamCloser.addStreamProtocol((StreamProtocol) lastProtocol);
        return lastProtocol;
    }

    // Updaters/differs ////////////

    /** Returns instance of differ on layers, always one instance. */
    public Differ getLayerDiffer() {
        if (layerDiffer == null) {
            layerDiffer = new Differ(layers);
        }
        return (Differ) layerDiffer;
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
        Vis2DOutput ret = createVis2D(params);
        ret.addInput(createVis2dPainter(ret));
        return ret;
    }

    /** Creates and returns vis 2D player on last protocol. */
    public Vis2DOutput createVis2DPlayerOutput(Vis2DParams params) {
        if (!(lastProtocol instanceof FileReaderProtocol)) {
            System.err.println("Player should be created on file reader protocol.");
        }
        Vis2DOutput ret = createVis2D(params);
        Player player = new Player(lastProtocol);
        TreePainter painter = new TreePainter(new StateGetter(player));
        painter.addPainters(Vis2DBasicPainters.createBasicPainters(ret));
        ret.addPanel(new PlayerControls(player), BorderLayout.SOUTH);
        ret.addInput(painter);
        this.painter = painter;
        return ret;
    }

    private Vis2DOutput createVis2D(Vis2DParams params) {
        Vis2DOutput ret = new Vis2DOutput(params);
        ret.addTransformators(Vis2DBasicTransformators.createBasicTransformators());
        ret.setStreamCloser(streamCloser);
        return ret;
    }

    // Painters ////////////

    /**
     * Returns instance of tree painter for vis 3D on last protocol, always one instance for last
     * protocol, new instance for new protocol.
     */
    private StructProcessor createVis2dPainter(Vis2DOutput vis2d) {
        TreePainter painter;
        if (lastProtocol == null) {
            painter = new TreePainter(new MergeUpdater(layers));
        } else {
            if (lastProtocol instanceof StreamOutputProtocol) {
                throw new RuntimeException("Painter cannot be created on output protocol.");
            }
            painter = new TreePainter(new DiffUpdater(lastProtocol));
        }
        painter.addPainters(Vis2DBasicPainters.createBasicPainters(vis2d));
        this.painter = painter;
        return painter;
    }

    // Layers ////////////

    public void addTimeLayer(TimeHolder timeHolder) {
        addLayer(new TimeLayer(timeHolder));
        timeLayerAdded = true;
    }

    /** adds layer which generates elements from environment */
    public void addLayer(StructProcessor layer) {
        if (layer instanceof AbstractLayer) {
            AbstractLayer abstractLayer = (AbstractLayer) layer;
            abstractLayer.setRoot(projectName);
            abstractLayer.setFilter(filter);
            String name = abstractLayer.getName();
            if (layerNames.contains(name)) {
                System.err.println("Warning! duplicate layer name (path): " + name);
            } else {
                layerNames.add(name);
            }
        }
        if (layer instanceof TimeLayer) {
            timeLayerAdded = true;
        }
        layers.add(layer);
    }

    /** adds output layer to last painter */
    public void addOutputLayer(StructProcessor layer) {
        painter.addInput(layer);
    }

    // Other ////////

    // TODO remove filter
    public FilterStorage getFilter() {
        return filter;
    }

}
