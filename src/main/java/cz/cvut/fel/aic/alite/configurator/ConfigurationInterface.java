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
package cz.cvut.fel.aic.alite.configurator;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Ondrej Hrstka (ondrej.hrstka at agents.fel.cvut.cz)
 */
public interface ConfigurationInterface {

	/**
	 * Writes Config into given writer
	 * @param writer
	 */
	public void writeTo(Writer writer);

	/**
	 * Returns true if Config contains key, else false
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key);

	/**
	 * Retrieves boolean value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found or cannot be cast to boolean.
	 */
	public boolean getBoolean(String key);

	/**
	 * Retrieves boolean value for given key. If value is not found or cannot be
	 * cast to boolean, defaultValue is returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defaultValue);

	/**
	 * Retrieves double value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found or cannot be cast to BigDecimal.
	 */
	public double getDouble(String key);

	/**
	 * Retrieves double value for given key. If value is not found or cannot be
	 * cast to BigDecimal, defaultValue is returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String key, double defaultValue);

	/**
	 * Retrieves float value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found or cannot be cast to BigDecimal.
	 */
	public float getFloat(String key);

	/**
	 * Retrieves integer value for given key. If value is not found or cannot be
	 * cast to float, defaultValue is returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(String key, float defaultValue);

	/**
	 * Retrieves integer value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found or cannot be cast to integer.
	 */
	public int getInt(String key);

	/**
	 * Retrieves integer value for given key. If value is not found or cannot be
	 * cast to integer, defaultValue is returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String key, int defaultValue);

	/**
	 * Retrieves value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found
	 */
	public Object getObject(String key);

	/**
	 * Retrieves value for given key. If value is not found, defaultValue is
	 * returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Object getObject(String key, Object defaultValue);

	/**
	 * Gets key list
	 * @return
	 */
	public List<String> getKeyList();

	/**
	 * Retrieves String value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found or cannot be cast to String.
	 */
	public String getString(String key);

	/**
	 * Retrieves String value for given key. If value is not found or cannot be
	 * cast to String, defaultValue is returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getString(String key, String defaultValue);

	/**
	 * Retrieves BigDecimal value for given key.
	 * @param key
	 * @return
	 * @throws IllegalArgumentException if value is not found or cannot be cast to BigDecimal.
	 */
	public BigDecimal getBigDecimal(String key);

	/**
	 * Retrieves BigDecimal value for given key. If value is not found or cannot be
	 * cast to BigDecimal, defaultValue is returned
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public BigDecimal getBigDecimal(String key, BigDecimal defaultValue);

	/**
	 * Retrieves Map value for given key.
	 * @param <TKey> type of maps key
	 * @param <TValue> type of maps value
	 * @param key
	 * @throws IllegalArgumentException if value is not found or cannot be cast to Map.
	 * @return
	 */
	public <TKey, TValue> Map<TKey, TValue> getMap(String key);

	/**
	 * Retrieves Map value for given key. If value is not found or cannot be
	 * cast to Map, defaultValue is returned
	 * @param <TKey> type of maps key
	 * @param <TValue> type of maps value
	 * @param key
	 * @return
	 */
	public <TKey, TValue> Map<TKey, TValue> getMap(String key, Map<TKey, TValue> defaultValue);

	/**
	 * Retrieves List value for given key.
	 * @param <TValue> type of list's value
	 * @param key
	 * @throws IllegalArgumentException if value is not found or cannot be cast to List.
	 * @return
	 */
	public <TValue> List<TValue> getList(String key);

	/**
	 * Retrieves List value for given key. If value is not found or cannot be
	 * cast to List, defaultValue is returned
	 * @param <TValue> type of list's value
	 * @param key
	 * @return
	 */
	public <TValue> List<TValue> getList(String key, List<TValue> defaultValue);

	/**
	 * Retrieves subtree for given prefix.
	 * @param prefix
	 * @return
	 * @throws IllegalArgumentException if value with given prefix is not found.
	 */
	public ConfigurationInterface getSubTree(String prefix);

}
