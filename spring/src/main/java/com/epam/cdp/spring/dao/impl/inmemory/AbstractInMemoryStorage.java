package com.epam.cdp.spring.dao.impl.inmemory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractInMemoryStorage<T, O> {
  Map<T, O> storage = new HashMap<>();

  abstract T generateId();

  public void create(O obj) {
    storage.put(generateId(), obj);
  }

  public O read(T id) {
    return storage.get(id);
  }

  public O update(T id, O obj) {
    storage.put(id, obj);
    return storage.get(id);
  }

  public O delete(T id) {
    return storage.remove(id);
  }
}
