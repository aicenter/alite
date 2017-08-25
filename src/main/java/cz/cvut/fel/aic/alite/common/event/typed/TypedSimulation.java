/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.aic.alite.common.event.typed;

import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessorEventType;
import cz.cvut.fel.aic.alite.common.event.EventType;
import cz.cvut.fel.aic.alite.simulation.Simulation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fido
 */
public class TypedSimulation extends Simulation{
    
    private final HashMap<EventType,List<AliteEntity>> listeningEntities;

    public TypedSimulation(long simulationEndTime) {
        super(simulationEndTime);
        listeningEntities = new HashMap<EventType, List<AliteEntity>>();
    }
    
    
    
    public void addEventHandler(AliteEntity eventHandler, List<EventType> eventsTypesToHandle) {
        for (EventType eventType : eventsTypesToHandle) {
            if(!listeningEntities.containsKey(eventType)){
                listeningEntities.put(eventType, new LinkedList<AliteEntity>());
            }
            listeningEntities.get(eventType).add(eventHandler);
        }
    }

    @Override
    protected void fireEvent(Event event) {
        if (event.getRecipient() != null) {
            event.getRecipient().handleEvent(event);
        }
        else {
            List<AliteEntity> relevantEntities = listeningEntities.get(event.getType());
            if(relevantEntities != null){
                for (EventHandler entity : relevantEntities) {
                    entity.handleEvent(event);
                }
            }
            if (event.isType(EventProcessorEventType.STOP)) {
                eventQueue.clear();
            }
        }
    }
    
    
    
}
