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
package cz.cvut.fel.aic.alite.simulation;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * List of Draw Listeners to draw. When simulation allows to draw, it draws all sublisteners. When
 * one listener is complete, it must be added again to draw again. If listener must interrupt
 * drawing because of timeout, it will return false, will not be removed from list and next time
 * will be drawed first.
 *
 * @author Ondrej Milenovsky
 * */
public class MultipleDrawListener implements DrawListener {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MultipleDrawListener.class);

	private final List<DrawListener> listeners;
	private final Simulation simulation;
	private final String name;

	/** create and set pointer to simulation, also set to simulation */
	public MultipleDrawListener(Simulation sim, String name) {
		listeners = new LinkedList<DrawListener>();
		simulation = sim;
		simulation.setDrawListener(this);
		this.name = name;
	}

	public MultipleDrawListener(Simulation sim) {
		this(sim, "Multiple draw listener");
	}

	/**
	 * create and set pointer to simulation, also set to simulation, sets timeout and reload to
	 * simulation
	 */
	public MultipleDrawListener(Simulation sim, long timeout, long reload) {
		this(sim);
		simulation.setDrawTimeout(timeout);
		simulation.setDrawReload(reload);
	}

	/**
	 * if simulation running adds it to queue and returns true, else only returns false
	 */
	public synchronized boolean requestDraw(DrawListener listener) {
		if (simulation.requestDraw()) {
			if (!listeners.contains(listener)) {
				listeners.add(listener);
			}
			return true;
		}
		return false;
	}

	public synchronized boolean removeListener(DrawListener listener) {
		return listeners.remove(listener);
	}

	/**
	 * draw all listeners, if the listener implementation will draw everything until deadline, it
	 * will be removed from list, each implementation must not exceed the deadline !
	 */
	@Override
	public synchronized boolean drawFrame(long deadline) {
		for (Iterator<DrawListener> it = listeners.iterator(); it.hasNext();) {
			DrawListener listener = it.next();
			// draw one listener
			boolean drawed = false;
			try {
				drawed = listener.drawFrame(deadline);
			} catch (ConcurrentModificationException e) {
				e.printStackTrace();
				drawed = false;
			} catch (Exception e) {
				e.printStackTrace();
				drawed = true;
			}
			if (drawed) {
				it.remove();
			} else {
				return false;
			}
			// check timeout
			if (System.currentTimeMillis() >= deadline) {
				LOGGER.warn("{}: Drawing frame exceeted timeout. Each implementation of DrawListener must not exceet "
						+ "the timeout! Exceeted {}ms", listener.getName(), (System.currentTimeMillis() - deadline));
				return false;
			}
		}
		return true;
	}

	@Override
	public String getName() {
		return name;
	}

}
