package com.epam.cdp.spring.service.impl;

import com.epam.cdp.spring.dao.CustomerRepository;
import com.epam.cdp.spring.model.Customer;
import com.epam.cdp.spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  CustomerRepository customerRepository;

  @Override
  public void create(Customer obj) {
    customerRepository.create(obj);
  }

  @Override
  public Customer read(Integer id) {
    return customerRepository.read(id);
  }

  @Override
  public Customer update(Integer id, Customer obj) {
    return customerRepository.update(id, obj);
  }

  @Override
  public Customer delete(Integer id) {
    return customerRepository.delete(id);
  }
}
