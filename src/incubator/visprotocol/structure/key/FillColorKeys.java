package incubator.visprotocol.structure.key;

import java.awt.Color;

/**
 * Keys for FillColorPainter, fills the whole canvas by one color.
 * 
 * @author Ondrej Milenovsky
 * */
public abstract class FillColorKeys {

    public static final String TYPE = "FillColor";

    public static final Typer<Color> COLOR = new Typer<Color>("color");

}
