/* 
 * Copyright (C) 2019 Czech Technical University in Prague.
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
package cz.cvut.fel.aic.alite.vis.layer.common;

import cz.cvut.fel.aic.alite.simulation.Simulation;
import cz.cvut.fel.aic.alite.vis.Vis;
import cz.cvut.fel.aic.alite.vis.layer.AbstractLayer;
import cz.cvut.fel.aic.alite.vis.layer.VisLayer;
import cz.cvut.fel.aic.alite.vis.layer.toggle.KeyToggleLayer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.MessageFormat;

/**
 * The layer shows the status of the simulation and controls it through various
 * key bindings.
 *
 * The information shown, tells the user, the current simulation speed (ratio of
 * the real time and simulation time) and the state of the simulation.
 *
 * The simulation speed ratio can be controlled by '+' and '-' keys. And
 * additionally, Ctrl+'*' sets the fastest possible speed (infinite ratio), and
 * '*' pressed sets the ratio to its default value.
 *
 * All the possible key strokes are described in the internal help showed by the
 * {@link HelpLayer}.
 *
 *
 * @author Antonin Komenda
 */
public class SimulationControlLayer extends AbstractLayer {

	private final Simulation simulation;
	private KeyListener keyListener;

	SimulationControlLayer(Simulation simulation) {
		this.simulation = simulation;
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
				if (e.getKeyChar() == '+') {
					simulation.setSimulationSpeed(simulation.getSimulationSpeed() * 0.9);
				} else if (e.getKeyChar() == '-') {
					simulation.setSimulationSpeed(simulation.getSimulationSpeed() * 1.1);
				} else if (e.getKeyChar() == '*') {
					if ((e.getModifiers() & (KeyEvent.CTRL_MASK | KeyEvent.CTRL_DOWN_MASK)) != 0) {
						simulation.setSimulationSpeed(0);
					} else {
						simulation.setSimulationSpeed(0.2);
					}
				} else if (e.getKeyChar() == ' ') {
					if (simulation.isRunning()) {
						simulation.setRunning(false);
					} else {
						simulation.setRunning(true);
					}
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
	public void paint(Graphics2D canvas) {
		StringBuilder label = new StringBuilder();
		label.append("TIME: ");
		label.append(simulation.getCurrentTime() / 1000.0);
		label.append(" ");
		if (simulation.isFinished()) {
			label.append("(FINISHED)");
		} else {
			if (simulation.getCurrentTime() == 0) {
				label.append("(INITIALIZING)");

				canvas.setColor(new Color(0, 0, 0, 200));
				canvas.fillRect(200, 400, Vis.getDrawingDimension().width - 400, Vis.getDrawingDimension().height - 800);

				Font oldFont = canvas.getFont();
				canvas.setFont(new Font("Arial", 0, 20));
				canvas.setColor(Color.WHITE);
				canvas.drawString("INITIALIZING...", Vis.getDrawingDimension().width / 2 - 60 , Vis.getDrawingDimension().height / 2 + 7);
				canvas.setFont(oldFont);
			} else {
				if (simulation.isRunning()) {
					label.append("(");
					label.append(MessageFormat.format("{0,number,#.##}", 1/simulation.getSimulationSpeed()));
					label.append("x)");
				} else {
					label.append("(PAUSED)");
				}
			}
		}

		canvas.setColor(Color.BLUE);
		canvas.drawString(label.toString(), 15, 20);
	}

	@Override
	public String getLayerDescription() {
		String description = "[Simulation Control] Layer controls the simulation and shows simulation time and speed,\n" +
				"by pressing '<space>', the simulation can be paused and unpaused,\n" +
				"by pressing '+'/'-', the simulation can be speed up and slow down,\n" +
				"by pressing '*', the speed of simulation is set to default value (1x),\n" +
				"by pressing Ctrl+'*', the speed of simulation is set to fastest possible speed (∞x).";
		return buildLayersDescription(description);
	}

	public static VisLayer create(Simulation simulation) {
		VisLayer simulationControl = new SimulationControlLayer(simulation);

		KeyToggleLayer toggle = KeyToggleLayer.create("s");
		toggle.addSubLayer(simulationControl);
		toggle.setHelpOverrideString(simulationControl.getLayerDescription() + "\n" +
				"By pressing 's', the simulation info can be turned off and on.");

		return toggle;
	}

}