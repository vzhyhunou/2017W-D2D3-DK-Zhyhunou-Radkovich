package com.epam.cdp.spring.dao;

import com.epam.cdp.spring.dao.impl.inmemory.CrudRepository;
import com.epam.cdp.spring.model.Customer;

public interface CustomerRepository extends CrudRepository<Integer, Customer> {
  Customer getById(int id);
}
