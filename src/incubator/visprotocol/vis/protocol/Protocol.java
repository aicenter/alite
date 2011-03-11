package incubator.visprotocol.vis.protocol;

public interface Protocol {

    public void push(String id, Object elements);

    public Object pull(String id);

    public void nextStep();

}
