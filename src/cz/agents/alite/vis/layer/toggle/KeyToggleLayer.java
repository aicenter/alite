package cz.agents.alite.vis.layer.toggle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.KeyStroke;

import cz.agents.alite.vis.Vis;


public class KeyToggleLayer extends ToggleLayer {

    private final String toggleKey;
    private final Integer toggleKeyCode;

    protected KeyToggleLayer(String toggleKey) {
        this.toggleKey = toggleKey;
        this.toggleKeyCode = null;
    }

    protected KeyToggleLayer(int toggleKeyCode) {
        this.toggleKey = null;
        this.toggleKeyCode = toggleKeyCode;
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
                if (toggleKeyCode != null && e.getKeyCode() == toggleKeyCode) {
                    setEnabled(!getEnabled());
                }
                if (toggleKey != null && KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(toggleKey)) {
                    setEnabled(!getEnabled());
                }
            }
        });
    }

    @Override
    public String getLayerDescription() {
        String description;
        if (toggleKey != null) {
            description = "Toggle by pressing key '" + toggleKey+ "':";
        } else if (toggleKeyCode != null) {
            description = "Toggle by pressing key '" + KeyStroke.getKeyStroke(toggleKeyCode, 0) + "':";
        } else {
            description = "All sub-layers are always shown:";
        }
        return buildLayersDescription(description);
    }

    public String getToggleKey() {
        return toggleKey;
    }

    public Integer getToggleKeyCode() {
        return toggleKeyCode;
    }

    public static KeyToggleLayer create(int toggleKey) {
        return new KeyToggleLayer(toggleKey);
    }

    public static KeyToggleLayer create(String toggleKeyCode) {
        return new KeyToggleLayer(toggleKeyCode);
    }

}
