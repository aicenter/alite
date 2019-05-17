/* 
 * Copyright (C) 2017 Czech Technical University in Prague.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package cz.cvut.fel.aic.alite.vis.layer.toggle;

import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.KeyStroke;


/**
 * The KeySwithLayer cycle through sub-layer according to a pressed key.
 *
 * @author Jiri Vokrinek
 */
public class KeySwitchLayer extends SwitchLayer {

	private final String toggleKey;
	private final Integer toggleKeyCode;
	private KeyListener keyListener;

	
	protected KeySwitchLayer(String toggleKey) {
		this.toggleKey = toggleKey;
		this.toggleKeyCode = null;
	}

	protected KeySwitchLayer(String toggleKey, VisLayer layer) {
		this.toggleKey = toggleKey;
		this.toggleKeyCode = null;
		addSubLayer(layer);
	}
	
	protected KeySwitchLayer(int toggleKeyCode) {
		this.toggleKey = null;
		this.toggleKeyCode = toggleKeyCode;
	}

	@Override
	public void init(Vis vis) {
		super.init(vis);

		keyListener = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (toggleKeyCode != null && e.getKeyCode() == toggleKeyCode) {
					cycle();
				}
				if (toggleKey != null && KeyEvent.getKeyText(e.getKeyCode()).equalsIgnoreCase(toggleKey)) {
					cycle();
				}
			}
		};
		vis.addKeyListener(keyListener);
	}

	@Override
	public void deinit(Vis vis) {
		super.deinit(vis);

		vis.removeKeyListener(keyListener);
	}

	@Override
	public String getLayerDescription() {
		String description;
		if (toggleKey != null) {
			description = "Switch by pressing key '" + toggleKey+ "':";
		} else if (toggleKeyCode != null) {
			description = "Switch by pressing key '" + KeyStroke.getKeyStroke(toggleKeyCode, 0) + "':";
		} else {
			description = "Switched sub-layers to show:";
		}
		return buildLayersDescription(description);
	}

	public String getToggleKey() {
		return toggleKey;
	}

	public Integer getToggleKeyCode() {
		return toggleKeyCode;
	}

	public static KeySwitchLayer create(int toggleKey) {
		return new KeySwitchLayer(toggleKey);
	}

	public static KeySwitchLayer create(String toggleKeyCode) {
		return new KeySwitchLayer(toggleKeyCode);
	}
	
	public static KeySwitchLayer create(String toggleKeyCode, VisLayer visLayer) {
		return new KeySwitchLayer(toggleKeyCode, visLayer);
	}

}
