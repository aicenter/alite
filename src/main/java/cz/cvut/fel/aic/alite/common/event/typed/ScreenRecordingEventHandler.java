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
package cz.cvut.fel.aic.alite.common.event.typed;

import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessor;
import cz.cvut.fel.aic.alite.vis.VisManager;

public class ScreenRecordingEventHandler implements EventHandler {

	private int width, height;
	private boolean recording = false;
	private EventProcessor eventProcessor;

	public ScreenRecordingEventHandler(EventProcessor eventProcessor, int width, int height){
		this.eventProcessor = eventProcessor;
		this.width = width;
		this.height = height;
	}

	public void recordingStarted(){
		recording = true;
	}

	public void recordingStopped(){
		recording = false;
	}

	@Override
	public EventProcessor getEventProcessor() {
		return null;
	}

	@Override
	public void handleEvent(Event event) {
		if(recording){
			VisManager.encodeFrame(width, height);
			eventProcessor.addEvent(this, 40);
		} else {
			VisManager.finishVideoRecording();
		}
	}
}
