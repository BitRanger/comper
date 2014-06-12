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


import java.io.Serializable;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author BowenCai
 *
 * @param <K>
 * @param <V>
 */
public final class SimpleCache<K,V> implements Serializable {

	private static final long serialVersionUID = 3159753264696995816L;

	private final int size;
    private ReentrantLock lock;

    private final ConcurrentHashMap<K,V> eden;
    private transient WeakHashMap<K,V> longterm;
    
    public SimpleCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<K, V>(size * 4 / 3);
        this.longterm = new WeakHashMap<K, V>(size * 4 / 3);
        lock = new ReentrantLock(true);
    }

	public V get(K k) {
		V v = this.eden.get(k);
		if (v == null) {
			lock.lock();
			try {
				if (longterm == null) {
					longterm = new WeakHashMap<K, V>(size * 4 / 3);
					return null;
				} else {
					v = this.longterm.get(k);
					if (v != null) {
						this.eden.put(k, v);
					}
				}
			} finally {
				lock.unlock();
			}
		}
		return v;
	}

	public void put(K k, V v) {

		if (this.eden.size() >= size) {
			lock.lock();
			try {
				if (longterm == null) {
					longterm = new WeakHashMap<K, V>(size * 4 / 3);
				}
				this.longterm.putAll(this.eden);
				this.eden.clear();
			} finally {
				lock.unlock();
			}
		}
		this.eden.put(k, v);
	}
}
