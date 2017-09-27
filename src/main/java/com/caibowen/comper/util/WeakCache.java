
package com.caibowen.comper.util;

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