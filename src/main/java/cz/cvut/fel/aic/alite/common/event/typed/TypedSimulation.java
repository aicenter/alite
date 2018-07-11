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
package cz.cvut.fel.aic.alite.common.event.typed;

import cz.cvut.fel.aic.alite.common.event.Event;
import cz.cvut.fel.aic.alite.common.event.EventHandler;
import cz.cvut.fel.aic.alite.common.event.EventProcessorEventType;
import cz.cvut.fel.aic.alite.simulation.Simulation;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author fido
 */
public class TypedSimulation extends Simulation{
    
    private final HashMap<Enum,List<AliteEntity>> listeningEntities;

    public TypedSimulation(long simulationEndTime) {
        super(simulationEndTime);
        listeningEntities = new HashMap<>();
    }
    
    
    
    public void addEventHandler(AliteEntity eventHandler, List<Enum> eventsTypesToHandle) {
        for (Enum eventType : eventsTypesToHandle) {
            if(!listeningEntities.containsKey(eventType)){
                listeningEntities.put(eventType, new LinkedList<>());
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
