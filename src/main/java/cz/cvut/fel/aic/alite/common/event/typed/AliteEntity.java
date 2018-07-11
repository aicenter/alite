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
import cz.cvut.fel.aic.alite.common.event.EventProcessor;
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
    
    protected List<Enum> getEventTypesToHandle(){
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
