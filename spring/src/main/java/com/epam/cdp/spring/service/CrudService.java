package com.epam.cdp.spring.service;

import com.epam.cdp.spring.dao.impl.inmemory.CrudRepository;

public interface CrudService<T, O> extends CrudRepository<T, O> {
}
