package cz.agents.alite.vis.layer.common;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cz.agents.alite.vis.Vis;
import cz.agents.alite.vis.VisManager;
import cz.agents.alite.vis.layer.VisLayer;
import cz.agents.alite.vis.layer.terminal.SpriteLayer;
import cz.agents.alite.vis.layer.toggle.KeyToggleLayer;
import cz.agents.alite.vis.layer.toggle.ToggleLayer;

/**
 * The HelpLayer shows a help window, if F1 is pressed.
 *
 * The help text is dynamically built from the help strings defined in
 * the particular layers by the setHelpOverrideString() method, or
 * from a default value, defined as a string "ClassNameOfTheLayer layer".
 *
 *
 * @author Antonin Komenda
 *
 */
public class HelpLayer extends CommonLayer {

    private final ToggleLayer toggleLayer;
    private int offsetPages = 0;

    protected HelpLayer(ToggleLayer toggleLayer) {
        this.toggleLayer = toggleLayer;
    }

    @Override
    public void init(Vis vis) {
        super.init(vis);

        vis.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                    offsetPages--;
                    if (offsetPages < 0) {
                        offsetPages = 0;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                    offsetPages++;
                    if (offsetPages > 10) {
                        offsetPages = 10;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    toggleLayer.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void paint(Graphics2D canvas) {
        Dimension dim = Vis.getDrawingDimension();

        int width = 800;
        int y = 20;
        int x = (dim.width - width) / 2;
        int height = dim.height - y * 2;

        // help box
        canvas.setColor(new Color(0, 0, 0, 200));
        canvas.fillRect(x, y, width, height);

        // help scroll info
        canvas.setColor(Color.WHITE);
        canvas.drawString("Scroll help by PgUp/PgDn keys.", x + width - 195, y + height - 15);

        // info text
        String infoText = "A-Lite Operator\n" +
                "\n" +
                "[Basic Controls] World can be zoomed and panned.\n" +
                "Zooming can be done by mouse wheel and it magnifies area under mouse cursor.\n" +
                "Panning can be done by drag and drop using right mouse button.\n" +
                "\n";

        // help text
        double pageHeight = height / 2.0;
        boolean showing = false;

        int offsetY = 0;

        boolean layerShowed;
        int index = 0;
        do {
            layerShowed = false;
            for (VisLayer layer : VisManager.getLayers()) {
                if (showLayerHelp(index, layer)) {
                    layerShowed = true;

                    String description = layer.getLayerDescription().replace("<br/>", "\n");
                    if (infoText != null) {
                        description = infoText + description;
                        infoText = null;
                    }
                    String[] strings = description.split("\n");

                    for (String string : strings) {
                        if (offsetY >= pageHeight*offsetPages) {
                            showing = true;
                        }
                        if (offsetY >= pageHeight*offsetPages + pageHeight * 2.0 - 20) {
                            showing = false;
                        }

                        if (showing) {
                            canvas.drawString(string, x + 10, (int) (y + offsetY + 20 - pageHeight * offsetPages));
                        }

                        offsetY += canvas.getFontMetrics().getHeight();
                    }
                    offsetY += canvas.getFontMetrics().getHeight();
                }
            }
            if (index == 0){
                infoText = "   ----------- toggleable -----------\n\n";
            }
            if (index == 1){
                infoText = "   ----------- non-toggleable -----------\n\n";
            }
            if (index == 2){
                infoText = "   ----------- entities -----------\n\n";
            }

            index++;
        } while (layerShowed);

    }

    public static VisLayer create() {
        KeyToggleLayer toggle = KeyToggleLayer.create(KeyEvent.VK_F1);
        toggle.setEnabled(false);
        toggle.addSubLayer(new HelpLayer(toggle));

        return toggle;
    }

    @Override
    public String getLayerDescription() {
        String description = "[Help] The layer shows this help.";
        return buildLayersDescription(description);
    }

    private boolean showLayerHelp(int index, VisLayer layer) {
        switch (index) {
        case 0:
            if ((layer instanceof KeyToggleLayer) && ((KeyToggleLayer) layer).getToggleKey() == "s") {
                return true;
            }
            break;

        case 1:
            if (!showLayerHelp(0, layer)
                    && (layer instanceof KeyToggleLayer)
                    && (((KeyToggleLayer) layer).getToggleKey() != null || ((KeyToggleLayer) layer).getToggleKeyCode() != null)) {
                return true;
            }
            break;

        case 2:
            if (!showLayerHelp(0, layer) && !showLayerHelp(1, layer) && !showLayerHelp(3, layer)) {
                return true;
            }
            break;

        case 3:
            // TODO: refactor entity toggle layer
            //if ((layer instanceof EntityToggleLayer) || (layer instanceof SpriteLayer)) {
            if (layer instanceof SpriteLayer) {
                return true;
            }
            break;

        default:
            break;
        }

        return false;
    }

}