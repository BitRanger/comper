/*******************************************************************************
 * Copyright (c) 2014 Cai Bowen, Zhou Liangpeng.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Cai Bowen,  Zhou Liangpeng. - initial API and implementation
 ******************************************************************************/
package org.blacklancer.comper.util;


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
