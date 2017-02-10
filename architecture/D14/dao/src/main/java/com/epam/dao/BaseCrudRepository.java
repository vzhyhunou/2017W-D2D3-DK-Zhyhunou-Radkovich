package com.epam.dao;

public interface BaseCrudRepository<T> {
  T create(T obj);
  T read(long id);
  T update(T obj);
  T delete(long id);
}
