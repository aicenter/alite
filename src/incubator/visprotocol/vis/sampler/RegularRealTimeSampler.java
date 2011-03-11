package incubator.visprotocol.vis.sampler;

public abstract class RegularRealTimeSampler implements Sampler {

    private static final int PERIOD_MILLIS = 1000;
    private static final int THREAD_PRIORITY = Thread.MIN_PRIORITY;

    @Override
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
