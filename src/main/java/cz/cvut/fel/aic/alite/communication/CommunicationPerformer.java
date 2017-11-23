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
package cz.cvut.fel.aic.alite.communication;

/**
 * Used to implement passive communication - in order to receive any messages 
 * via a MessageHandler, the performReceive() method must be invoked. 
 * The message (if any) is then delivered using the same thread which called 
 * the performReceive() method. The call is non-blocking.
 * 
 * @author stolba
 *
 */
public interface CommunicationPerformer {
	
	/**
	 * Use the current thread to poll a message and give it to the handlers.
	 * The call is non-blocking.
	 * @return true if a message was received, false otherwise
	 */
	public boolean performReceiveNonblock();
	
	/**
	 * Use the current thread to poll a message and give it to the handlers.
	 * The call is blocking.
	 * @param timeout in milliseconds
	 */
	public void performReceiveBlock(long timeoutMs);
	
	/**
	 * Close opened resources, i.e. socket.
	 */
	public void performClose();

}
