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

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import cz.cvut.fel.aic.alite.communication.content.Content;

public final class Message implements Serializable {

	private static final long serialVersionUID = 5903646003802722160L;

	private final long id;
	private final String sender;
	private final LinkedList<String> receivers = new LinkedList<String>();
	private final Content content;

	Message(String sender, Content content, long id) {

		if (content == null) {
			throw new IllegalArgumentException("null Content is not permitted");
		}

		this.sender = sender;
		this.content = content;
		this.id = id;
	}

	/**
	 * Get the value of receiver
	 *
	 * @return the value of receiver
	 */
	public Collection<String> getReceivers() {
		return receivers;
	}

	/**
	 * Set the value of receiver
	 *
	 * @param newReceivers
	 */
	public void addReceivers(Collection<String> newReceivers) {
		receivers.addAll(newReceivers);
	}

	/**
	 *
	 * @param receiver
	 */
	public void addReceiver(String receiver) {
		receivers.add(receiver);
	}

	/**
	 *
	 * @return
	 */
	public Content getContent() {
		return content;
	}

	/**
	 *
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Get the value of senderID
	 *
	 * @return the value of senderID
	 */
	public String getSender() {
		return sender;
	}

	public String toString(){
		return "[Sender: " + sender +"\n" +
				"Receivers: " + receivers + "\n" +
				"Content: " + content + "]";
	}
}
