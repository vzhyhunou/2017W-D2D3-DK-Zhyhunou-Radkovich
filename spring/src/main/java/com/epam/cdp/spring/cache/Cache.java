package com.epam.cdp.spring.cache;

import java.util.*;

public class Cache {
  private Map<String, Object> map = new HashMap<>();

  public Cache(Map<String, Object> map) {
    this.map = map;
  }

  public Object get(String key) {
    return map.get(key);
  }

  public Object put(String key, Object value) {
    return map.put(key, value);
  }

  public Object remove(String key) {
    return map.remove(key);
  }

  public void clear() {
    map.clear();
  }

  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Cache cache = (Cache) o;

    return map != null ? map.equals(cache.map) : cache.map == null;

  }

  @Override
  public int hashCode() {
    return map != null ? map.hashCode() : 0;
  }
}
