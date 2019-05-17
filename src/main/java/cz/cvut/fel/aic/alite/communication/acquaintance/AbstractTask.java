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
package cz.cvut.fel.aic.alite.communication.acquaintance;

import java.io.Serializable;

/**
 *
 * Abstract class for default Task. 
 * Contains content Object which defines the taks activity.
 *
 * @author Jiri Vokrinek
 */
public class AbstractTask implements Task, Serializable {

	private static final long serialVersionUID = -8640881245155276281L;

	private static final String TASK_TYPE_NAME = "AbstractTask";
	
	private Object content;
	private String taskID;
	
	/**
	 *  Default constructor
	 *
	 * @param taskID unique task identiffication
	 * @param content content of the task
	 */
	public AbstractTask(String taskID, Object content) {
		this.content = content;
		this.taskID = taskID;
	}

	public String getTaskType() {
		return TASK_TYPE_NAME;
	}

	/**
	 * Gives the name of the tak type of this class.
	 * @return name
	 */
	public static String getTaskTypeName() {
		return TASK_TYPE_NAME;
	}

	public boolean compliesType(String typeName) {
		if (TASK_TYPE_NAME.equals(typeName)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the content of this task
	 *
	 * @return task content
	 */
	public Object getContent() {
		return content;
	}

	@Override
	public String toString() {
		return getTaskType() + "("+taskID+"): " + ((content!=null)?content.toString():"");
	}

	public String getTaskID() {
		return taskID;
	}
}
