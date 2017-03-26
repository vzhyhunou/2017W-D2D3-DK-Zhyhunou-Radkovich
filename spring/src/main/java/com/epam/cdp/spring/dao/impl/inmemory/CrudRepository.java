package com.epam.cdp.spring.dao.impl.inmemory;

public interface CrudRepository<T, O> {
  void create(O obj);
  O read(T id);
  O update(T id, O obj);
  O delete(T id);
}
