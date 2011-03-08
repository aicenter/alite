package incubator.visprotocol.creator;

import java.util.Random;

import javax.vecmath.Point3d;

import cz.agents.alite.creator.Creator;

public class TestCreator implements Creator {

    private static final int DELAY = 100;

    @Override
    public void init(String[] args) {
    }

    @Override
    public void create() {
        ExampleState exampleState = new ExampleState();
        Random random = new Random();
        while(true) {
            exampleState.exampleString = "string" + Long.toString(random.nextLong());
            exampleState.examplePosition = new Point3d(
                    random.nextDouble() * 100.0,
                    random.nextDouble() * 100.0,
                    random.nextDouble() * 100.0);
            exampleState.exampleInteger = random.nextInt();

            exampleState.exampleTime++;

            try {
                wait(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ExampleState {

        private long exampleTime = 1;

        private String exampleString = "string";
        private Point3d examplePosition = new Point3d();
        private int exampleInteger = 100;

    }

}
