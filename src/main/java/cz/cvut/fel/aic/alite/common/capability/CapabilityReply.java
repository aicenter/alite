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
package cz.cvut.fel.aic.alite.common.capability;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

class CapabilityReply implements Serializable{
	
	private static final long serialVersionUID = 7203583301689529748L;

	private static final String SET_END = "|";
	
	private final String[] data;
	
	/**
	 * Encode
	 * @param register
	 */
	public CapabilityReply(Map<String, Set<String>> register){
		LinkedList<String> dataList = new LinkedList<String>();
		
		for(String key : register.keySet()){
			dataList.add(key);
			dataList.addAll(register.get(key));
			dataList.add(SET_END);
		}
		
		data = dataList.toArray(new String[0]);
	}
	
	/**
	 * Decode
	 * @return
	 */
	public Map<String, Set<String>> getData(){
		Map<String, Set<String>> register = new HashMap<String, Set<String>>();
		
		String key = null;
		
		for(String s : data){
			if(key == null){
				key = s;
				register.put(key, new LinkedHashSet<String>());
			}else{
				if(s.equals(SET_END)){
					key = null;
				}else{
					register.get(key).add(s);
				}
			}
		}
		
		return register;
	}
}