/*******************************************************************************
 * Copyright 2014 Cai Bowen Zhou Liangpeng
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
