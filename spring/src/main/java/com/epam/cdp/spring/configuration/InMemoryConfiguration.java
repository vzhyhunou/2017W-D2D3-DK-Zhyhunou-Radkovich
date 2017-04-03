package com.epam.cdp.spring.configuration;

import com.epam.cdp.spring.cache.*;
import com.epam.cdp.spring.dao.*;
import com.epam.cdp.spring.dao.impl.orm.CrudOrmRepository;
import com.epam.cdp.spring.dao.impl.inmemory.*;
import com.epam.cdp.spring.model.Customer;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
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

  @Bean
  @Profile("h2")
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
    return databaseBuilder
        .setType(EmbeddedDatabaseType.H2)
        .addScript("h2/create-tables.sql")
        .build();
  }

  @Bean
  @Profile("h2")
  public CrudOrmRepository customerCrudOrmRepository() {
    return new CrudOrmRepository(dataSource());
  }
}
