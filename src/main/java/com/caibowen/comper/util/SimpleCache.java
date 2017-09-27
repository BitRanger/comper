
package com.caibowen.comper.util;


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
public final class SimpleCache<K, V> implements Serializable {

  private static final long serialVersionUID = 3159753264696995816L;

  private final int size;
  private ReentrantLock lock;

  private final ConcurrentHashMap<K, V> eden;
  private transient WeakHashMap<K, V> longterm;

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
