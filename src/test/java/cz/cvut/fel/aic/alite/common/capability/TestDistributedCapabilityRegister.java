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
package cz.cvut.fel.aic.alite.common.capability;

import cz.cvut.fel.aic.alite.common.capability.DistributedCapabilityRegisterImpl;
import cz.cvut.fel.aic.alite.common.capability.CapabilityRegister;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.cvut.fel.aic.alite.communication.DefaultCommunicator;
import cz.cvut.fel.aic.alite.communication.channel.CommunicationChannelException;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel.DefaultReceiverTable;
import cz.cvut.fel.aic.alite.communication.channel.DirectCommunicationChannel.ReceiverTable;

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
		
		
		DefaultCommunicator comm3 = new DefaultCommunicator("C");
		comm3.addChannel(new DirectCommunicationChannel(comm3, rt));
		
		assertTrue(rt.contains("C"));
		
		CapabilityRegister cr3 = new DistributedCapabilityRegisterImpl(comm3);
		
		assertTrue(cr3.getIdentities().equals(cr1.getIdentities()));
		assertTrue(cr3.getIdentities().equals(cr2.getIdentities()));
		
		cr3.register("C", "canSwim");
		
		assertTrue(cr1.getIdentities("canSwim").contains("C"));
		
		assertTrue(cr3.getIdentities().equals(cr1.getIdentities()));
		assertTrue(cr3.getIdentities().equals(cr2.getIdentities()));
		
		assertTrue(cr1.getIdentities("canSwim").contains("B"));
		assertTrue(cr1.getIdentities("canSwim").contains("C"));
		
	}

}
