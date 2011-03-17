package incubator.visprotocol.vis.player;

/**
 * @author Ondrej Milenovsky
 */
public interface PlayerInterface {
    public void setSpeed(double speed);

    public void setPosition(long time);

    public void pause();

    public void play();

    public void playBackwards();
}
