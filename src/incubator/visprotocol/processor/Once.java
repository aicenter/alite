package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Only once pulling from input.
 * 
 * Push: push to the input
 * 
 * Pull: First time pulls input.pull(), then always empty structure.
 * 
 * @author Ondrej Milenovsky
 * */
public class Once implements StructProcessor {

    private boolean generate = true;
    private StructProcessor input;

    public Once() {
    }

    public Once(StructProcessor input) {
        this.input = input;
    }

    public void setInput(StructProcessor input) {
        this.input = input;
    }

    public StructProcessor getInput() {
        return input;
    }

    /** First time pulls from input, then always empty structure. */
    @Override
    public Structure pull() {
        if (generate) {
            generate = false;
            return input.pull();
        }
        return Structure.createEmptyInstance();
    }

    /** Pushes to the input (probably not useful) */
    @Override
    public void push(Structure newPart) {
        input.push(newPart);
    }

}
