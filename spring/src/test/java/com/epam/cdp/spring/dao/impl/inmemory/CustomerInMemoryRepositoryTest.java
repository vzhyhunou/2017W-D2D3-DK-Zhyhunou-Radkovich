package com.epam.cdp.spring.dao.impl.inmemory;

import com.epam.cdp.spring.dao.CustomerRepository;
import com.epam.cdp.spring.dao.impl.inmemory.CustomerInMemoryRepository;
import com.epam.cdp.spring.model.Customer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerInMemoryRepositoryTest {

  private CustomerRepository repository;

  @Before
  public void setUp() throws Exception {
    repository = new CustomerInMemoryRepository();
  }

  @Test
  public void testCreateRead() throws Exception {
    Customer customer1 = new Customer(1, "a", "b");
    repository.create(customer1);
    Customer customer2 = new Customer(2, "b", "c");
    repository.create(customer2);

    assertEquals(customer1, repository.read(1, Customer.class));
    assertEquals(customer2, repository.read(2, Customer.class));
  }

  @Test
  public void testUpdate() throws Exception {
    Customer customer1 = new Customer(1, "a", "b");
    repository.create(customer1);

    Customer updatedCustomer = repository.update(customer1.customerId, customer1);
    assertEquals(customer1, updatedCustomer);
    assertEquals(customer1, repository.read(customer1.customerId, Customer.class));
  }
}