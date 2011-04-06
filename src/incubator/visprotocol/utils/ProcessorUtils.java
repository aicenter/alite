package incubator.visprotocol.utils;

import incubator.visprotocol.processor.StructProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondrej Milenovsky
 * */
public class ProcessorUtils {

    private ProcessorUtils() {
    }

    public static List<StructProcessor> asList(StructProcessor... inputs) {
        ArrayList<StructProcessor> list = new ArrayList<StructProcessor>(inputs.length);
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        return list;
    }

    public static List<StructProcessor> join(StructProcessor firstInput, StructProcessor... inputs) {
        ArrayList<StructProcessor> list = new ArrayList<StructProcessor>(inputs.length + 1);
        list.add(firstInput);
        for (int i = 0; i < inputs.length; i++) {
            list.add(inputs[i]);
        }
        return list;
    }

}
