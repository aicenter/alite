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
package cz.cvut.fel.aic.alite.communication.content.binary;

import cz.cvut.fel.aic.alite.communication.Message;
import cz.cvut.fel.aic.alite.communication.MessageHandler;

/**
 * 
 * {@link MessageHandler} for handling {@link BinaryContent}.
 * 
 * @author stolba
 *
 */
public abstract class BinaryMessageHandler implements MessageHandler {

	@Override
	public void notify(Message message) {
		if (BinaryContent.class.equals(message.getContent().getClass())) {
			BinaryContent content = (BinaryContent) message.getContent();
			handleMessage(message, content);
		}
	}

	/**
	 * Handler for binary messages.
	 *
	 * @param message
	 * @param content
	 */
	abstract public void handleMessage(Message message, BinaryContent content);
}
