package incubator.visprotocol.vis.differ;

import incubator.visprotocol.vis.structure.Structure;

/**
 * Stores last state and structure to send. When pushed pushed new part updates
 * last state and differences are added to the structure to send.
 */
public class GeneralDiffer implements Differ {

    private Structure state;
    private Structure newState;

    public GeneralDiffer() {
        state = new Structure();
        newState = new Structure();
    }

    @Override
    public void push(Structure newPart) {
        // TODO Auto-generated method stub

    }

    @Override
    public Structure pull() {
        Structure st = newState;
        newState = new Structure();
        return st;
    }

}
