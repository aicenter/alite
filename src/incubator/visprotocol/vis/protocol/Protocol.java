package incubator.visprotocol.vis.protocol;


public interface Protocol {

    public <T> void push(T elements);

    public <T> T pull(Class<T> clazz);

    public void nextStep();

}
