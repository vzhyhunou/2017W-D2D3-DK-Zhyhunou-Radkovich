package com.epam.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public abstract class BaseInmemoryCrudRepository<T> implements BaseCrudRepository<T> {

  private List<T> objects = new ArrayList<>();

  protected abstract long generateId();

  protected abstract long getId(T obj);

  protected abstract void setId(T obj, long id);

  @Override
  public T create(T obj) {
    setId(obj, generateId());
    objects.add(obj);
    return obj;
  }

  @Override
  public T read(long id) {
    List<T> list = objects.stream()
        .filter(obj -> getId(obj) == id)
        .collect(toList());
    if (list.isEmpty()) return null;
    else return list.get(0);
  }

  @Override
  public T update(T obj) {
    objects = objects.stream()
        .map(persistedObj -> {
          if (getId(persistedObj) == getId(obj)) return obj;
          else return persistedObj;
        })
        .collect(toList());
    return obj;
  }

  @Override
  public T delete(long id) {
    int i;
    for (i = 0; i < objects.size(); i++) if (getId(objects.get(i)) == id) break;
    return objects.remove(i);
  }
}
