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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Externalizable content wrapper for the messaging.
 *
 * @author Michal Stolba
 */
public class ExternalizableContent extends Content implements Externalizable {

	private Object data;

	public ExternalizableContent() {
		super(null);
	}

	/**
	 *
	 * @param data the content data
	 */
	public ExternalizableContent(Object data) {
		super(null);
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

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(data);
		out.flush();
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		data = in.readObject();
	}

	public String toString() {
		if (data == null) {
			return "";
		} else {
			return data.toString();
		}
	}

}
