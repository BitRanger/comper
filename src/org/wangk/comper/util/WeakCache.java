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
package org.wangk.comper.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

public final class WeakCache<K, V> {

	private final WeakHashMap<K, Reference<V>> map = new WeakHashMap<K, Reference<V>>();
	
	public boolean contains(K key) {
		return map.get(key) == null;
	}
	
	public V get(K key) {
		Reference<V> reference = this.map.get(key);
		if (reference == null) {
			return null;
		}
		V value = reference.get();
		if (value == null) {
			this.map.remove(key);
		}
		return value;
	}

	public void put(K key, V value) {

		if (value != null) {
			this.map.put(key, new WeakReference<V>(value));
		} else {
			this.map.remove(key);
		}
	}

	public void clear() {
		this.map.clear();
	}
}
