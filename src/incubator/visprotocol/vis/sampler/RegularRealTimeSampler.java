package incubator.visprotocol.vis.sampler;

//TODO: create sampler interface
public abstract class RegularRealTimeSampler {

    private static final int PERIOD_MILLIS = 1000;
    private static final int THREAD_PRIORITY = Thread.MIN_PRIORITY;

    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Thread.currentThread().setPriority(THREAD_PRIORITY);
                while (true) {
                    sample();
                    try {
                        Thread.sleep(PERIOD_MILLIS);
                    } catch (InterruptedException ex) {

                    }
                }
            }

        }).start();
    }

    protected abstract void sample();

}
