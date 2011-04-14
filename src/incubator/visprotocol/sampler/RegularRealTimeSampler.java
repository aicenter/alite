package incubator.visprotocol.sampler;

/**
 * Constant interval between samples
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class RegularRealTimeSampler implements Sampler {

    private static final int PERIOD_MILLIS = 1000;
    private static final int THREAD_PRIORITY = Thread.MIN_PRIORITY;

    private boolean running = false;

    @Override
    public void start() {
        running = true;
        new Thread(new Runnable() {

            @Override
            public void run() {
                Thread.currentThread().setPriority(THREAD_PRIORITY);
                while (running) {
                    sample();
                    try {
                        Thread.sleep(PERIOD_MILLIS);
                    } catch (InterruptedException ex) {

                    }
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        running = false;
    }

    protected abstract void sample();
}
