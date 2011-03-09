package incubator.vis;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;

import javax.vecmath.Point3d;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.layer.common.CommonLayer;

public class TextLayer extends CommonLayer {

    public static final char SPACING_CHARACTER = 'H';
    private final TextInfoBox texts;
    private final Font font;
    private final Color fontColor;

    public TextLayer(TextInfoBox texts) {
	this.texts = texts;
	font = new Font("Arial", 0, 10);
	fontColor = Color.WHITE;
    }

    @Override
    public void paint(Graphics2D canvas) {
	FontMetrics fontMetrics = canvas.getFontMetrics(font);
	int textWidth = 1;
	// compute max width of the text
	for (String string : texts.getTexts()) {
	    int stringWidth = fontMetrics.stringWidth(string);
	    if (stringWidth > textWidth) {
		textWidth = stringWidth;
	    }
	}
	textWidth += 2 * fontMetrics.charWidth(SPACING_CHARACTER);
	// compute text height
	int textHeight = (1 + texts.getTexts().size())
		* fontMetrics.getHeight();

	canvas.setColor(new Color(0, 0, 0, 200));
	canvas.fillRect(Vis.transX(texts.getPosition().x), Vis.transY(texts
		.getPosition().y), textWidth, textHeight);

	Font oldFont = canvas.getFont();
	canvas.setFont(font);
	canvas.setColor(fontColor);

	int x = Vis.transX(texts.getPosition().x)
		+ fontMetrics.charWidth(SPACING_CHARACTER);
	int y = Vis.transY(texts.getPosition().y);
	y += fontMetrics.getHeight();
	for (String string : texts.getTexts()) {
	    canvas.drawString(string, x, y);
	    y += fontMetrics.getHeight();
	}

	canvas.setFont(oldFont);

    }

    @Override
    public String getLayerDescription() {
	String description = "Layer shows textboxes";
	return buildLayersDescription(description);
    }

    public static TextLayer create(TextInfoBox textInfoBox) {
	return new TextLayer(textInfoBox);
    }

    public interface TextInfoBox {

	Point3d getPosition();

	List<String> getTexts();
    }
}
