package incubator.visprotocol.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of StructProcessor with ArrayList of inputs. If the collection of inputs is used
 * elsewhere, inherited class should compy the inputs before first pull.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class MultipleInputProcessor implements StructProcessor {

    private List<StructProcessor> inputs;

    public MultipleInputProcessor(List<StructProcessor> inputs) {
        this.inputs = inputs;
    }

    public void addInput(StructProcessor input) {
        inputs.add(input);
    }

    protected boolean isEmpty() {
        return inputs.isEmpty();
    }

    /** makes copy of collection of inputs to new array list */
    protected void copyInputsArrayList() {
        inputs = new ArrayList<StructProcessor>(inputs);
    }

    /** makes copy of collection of inputs to new linked list */
    protected void copyInputsLinkedList() {
        inputs = new LinkedList<StructProcessor>(inputs);
    }

    protected Collection<StructProcessor> getInputs() {
        return inputs;
    }

}
