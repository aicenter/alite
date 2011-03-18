package incubator.visprotocol.vis.player;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StateHolder;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.processor.updater.DiffUpdater;
import incubator.visprotocol.protocol.StreamProtocol;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

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
    private Structure currFrame = new Structure(0L);
    private long lastFullFrame = Long.MIN_VALUE;
    private long duration = -1;
    private long startTime = 0;
    private boolean positionLock = true;
    private boolean fullFramesDuringPush = false;

    public Player(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public Player(List<StructProcessor> inputs) {
        super(inputs);
        listeners = new LinkedHashSet<FrameListener>();
        diffFrames = new TreeMap<Long, Structure>();
        fullFrames = new TreeMap<Long, Structure>();
        speed = 0.1;
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
    }

    @Override
    public synchronized void setPosition(long position) {
        if (positionLock) {
            return;
        }
        long lastPosition = this.position;
        this.position = Math.min(position, startTime + duration);
        if (lastPosition != position) {
            generateFrame();
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
    public synchronized void play() {
        if (state == State.STOPPED) {
            wakeUp();
        }
        changeState(State.PLAYING);
    }

    @Override
    public synchronized void playBackwards() {
        if (state == State.STOPPED) {
            wakeUp();
        }
        changeState(State.BACKWARDS);
    }

    @Override
    public synchronized void pause() {
        if (changeState(State.STOPPED)) {
            fallAsleep();
        }
    }

    private synchronized void wakeUp() {
        thread.notify();
    }

    private synchronized void fallAsleep() {
        try {
            thread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        return currFrame;
    }

    private synchronized void generateFrame() {
        if (fullFrames.isEmpty()) {
            return;
        }
        positionLock = true;
        currFrame = generateFrame(position);
        if (lastFullFrame + intervalFullStates <= position) {
            fullFrames.put(currFrame.getTimeStamp(), currFrame);
        }
        for (FrameListener listener : listeners) {
            listener.drawFrame(currFrame);
        }
        positionLock = false;
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
            updater = new DiffUpdater(entry.getValue());
        }
        long time = entry.getKey();
        // update until position
        while (true) {
            entry = diffFrames.ceilingEntry(time);
            if ((entry == null) || (entry.getKey() > position)) {
                Structure ret = updater.pull();
                if (currFrame == null) {
                    currFrame = ret;
                }
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

    @Override
    public void close() {
        state = State.TERMINATE;
    }

    @Override
    public void run() {
        loadFromInputs();
        state = State.PLAYING;
        position = startTime;
        positionLock = false;
        while (state != State.TERMINATE) {
            generateFrame();
            try {
                Thread.sleep(1000 / frameRate);
                position += (long) (1000.0 * speed / (double) frameRate);
                position = Math.min(startTime + duration, position);
                generateFrame();
            } catch (InterruptedException e) {
            }
        }
    }

    public static enum State {
        STOPPED, PLAYING, BACKWARDS, TERMINATE, STARTING
    }

}
