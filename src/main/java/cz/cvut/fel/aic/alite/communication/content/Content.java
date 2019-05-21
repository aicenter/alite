/* 
 * Copyright (C) 2019 Czech Technical University in Prague.
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
package cz.cvut.fel.aic.alite.communication.content;

import java.io.Serializable;

/**
 * Basic content wrapper for the messaging.
 *
 * @author Jiri Vokrinek
 */
public class Content implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3523260995414320200L;
	private final Object data;

	/**
	 *
	 * @param data the content data
	 */
	public Content(Object data) {
		this.data = data;
	}

	/**
	 * Returns the content data.
	 *
	 * @return the content data
	 */
	public Object getData() {
		return data;
	}
	
	public String toString(){
		if(data == null){
			return "";
		}else{
			return data.toString();
		}
	}
}
