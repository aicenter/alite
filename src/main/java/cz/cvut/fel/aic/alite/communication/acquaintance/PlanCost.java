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
package cz.cvut.fel.aic.alite.communication.acquaintance;

/**
 *  For plan cost comparisions
 *
 * @author Jiri Vokrinek
 */
public interface PlanCost {

	/**
	 * Compares this plan cost with given cost
	 *
	 * @param cost the cost to compare
	 * @return true if this PlanCost is lower then cost, or if cost is null
	 */
	boolean isLower(PlanCost cost);

	/**
	 * Computes scalar representation of the plan cost.
	 *
	 * @return integer equivalent of the cost
	 */
	Long getIntegerEquivalent();

	/**
	 * Computes the summation of the cost and given cost.
	 * This PlanCost object remains unchanged.
	 *
	 * @param cost cost to add
	 * @return Summ of the costs coresponding to PlanCost = this + cost
	 */
	PlanCost add(PlanCost cost);

	/**
	 * Computes the substraction of the cost and given cost.
	 * This PlanCost object remains unchanged.
	 *
	 * @param cost cost to sub
	 * @return Summ of the costs coresponding to PlanCost = this - cost
	 */
	PlanCost sub(PlanCost cost);
	

}
