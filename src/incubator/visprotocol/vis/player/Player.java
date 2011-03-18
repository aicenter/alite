package incubator.visprotocol.vis.player;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StateHolder;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.protocol.StreamProtocol;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.structure.key.CommonKeys;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

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
    private final PriorityQueue<Structure> data;
    private final PriorityQueue<Structure> fullFrames;

    // properties
    private double speed = 1;
    private long intervalFullStates = 10000;

    // state
    private long position = 0;
    private State state = State.STOPPED;
    private Structure currFrame = new Structure(0L);
    private long lastFullFrame = Long.MIN_VALUE;
    private long duration = -1;
    private long startTime = 0;

    public Player(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public Player(List<StructProcessor> inputs) {
        super(inputs);
        listeners = new LinkedHashSet<FrameListener>();
        data = new PriorityQueue<Structure>();
        fullFrames = new PriorityQueue<Structure>();
        thread = new Thread(this);
        thread.start();
        pause();
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
        long lastPosition = position;
        this.position = Math.min(position, duration);
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

    public synchronized void push(Structure newPart) {
        try {
            if (newPart.getTimeStamp() == null) {
                throw new NullPointerException(newPart.getType() + " with no timestamp");
            }
            if (dataIsEmpty()) {
                startTime = newPart.getTimeStamp();
            }
            if (newPart.getType().equals(CommonKeys.STRUCT_COMPLETE)) {
                fullFrames.add(newPart);
                lastFullFrame = Math.max(newPart.getTimeStamp(), lastFullFrame);
            } else {
                data.add(newPart);
                if (fullFrames.isEmpty()) {
                    lastFullFrame = startTime - intervalFullStates;
                }
            }
            if (newPart.getTimeStamp() > duration) {
                duration = newPart.getTimeStamp();
                if (lastFullFrame + intervalFullStates < duration) {
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
        return null;
    }

    /** returns current frame */
    @Override
    public Structure getState() {
        return currFrame;
    }

    @Override
    public void run() {
        while (state != State.TERMINATE) {
            generateFrame();
            try {
                // TODO presne spocitat delay
                Thread.sleep((int) (1000 / speed));
                position += 1000 / speed;
                generateFrame();
            } catch (InterruptedException e) {
            }
        }
    }

    private synchronized void generateFrame() {
        if (fullFrames.isEmpty()) {
            return;
        }
        currFrame = generateFrame(position);
        for (FrameListener listener : listeners) {
            listener.drawFrame(currFrame);
        }
    }

    private void generateFullFrame() {
        lastFullFrame += intervalFullStates;
        fullFrames.add(generateFrame(lastFullFrame));
    }

    private Structure generateFrame(long where) {
        // TODO
        return null;
    }

    private boolean changeState(State newState) {
        if ((state == State.TERMINATE) || (state == newState)) {
            return false;
        }
        state = newState;
        return true;
    }

    private boolean dataIsEmpty() {
        return data.isEmpty() && fullFrames.isEmpty();
    }

    @Override
    public void close() {
        state = State.TERMINATE;
    }

    public static enum State {
        STOPPED, PLAYING, BACKWARDS, TERMINATE
    }

}
