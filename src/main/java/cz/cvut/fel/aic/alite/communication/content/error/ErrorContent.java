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
package cz.cvut.fel.aic.alite.communication.content.error;

import cz.cvut.fel.aic.alite.communication.content.Content;

/**
 * Error content for encapsulating a {@link Exception} for messaging.
 * 
 * @author Jiri Vokrinek
 */
public class ErrorContent extends Content {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7857744158681575964L;

	/**
	 *
	 * @param exception
	 */
	public ErrorContent(Exception exception) {
		super(exception);
	}

	@Override
	public Exception getData() {
		return (Exception) super.getData();
	}
}
