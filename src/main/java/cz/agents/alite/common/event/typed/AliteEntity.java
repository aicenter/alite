/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.agents.alite.common.event.typed;

import cz.agents.alite.common.event.Event;
import cz.agents.alite.common.event.EventHandler;
import cz.agents.alite.common.event.EventProcessor;
import cz.agents.alite.common.event.EventType;
import java.util.List;

/**
 *
 * @author fido
 */
public abstract class AliteEntity implements EventHandler{
    
    private TypedSimulation typedSimulation;

    
    public void init(TypedSimulation eventProcessor){
        if(getEventTypesToHandle() != null){
            eventProcessor.addEventHandler(this, getEventTypesToHandle());
        }
        typedSimulation = eventProcessor;
    }
    
    protected List<EventType> getEventTypesToHandle(){
        return null;
    }

    @Override
    public EventProcessor getEventProcessor() {
        return typedSimulation;
    }

    @Override
    public void handleEvent(Event event){
        
    }

    
}
