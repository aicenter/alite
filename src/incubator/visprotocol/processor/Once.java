package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Only once pulling from input.
 * 
 * @author Ondrej Milenovsky
 * */
public class Once extends OneInputProcessor {

    private boolean generate = true;

    public Once(StructProcessor input) {
        super(input);
    }

    /** First time pulls from input, then always empty structure. */
    @Override
    public Structure pull() {
        if (generate) {
            generate = false;
            return super.pull();
        }
        return Structure.createEmptyInstance();
    }

}
