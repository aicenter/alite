package incubator.visprotocol.vis.structure.key;

/**
 * Structure to get typed parameter from Element.
 * 
 * @author Ondrej Milenovsky
 * */
public class Typer<E> {

    public final String paramId;

    public Typer(String paramId) {
        this.paramId = paramId;
    }

    public String getId() {
        return paramId;
    }

    @Override
    public String toString() {
        return paramId;
    }

    @Override
    public int hashCode() {
        return paramId.hashCode();
    }

}
