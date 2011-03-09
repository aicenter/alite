package incubator.visprotocol.vis.sampler;

// TODO: create sampler interface
public abstract class MaxFPSRealTimeSampler {

    private static final int FPS_MAX = 24;
    private static final int THREAD_PRIORITY = Thread.MIN_PRIORITY;

    public void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Thread.currentThread().setPriority(THREAD_PRIORITY);
                while (true) {
                    long startNanos = System.nanoTime();
                    sample();
                    long endNanos = System.nanoTime();

                    long sleepNanos = (long) (1.0 / FPS_MAX * 1000000000.0)
                            - (endNanos - startNanos);
                    sleepNanos = sleepNanos < 0 ? 0 : sleepNanos;
                    long sleepMillis = sleepNanos / 1000000;
                    sleepNanos -= sleepMillis * 1000000;

                    try {
                        Thread.sleep(sleepMillis, (int) sleepNanos);
                    } catch (InterruptedException ex) {

                    }
                }
            }

        }).start();
    }

    protected abstract void sample();

}
