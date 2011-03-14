package incubator.visprotocol.structure.key;

/**
 * Structure to get typed parameter from Element.
 * 
 * @author Ondrej Milenovsky
 * */
public class Typer<E> {

    public final String id;

    public Typer(String paramId) {
        this.id = paramId;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
