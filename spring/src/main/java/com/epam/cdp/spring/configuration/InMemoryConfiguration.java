package com.epam.cdp.spring.configuration;

import com.epam.cdp.spring.dao.CustomerRepository;
import com.epam.cdp.spring.dao.FlightRepository;
import com.epam.cdp.spring.dao.TicketRepository;
import com.epam.cdp.spring.dao.impl.inmemory.CustomerInMemoryRepository;
import com.epam.cdp.spring.dao.impl.inmemory.FlightInMemoryRepository;
import com.epam.cdp.spring.dao.impl.inmemory.TickerInMemoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
}
