package incubator.visprotocol.vis.player;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StateHolder;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.processor.updater.DiffUpdater;
import incubator.visprotocol.protocol.StreamProtocol;
import incubator.visprotocol.structure.Element;
import incubator.visprotocol.structure.Folder;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;
import incubator.visprotocol.structure.key.FillColorKeys;
import incubator.visprotocol.structure.key.TextKeys;
import incubator.visprotocol.structure.key.struct.Align;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.vecmath.Point2d;

/**
 * Player pulling from input.
 * 
 * @author Ondrej Milenovsky
 * */
public class Player extends MultipleInputProcessor implements PlayerInterface, Runnable,
        StateHolder, StreamProtocol {

    // pointers
    private final Set<FrameListener> listeners;
    private Thread thread;
    private final TreeMap<Long, Structure> diffFrames;
    private final TreeMap<Long, Structure> fullFrames;

    // properties
    private double speed = 1;
    private long intervalFullStates = 2000;
    private int frameRate = 25;

    // state
    private long position = 0;
    private State state = State.STARTING;
    private Structure currFrame = null;
    private long lastFullFrame = Long.MIN_VALUE;
    private long duration = -1;
    private long startTime = 0;
    private boolean fullFramesDuringPush = true;

    public Player(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public Player(List<StructProcessor> inputs) {
        super(inputs);
        listeners = new LinkedHashSet<FrameListener>();
        diffFrames = new TreeMap<Long, Structure>();
        fullFrames = new TreeMap<Long, Structure>();
        thread = new Thread(this);
        thread.start();
    }

    /** sets interval of fully generated frames */
    public void setIntervalFullStates(long intervalFullStates) {
        if (intervalFullStates <= 0) {
            throw new IllegalArgumentException("Max interval between full states must be > 0");
        }
        this.intervalFullStates = intervalFullStates;
    }

    public long getIntervalFullStates() {
        return intervalFullStates;
    }

    @Override
    public void addFrameListener(FrameListener listener) {
        listeners.add(listener);
    }

    public void removeFrameListener(FrameListener listner) {
        listeners.remove(listner);
    }

    @Override
    public synchronized void setSpeed(double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException(
                    "Speed must be > 0, you can use stop() or playBackwards()");
        }
        this.speed = speed;
        if (state == State.BACKWARDS) {
            this.speed = -this.speed;
        }
    }

    @Override
    public synchronized void setPosition(long position) {
        long lastPosition = this.position;
        this.position = Math.min(position, startTime + duration);
        if ((lastPosition != position) && (state == State.STOPPED)) {
            // TODO
            // generateFrame();
        }
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public long getStartTime() {
        return startTime;
    }

    @Override
    public void play() {
        if (state == State.STOPPED) {
            wakeUp();
        }
        if (changeState(State.PLAYING) && (speed < 0)) {
            speed = -speed;
        }
    }

    @Override
    public void playBackwards() {
        if (state == State.STOPPED) {
            wakeUp();
        }
        if (changeState(State.BACKWARDS) && (speed > 0)) {
            speed = -speed;
        }
    }

    @Override
    public void pause() {
        if (changeState(State.STOPPED)) {
            fallAsleep();
        }
    }

    private void wakeUp() {
        speed = 0.1;
        // TODO fix
        // synchronized (thread) {
        // thread.notify();
        // }
    }

    private void fallAsleep() {
        speed = 0;
        // TODO fix
        // try {
        // synchronized (thread) {
        // thread.wait();
        // }
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
    }

    public void loadFromInputs() {
        for (StructProcessor pr : getInputs()) {
            System.out.println("Loading from input");
            try {
                while (true) {
                    Structure struct = pr.pull();
                    if (struct == null) {
                        break;
                    }
                    push(struct);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Full frames: " + fullFrames.size());
            System.out.println("Diff frames: " + diffFrames.size());
            System.out.println("Start: " + startTime);
            System.out.println("End: " + (startTime + duration));
        }
    }

    public synchronized void push(Structure newPart) {
        try {
            if (newPart.getTimeStamp() == null) {
                throw new NullPointerException(newPart.getType() + " with no timestamp");
            }
            if (dataIsEmpty()) {
                startTime = newPart.getTimeStamp();
            }
            if (newPart.getType().equals(CommonKeys.STRUCT_COMPLETE)) {
                fullFrames.put(newPart.getTimeStamp(), newPart);
                lastFullFrame = Math.max(newPart.getTimeStamp(), lastFullFrame);
            } else {
                diffFrames.put(newPart.getTimeStamp(), newPart);
                if (fullFrames.isEmpty()) {
                    lastFullFrame = startTime - intervalFullStates;
                    generateFullFrame();
                }
            }
            if (newPart.getTimeStamp() > duration) {
                duration = newPart.getTimeStamp();
                if (fullFramesDuringPush && (lastFullFrame + intervalFullStates < duration)) {
                    generateFullFrame();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** updates data by inputs, returns null */
    @Override
    public Structure pull() {
        for (StructProcessor pr : getInputs()) {
            push(pr.pull());
        }
        return currFrame;
    }

    /** returns current frame */
    @Override
    public Structure getState() {
        if (currFrame == null) {
            return getLoadingScreen();
        }
        return currFrame;
    }

    private synchronized void generateFrame() {
        if ((currFrame != null) && (position == currFrame.getTimeStamp()) || fullFrames.isEmpty()) {
            return;
        }
        // faster forward generation from last full frame
        long backFullFrame = fullFrames.floorKey(position);
        if ((currFrame != null) && (backFullFrame < currFrame.getTimeStamp())
                && (fullFrames.floorKey(currFrame.getTimeStamp()) == backFullFrame)
                && (currFrame.getTimeStamp() <= position)) {
            DiffUpdater updater = new DiffUpdater(currFrame);
            long time = currFrame.getTimeStamp();
            while (true) {
                Entry<Long, Structure> entry = diffFrames.ceilingEntry(time + 1);
                if ((entry == null) || (entry.getKey() > position)) {
                    currFrame = updater.getState();
                    break;
                }
                updater.push(entry.getValue());
                time = entry.getKey();
            }
        } else {
            currFrame = generateFrame(position);
        }
        if (lastFullFrame + intervalFullStates <= position) {
            lastFullFrame = Math.max(currFrame.getTimeStamp(), lastFullFrame);
            fullFrames.put(currFrame.getTimeStamp(), currFrame);
        }
        for (FrameListener listener : listeners) {
            listener.drawFrame(currFrame);
        }
    }

    private void generateFullFrame() {
        lastFullFrame += intervalFullStates;
        fullFrames.put(lastFullFrame, generateFrame(lastFullFrame));
    }

    private Structure generateFrame(long position) {
        // find latest full frame
        DiffUpdater updater;
        Entry<Long, Structure> entry = fullFrames.floorEntry(position);
        if (entry == null) {
            // no full frame, generate from first diff
            entry = diffFrames.floorEntry(position);
            if (entry == null) {
                throw new RuntimeException("No data");
            }
            updater = new DiffUpdater();
            updater.push(entry.getValue());
        } else {
            updater = new DiffUpdater(entry.getValue().deepCopy());
        }
        long time = entry.getKey();
        // update until position
        while (true) {
            entry = diffFrames.ceilingEntry(time);
            if ((entry == null) || (entry.getKey() > position)) {
                Structure ret = updater.pull();
                return ret;
            }
            updater.push(entry.getValue());
            time = entry.getKey() + 1;
        }
    }

    private boolean changeState(State newState) {
        if ((state == State.TERMINATE) || (state == newState)) {
            return false;
        }
        state = newState;
        return true;
    }

    private boolean dataIsEmpty() {
        return diffFrames.isEmpty() && fullFrames.isEmpty();
    }

    private Structure getLoadingScreen() {
        Structure ret = new Structure(CommonKeys.STRUCT_COMPLETE, 0L);
        Folder f = ret.getRoot("Root");
        Element e;

        e = f.getElement("Back", FillColorKeys.TYPE);
        e.setParameter(FillColorKeys.COLOR, Color.BLACK);

        e = f.getElement("Loading", TextKeys.TYPE);
        e.setParameter(TextKeys.ALIGN_ON_SCREEN, Align.CENTER);
        e.setParameter(TextKeys.TEXT, "Loading in progress");
        e.setParameter(TextKeys.FONT, new Font("GothicE", Font.PLAIN, 30));
        e.setParameter(TextKeys.CONSTANT_SIZE, true);
        e.setParameter(TextKeys.COLOR, Color.RED);

        e = f.getElement("Diffs", TextKeys.TYPE);
        e.setParameter(TextKeys.FONT, new Font("Arial", Font.PLAIN, 10));
        e.setParameter(TextKeys.TEXT, "Diff frames: " + diffFrames.size());
        e.setParameter(TextKeys.COLOR, Color.WHITE);
        e.setParameter(TextKeys.POS, new Point2d(0, 30));

        e = f.getElement("Fulls", TextKeys.TYPE);
        e.setParameter(TextKeys.TEXT, "Full frames: " + fullFrames.size());
        e.setParameter(TextKeys.POS, new Point2d(0, 40));

        e = f.getElement("Start", TextKeys.TYPE);
        e.setParameter(TextKeys.TEXT, "Start time: " + startTime);
        e.setParameter(TextKeys.POS, new Point2d(0, 50));

        e = f.getElement("End", TextKeys.TYPE);
        e.setParameter(TextKeys.TEXT, "End time: " + (startTime + duration));
        e.setParameter(TextKeys.POS, new Point2d(0, 60));
        return ret;
    }

    @Override
    public void close() {
        state = State.TERMINATE;
    }

    @Override
    public void run() {
        loadFromInputs();
        state = State.PLAYING;
        position = startTime;
        while (state != State.TERMINATE) {
            try {
                Thread.sleep(1000 / frameRate);
                step();
            } catch (InterruptedException e) {
            }
        }
    }

    private synchronized void step() {
        position += (long) (1000.0 * speed / (double) frameRate);
        position = Math.min(startTime + duration, position);
        position = Math.max(startTime, position);
        generateFrame();
    }

    public static enum State {
        STOPPED, PLAYING, BACKWARDS, TERMINATE, STARTING
    }

}
