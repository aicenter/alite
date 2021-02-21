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
package cz.cvut.fel.aic.alite.creator;

import org.slf4j.LoggerFactory;

/**
 * The {@link CreatorFactory} dynamically instantiates a particular {@link Creator}
 * according to the application arguments.
 *
 * The application arguments are passed into the instantiated creator instantly
 * after its instantiation by the init() method. It means, the first creator's
 * argument is its class type as a string.
 *
 *
 * @author Antonin Komenda
 */
public class CreatorFactory {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CreatorFactory.class);

	public static Creator createCreator(String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("Creator must be instantiated with at least Creator class name as a first parameter!");
		}

		try {
			Creator creator = (Creator) Class.forName(args[0]).newInstance();
			creator.init(args);
			return creator;
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return null;
	}

}
