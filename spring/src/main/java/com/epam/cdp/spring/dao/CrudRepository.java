package com.epam.cdp.spring.dao;

public interface CrudRepository<T, O> {
  void create(O obj);
  O read(T id, Class<O> entityType);
  O update(T id, O obj);
  O delete(T id, Class<O> entityType);
}
