package incubator.visprotocol.vis.player;

import incubator.visprotocol.processor.MultipleInputProcessor;
import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.structure.Structure;
import incubator.visprotocol.vis.player.ui.PlayerController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import com.sun.org.apache.xml.internal.utils.StopParseException;

/**
 * Player pulling from input.
 * 
 * @author Ondrej Milenovsky
 * */
public class Player extends MultipleInputProcessor implements PlayerInterface, Runnable {

    // pointers
    private final Set<PlayerController> controllers;
    private Thread thread;
    private final PriorityQueue<Structure> data;

    // properties
    private double speed = 1;
    private long maxStoredTime = 60000;

    // state
    private long position = 0;
    private State state = State.STOPPED;
    private Structure lastFrame = new Structure(0L);

    public Player(StructProcessor... inputs) {
        this(Arrays.asList(inputs));
    }

    public Player(List<StructProcessor> inputs) {
        super(inputs);
        controllers = new HashSet<PlayerController>();
        data = new PriorityQueue<Structure>();
        thread = new Thread(this);
        thread.start();
        pause();
    }

    public void addController(PlayerController pc) {
        controllers.add(pc);
    }

    public void removeController(PlayerController pc) {
        controllers.remove(pc);
    }

    public synchronized void setSpeed(double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException(
                    "Speed must be > 0, you can use stop() or playBackwards()");
        }
        this.speed = speed;
    }

    public synchronized void setPosition(long position) {
        this.position = position;
    }

    public synchronized void play() {
        if(state == State.STOPPED) {
            wakeUp();
        }
        state = State.PLAYING;
    }

    public synchronized void playBackwards() {
        if(state == State.STOPPED) {
            wakeUp();
        }
        state = State.BACKWARDS;
    }
    
    public synchronized void pause() {
        if (state != State.STOPPED) {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void wakeUp() {
        thread.notify();
    }

    public synchronized void push(Structure newPart) {
        try {
            data.add(newPart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Structure pull() {
        for (StructProcessor pr : getInputs()) {
            push(pr.pull());
        }
        return null;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    public static enum State {
        STOPPED, PLAYING, BACKWARDS
    }

}
