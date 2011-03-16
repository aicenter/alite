package incubator.visprotocol.vis.player;

import incubator.visprotocol.processor.StructProcessor;
import incubator.visprotocol.vis.player.ui.PlayerController;

import java.util.HashSet;
import java.util.Set;

/**
 * Player pulling from input.
 * 
 * @author Ondrej Milenovsky
 * */
public class Player {

    private final Set<PlayerController> controllers;

    private StructProcessor input;

    // properties
    private double speed = 1;
    private long position = 0;
    private long maxStoredTime = 60000;

    public Player() {
        this(null);
    }

    public Player(StructProcessor input) {
        this.input = input;
        controllers = new HashSet<PlayerController>();
    }

    public void setInput(StructProcessor input) {
        this.input = input;
    }

    public void addController(PlayerController pc) {
        controllers.add(pc);
    }

    public void removeController(PlayerController pc) {
        controllers.remove(pc);
    }

    public void setSpeed(double speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException(
                    "Speed must be > 0, you can use stop() or playBackwards()");
        }
        this.speed = speed;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public void play() {

    }

    public void stop() {

    }

    public void playBackwards() {

    }

}
