/*******************************************************************************
 * Copyright (c) 2014 WangKang.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributor:
 *     WangKang. - initial API and implementation
 ******************************************************************************/
package org.wangk.comper.db.jdbc.collection;

import java.util.HashMap;
import java.util.Map;

public class NoCaseMap<V> extends HashMap<String, V>{

	private static final long serialVersionUID = 6686934837476598122L;

	public NoCaseMap() {
		this(16);
	}
	
	public NoCaseMap(int initCap) {
		super(initCap);
	}
	
	@Override
	public V put(String key, V value) {
		return super.put(key.toLowerCase(), value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends V> map) {
		if (map.isEmpty()) {
			return;
		}
		for (Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public boolean containsKey(Object key) {
		return key instanceof String 
				&& super.containsKey(((String) key).toLowerCase());
	}

	@Override
	public V get(Object key) {
		if (key instanceof String) {
			return super.get(((String) key).toLowerCase());
		}
		else {
			return null;
		}
	}

	@Override
	public V remove(Object key) {
		if (key instanceof String ) {
			return super.remove(((String)key).toLowerCase());
		}
		else {
			return null;
		}
	}

	@Override
	public void clear() {
		super.clear();
	}
}
