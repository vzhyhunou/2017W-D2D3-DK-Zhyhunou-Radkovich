package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.cache.*;
import com.epam.cdp.spring.dao.*;
import com.epam.cdp.spring.model.*;
import com.epam.cdp.spring.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Qualifier("serviceCache")
  @Autowired
  Cache serviceCache;

  @Override
  public void create(Customer obj) {
    customerRepository.create(obj);
  }

  @Override
  public Customer read(Integer id) {
    return customerRepository.read(id, Customer.class);
  }

  @Override
  public Customer update(Integer id, Customer obj) {
    return customerRepository.update(id, obj);
  }

  @Override
  public Customer delete(Integer id) {
    return customerRepository.delete(id, Customer.class);
  }
}
