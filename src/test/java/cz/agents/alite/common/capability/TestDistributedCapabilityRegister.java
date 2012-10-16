package cz.agents.alite.common.capability;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.agents.alite.communication.DefaultCommunicator;
import cz.agents.alite.communication.channel.CommunicationChannelException;
import cz.agents.alite.communication.channel.DirectCommunicationChannel;
import cz.agents.alite.communication.channel.DirectCommunicationChannel.DefaultReceiverTable;
import cz.agents.alite.communication.channel.DirectCommunicationChannel.ReceiverTable;

public class TestDistributedCapabilityRegister {

	@Test
	public void test() throws CommunicationChannelException {
		
		ReceiverTable rt = new DefaultReceiverTable();
		
		DefaultCommunicator comm1 = new DefaultCommunicator("A");
		comm1.addChannel(new DirectCommunicationChannel(comm1, rt));
		
		DefaultCommunicator comm2 = new DefaultCommunicator("B");
		comm2.addChannel(new DirectCommunicationChannel(comm2, rt));
		
		assertTrue(rt.contains("A"));
		assertTrue(rt.contains("B"));
		
		CapabilityRegister cr1 = new DistributedCapabilityRegisterImpl(comm1);
		CapabilityRegister cr2 = new DistributedCapabilityRegisterImpl(comm2);
		
		cr1.register("A", "canCook");
		
		assertTrue(cr2.getIdentities("canCook").contains("A"));
		
		cr2.register("B", "canSwim");
		
		assertTrue(cr1.getIdentities("canCook").contains("A"));
		assertTrue(cr1.getIdentities("canSwim").contains("B"));
		
		assertTrue(cr1.getIdentities().equals(cr2.getIdentities()));
		
	}

}
