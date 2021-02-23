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
package cz.cvut.fel.aic.alite.common.event;

import static org.junit.Assert.* ;
import org.junit.Test;

public class TestEventProcessorDeterministic {

	@Test
	public void sameTimeEventOrdering() {
		final Counter counter = new Counter(10);
		final EventProcessor eventProcessor = new EventProcessor();

		for (int i = 10; i <= 1000; i++) {
			eventProcessor.addEvent(null, new EventHandler() {

				@Override
				public void handleEvent(Event event) {
					if ((Integer) event.getContent() / 10 != counter.value / 10) {
						assertTrue(false);
						return;
					}
					counter.value++;
				}

				@Override
				public EventProcessor getEventProcessor() {
					return eventProcessor;
				}

			}, null, i, i/10);
		}

		eventProcessor.run();

		assertTrue(true);
	}

	private static class Counter {

		int value = 10;

		public Counter(int value) {
			this.value = value;
		}

	}

}
