package com.epam.cdp.spring.service;

public interface CrudService<T, O> {
  void create(O obj);
  O read(T id);
  O update(T id, O obj);
  O delete(T id);
}
