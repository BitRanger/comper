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
package org.wangk.comper.util;


import java.io.Serializable;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

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

    private final ConcurrentHashMap<K,V> eden;

    private final transient WeakHashMap<K,V> longterm;

    public SimpleCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<>(size * 4 / 3);
        this.longterm = new WeakHashMap<>(size * 4 / 3);
    }

	public V get(K k) {
		V v = this.eden.get(k);
		if (v == null) {
			synchronized (longterm) {
				v = this.longterm.get(k);
				if (v != null) {
					this.eden.put(k, v);
				}
			}
		}
		return v;
	}

    public void put(K k, V v) {
        if (this.eden.size() >= size) {
            synchronized (longterm) {
                this.longterm.putAll(this.eden);
            }
            this.eden.clear();
        }
        this.eden.put(k, v);
    }
}
