package incubator.visprotocol.structure.key.struct;

/**
 * Shapes for ShapePainter.
 * 
 * @author Ondrej Milenovsky
 * */
public enum Shape {
    OVAL("Oval"), RECT("Rect");
    
    private final String name;
    
    private Shape(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
