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
