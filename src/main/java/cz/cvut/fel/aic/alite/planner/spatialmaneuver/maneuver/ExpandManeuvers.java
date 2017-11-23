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
package cz.cvut.fel.aic.alite.planner.spatialmaneuver.maneuver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExpandManeuvers implements Serializable {

    private final static long serialVersionUID = 18L;
    protected transient List<ExpandManeuver> maneuver;

    /**
     * Gets the value of the maneuver property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the maneuver property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManeuver().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExpandManeuver }
     *
     *
     */
    public List<ExpandManeuver> getManeuver() {
        if (maneuver == null) {
            maneuver = new ArrayList<ExpandManeuver>();
        }
        return this.maneuver;
    }

}
