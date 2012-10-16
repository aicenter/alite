package cz.agents.alite.common.capability;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import cz.agents.alite.communication.Communicator;
import cz.agents.alite.communication.Message;
import cz.agents.alite.communication.MessageHandler;
import cz.agents.alite.communication.channel.CommunicationChannelBroadcast;
import cz.agents.alite.communication.content.Content;

/**
 * Provides basic distributed implementation of @CapabilityRegister using @HashMap 
 * and broadcast of registered capabilities
 * 
 * @author Jiri Vokrinek
 * @author Michal Stolba
 */
public class DistributedCapabilityRegisterImpl implements CapabilityRegister, MessageHandler {

    private final HashMap<String, Set<String>> register = new HashMap<String, Set<String>>();
    
    private final Communicator comm;
    
    public DistributedCapabilityRegisterImpl(Communicator communicator){
    	comm = communicator;
    	comm.addMessageHandler(this);
    }

    public void register(String identity, String capabilityName) {
    	registerLocally(identity, capabilityName);
        
    	//broadcast to others
        Message m = comm.createMessage(new Content(new CapabilityRecord(identity,capabilityName)));
        m.addReceiver(CommunicationChannelBroadcast.BROADCAST_ADDRESS);
        comm.sendMessage(m);
    }
    
    private void registerLocally(String identity, String capabilityName) {
        Set<String> recS = register.get(capabilityName);
        if (recS == null) {
            LinkedHashSet<String> page = new LinkedHashSet<String>();
            page.add(identity);
            register.put(capabilityName, page);
        } else {
            recS.add(identity);
        }
    }
    

    public Set<String> getIdentities(String capabilityName) {
        Set<String> get = register.get(capabilityName);
        if (get == null) {
            return new LinkedHashSet<String>();
        }
        return new LinkedHashSet<String>(get);

    }

    public Set<String> getIdentities() {
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        for (Set<String> page : register.values()) {
            result.addAll(page);
        }
        return result;
    }

	@Override
	public void notify(Message message) {
		if(message.getContent().getData() instanceof CapabilityRecord){
			CapabilityRecord cr = (CapabilityRecord)message.getContent().getData();
			registerLocally(cr.identity, cr.capabilityName);
		}else{
			throw new RuntimeException("Unexpected content!");
		}
	}
	
	
	private class CapabilityRecord implements Serializable{
		
		private static final long serialVersionUID = -6082625766262200377L;
		
		public final String identity;
		public final String capabilityName;
		
		public CapabilityRecord(String identity, String capabilityName){
			this.identity = identity;
			this.capabilityName = capabilityName;
		}
	}
}
