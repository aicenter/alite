package incubator.visprotocol.processor;

import incubator.visprotocol.structure.Structure;

/**
 * Pulles structures from all subprocessors and pushes to output, then pulls structure from the
 * output.
 * 
 * Push: any structure (not useful)
 * 
 * Pull: product of output, which is composed from inputs, changes current state
 * 
 * Forward: fills the output
 * 
 * @author Ondrej Milenovsky
 * */
public class LightPullMux extends MultipleProcessor implements Forwarder {

    private StructProcessor output;

    public LightPullMux() {
    }

    public LightPullMux(StructProcessor output) {
        this.output = output;
    }

    public void setOutput(StructProcessor output) {
        this.output = output;
    }

    /** this might not be useful */
    @Override
    public void push(Structure newPart) {
        output.push(newPart);
    }

    /** pull from all subprocessors, push to output, return output.pull(), changes current state */
    @Override
    public Structure pull() {
        forward();
        return output.pull();
    }

    /** pushes from subprocessors to the output */
    @Override
    public void forward() {
        if (output == null) {
            throw new NullPointerException("No output");
        }
        for (StructProcessor pr : getProcessors()) {
            output.push(pr.pull());
        }
    }

}
