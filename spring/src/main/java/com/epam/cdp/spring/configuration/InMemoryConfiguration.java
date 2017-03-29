package com.epam.cdp.spring.configuration;

import com.epam.cdp.spring.cache.*;
import com.epam.cdp.spring.dao.*;
import com.epam.cdp.spring.dao.impl.inmemory.*;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
@ComponentScan("com.epam.cdp.spring.service")
public class InMemoryConfiguration {

  @Bean
  @Profile("inmemory")
  public CustomerRepository customerRepository() {
    return new CustomerInMemoryRepository();
  }

  @Bean
  @Profile("inmemory")
  public TicketRepository ticketRepository() {
    return new TickerInMemoryRepository();
  }

  @Bean
  @Profile("inmemory")
  public FlightRepository flightRepository() {
    return new FlightInMemoryRepository();
  }

  @Bean
  @Scope("prototype")
  @Profile("cacheble")
  public Cache serviceCache() {
    return new Cache(new HashMap<>());
  }
}
