package com.epam.cdp.spring.dao.impl.inmemory;

import com.epam.cdp.spring.dao.CustomerRepository;
import com.epam.cdp.spring.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerInMemoryRepository
    extends AbstractInMemoryStorage<Integer, Customer>
    implements CustomerRepository {

  private Integer incrementalId = 1;

  @Override
  public Customer getById(int id) {
    return read(id, Customer.class);
  }

  @Override
  Integer generateId() {
    return incrementalId++;
  }
}
